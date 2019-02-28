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

package pl.mk5.gdx.fireapp.promises;

import com.badlogic.gdx.utils.Timer;

import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Promise of future.
 *
 * @param <T> The promise subject type
 */
public class FuturePromise<T> implements Promise<T> {

    private static boolean THROW_FAIL_BY_DEFAULT = true;
    private static boolean AUTO_SUBSCRIBE = true;
    private static final int COMPLETE = 3;
    private static final int FAIL = 1;
    static final int INIT = 0;

    final ConsumerWrapper<T> thenConsumer;
    int state = INIT;
    Timer.Task subscribeTask;
    protected final PromiseStackRecognizer stackRecognizer;
    protected Runnable execution;
    private boolean pauseExecution;
    private boolean afterTriggered;
    private boolean throwFail;
    private T lazyResult;
    private FuturePromise thenPromise;
    private final BiConsumerWrapper failConsumer;
    private String failReason;
    private Throwable failThrowable;
    private FuturePromise parentPromise;

    protected FuturePromise() {
        throwFail = THROW_FAIL_BY_DEFAULT;
        thenConsumer = new ConsumerWrapper<>();
        failConsumer = new BiConsumerWrapper();
        stackRecognizer = new PromiseStackRecognizer(this);
    }

    /**
     * Sets then consumer.
     *
     * @param consumer "Then" consumer, will be call when promise had been competed.
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized <R extends T> FuturePromise<T> then(Consumer<R> consumer) {
        if (consumer == null) throw new IllegalArgumentException();
        thenConsumer.addConsumer((Consumer<T>) consumer);
        if (state == COMPLETE) {
            doComplete(lazyResult);
        }
        return this;
    }

    @Override
    public synchronized Promise<T> silentFail() {
        throwFail = false;
        stackRecognizer.getBottomThenPromise().throwFail = false;
        if (stackRecognizer.getTopParentPromise() != null)
            stackRecognizer.getTopParentPromise().throwFail = false;
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
        silentFail();
        failConsumer.addConsumer(consumer);
        if (state == FAIL) {
            doFail(failReason, failThrowable);
        }
        return this;
    }

    /**
     * Add 'then' promise which will be invoke with {@link #doComplete(Object)}
     *
     * @param promise The future promise, not null
     * @return Given promise
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
        if (((FuturePromise<R>) promise).subscribeTask != null) {
            ((FuturePromise<R>) promise).subscribeTask.cancel();
            ((FuturePromise<R>) promise).subscribeTask = null;
        }
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
        FuturePromise topParentPromise = stackRecognizer.getTopParentPromise();
        ((FuturePromise) promise).then(topParentPromise != null ? topParentPromise : this);
        return this;
    }

    @Override
    public Promise<T> subscribe() {
        FuturePromise topParentPromise = stackRecognizer.getTopParentPromise();
        if (topParentPromise != null && topParentPromise.execution != null) {
            topParentPromise.subscribe();
        } else if (execution != null && !pauseExecution) {
            execution.run();
        }
        return this;
    }

    @Override
    public Promise<T> subscribe(Consumer<T> thenConsumer) {
        return then(thenConsumer).subscribe();
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
        if (stackRecognizer.getBottomThenPromise() != this) {
            stackRecognizer.getBottomThenPromise().doFail(reason, throwable);
        } else {
            if (throwFail || stackRecognizer.getBottomThenPromise().throwFail) {
                throw new RuntimeException(reason, throwable);
            }
            if (failConsumer.isSet()) {
                failConsumer.accept(reason, throwable);
            } else {
                failReason = reason;
                failThrowable = throwable;
            }
        }
    }

    public void doFail(Throwable throwable) {
        doFail(throwable != null ? throwable.getLocalizedMessage() : "", throwable);
    }

    public Consumer<T> getThenConsumer() {
        return thenConsumer;
    }

    FuturePromise getThenPromise() {
        return thenPromise;
    }

    FuturePromise getParentPromise() {
        return parentPromise;
    }

    /**
     * Create promise with execution.
     * <p>
     * Execution should be included inside the {@code consumer#accept}
     *
     * @param consumer The execution consumer, not nul
     * @param <R>      THe promise return type
     * @return new promise
     */
    public static synchronized <R> FuturePromise<R> when(final Consumer<FuturePromise<R>> consumer) {
        final FuturePromise<R> promise = new FuturePromise<>();
        promise.execution = new Runnable() {
            @Override
            public void run() {
                promise.execution = null;
                try {
                    consumer.accept(promise);
                } catch (Exception e) {
                    promise.stackRecognizer.getBottomThenPromise().doFail(e);
                }
            }
        };
        if (GdxFIRApp.isAutoSubscribePromises()) {
            promise.subscribeTask = Timer.post(new AutoSubscribeTask(promise));
        }
        return promise;
    }

    /**
     * Creates empty promise.
     * <p>
     * Promise will be automatically complete
     *
     * @param <R> The promise type
     * @return new empty promise
     */
    public static <R> FuturePromise<R> empty() {
        return when(new Consumer<FuturePromise<R>>() {
            @Override
            public void accept(FuturePromise<R> rFuturePromise) {
                rFuturePromise.doComplete(null);
            }
        });
    }

    public static boolean isThrowFailByDefault() {
        return THROW_FAIL_BY_DEFAULT;
    }

    public static void setThrowFailByDefault(boolean throwFailByDefault) {
        THROW_FAIL_BY_DEFAULT = throwFailByDefault;
    }

    public static boolean isAutoSubscribe() {
        return AUTO_SUBSCRIBE;
    }

    public static void setAutoSubscribe(boolean autoSubscribe) {
        AUTO_SUBSCRIBE = autoSubscribe;
    }
}
