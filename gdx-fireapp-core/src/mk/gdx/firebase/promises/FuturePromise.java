/*
 * Copyright 2018 mk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mk.gdx.firebase.promises;

import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;

/**
 * Promise of future.
 *
 * @param <T> The promise subject type
 */
public class FuturePromise<T> implements Promise<T> {

    private static final int COMPLETE = 3;
    private static final int FAIL = 1;
    static final int INIT = 0;

    protected int state = INIT;
    final ConsumerWrapper<T> thenConsumer;
    private boolean pauseExecution;
    private boolean afterTriggered;
    private T lazyResult;
    private FuturePromise thenPromise;
    protected Runnable execution;
    private final BiConsumerWrapper failConsumer;
    private String failReason;
    private Throwable failThrowable;
    private FuturePromise parentPromise;

    protected FuturePromise() {
        thenConsumer = new ConsumerWrapper<>();
        failConsumer = new BiConsumerWrapper();
    }

    /**
     * Sets then consumer.
     *
     * @param consumer "Then" consumer, will be call when promise had been competed.
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized FuturePromise<T> then(Consumer<T> consumer) {
        if (consumer == null) throw new IllegalArgumentException();
        thenConsumer.addConsumer(consumer);
        if (state == COMPLETE) {
            doComplete(lazyResult);
        }
        exec();
        return this;
    }

    /**
     * Sets fail bi-consumer.
     *
     * @param consumer "Fail" bi-consumer, will be call when promise had been failed.
     */
    @Override
    public synchronized FuturePromise<T> fail(BiConsumer<String, ? super Throwable> consumer) {
        if (consumer == null) throw new IllegalArgumentException();
        failConsumer.addConsumer(consumer);
        if (state == FAIL) {
            doFail(failReason, failThrowable);
        }
        exec();
        return this;
    }

    /**
     * Add 'then' promise which will be invoke with {@link #doComplete(Object)}
     *
     * @param promise The future promise, not null
     * @return self
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <R> FuturePromise<R> then(Promise<R> promise) {
        if (!(promise instanceof FuturePromise)) throw new IllegalArgumentException();
        if (((FuturePromise) promise).thenConsumer.isSet() || ((FuturePromise) promise).failConsumer.isSet()) {
            // Because calling promise.then will run execution and execution can't be pause then
            throw new IllegalStateException("Chained promise couldn't have attached consumers");
        }
        ((FuturePromise) promise).pauseExecution = true;
        ((FuturePromise) promise).parentPromise = this;
        thenPromise = (FuturePromise) promise;
        return (FuturePromise<R>) promise;
    }

    /**
     * Sets this given promise after this promise.
     *
     * @param promise The future promise, not null
     * @return self
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized Promise<T> after(Promise<?> promise) {
        if (!(promise instanceof FuturePromise)) {
            throw new IllegalArgumentException();
        }
        if (afterTriggered) {
            throw new IllegalStateException("May be only one 'after' for promise");
        }
        afterTriggered = true;
        ((FuturePromise) promise).then(this);
        ((FuturePromise) promise).exec();
        return this;
    }

    @Override
    public Promise<T> exec() {
        FuturePromise topParentPromise = getTopParentPromise();
        if (topParentPromise != null) {
            topParentPromise.exec();
        }
        if (execution != null && !pauseExecution) {
            execution.run();
        }
        return this;
    }

    /**
     * Resolves success of this promise
     *
     * @param result The promise result, may be null
     */
    public synchronized void doComplete(T result) {
        if (state != INIT && state != COMPLETE) {
            return;
        }
        state = COMPLETE;
        lazyResult = result;
        if (!thenConsumer.isSet() && thenPromise == null) {
            return;
        }
        if (state == INIT && execution != null) {
            throw new IllegalStateException("Promise 'when' has not been executed, please use 'exec', 'then(Consumer)' or fail(Consumer) method ");
        }
        if (thenConsumer.isSet()) {
            thenConsumer.accept(result);
        }
        if (thenPromise != null) {
            if (thenPromise.execution == null) {
                throw new IllegalStateException("Chained promise should has 'when' execution");
            }
            thenPromise.pauseExecution = false;
            thenPromise.execution.run();
        }
    }

    /**
     * Resolves fail of  this promise
     *
     * @param reason    The fail reason, may be null
     * @param throwable The fail exception, may be null
     */
    public synchronized void doFail(String reason, Throwable throwable) {
        if (state != INIT && state != FAIL) {
            return;
        }
        state = FAIL;
        if (failConsumer.isSet()) {
            failConsumer.accept(reason, throwable);
        } else {
            failReason = reason;
            failThrowable = throwable;
        }
    }

    public void doFail(Throwable throwable) {
        doFail(throwable != null ? throwable.getLocalizedMessage() : "", throwable);
    }

    public Consumer<T> getThenConsumer() {
        return thenConsumer;
    }

    private FuturePromise getTopParentPromise() {
        if (parentPromise == null) return null;
        FuturePromise promise;
        FuturePromise tmp = parentPromise;
        do {
            promise = tmp;
            tmp = promise.parentPromise;
        } while (tmp != null);
        return promise;
    }

    protected FuturePromise getBottomThenPromise() {
        if (thenPromise == null) return this;
        FuturePromise promise;
        FuturePromise tmp = this;
        do {
            promise = tmp;
            tmp = promise.thenPromise;
        } while (tmp != null);
        return promise;
    }

    public static <R> FuturePromise<R> when(final Consumer<FuturePromise<R>> consumer) {
        final FuturePromise<R> promise = new FuturePromise<>();
        promise.execution = new Runnable() {
            @Override
            public void run() {
                promise.execution = null;
                try {
                    consumer.accept(promise);
                } catch (Exception e) {
                    promise.getBottomThenPromise().doFail(e);
                }
            }
        };
        return promise;
    }

    public static <R> FuturePromise<R> empty() {
        return new FuturePromise<>();
    }
}
