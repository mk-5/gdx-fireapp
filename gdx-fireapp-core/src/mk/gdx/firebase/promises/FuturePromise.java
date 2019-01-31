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

import com.badlogic.gdx.utils.Array;

import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;

/**
 * Promise of future.
 *
 * @param <T> The promise subject type
 */
public class FuturePromise<T> implements Promise<T> {

    private static final int COMPLETE_LAZY = 4;
    private static final int COMPLETE = 3;
    private static final int FAIL_LAZY = 2;
    private static final int FAIL = 1;
    static final int INIT = 0;

    Consumer<T> thenConsumer;
    protected int state = INIT;
    private BiConsumer<String, ? super Throwable> failConsumer;
    private Runnable alwaysRunnable;
    private Array<FuturePromise<T>> thenPromises = new Array<>();
    private T completeResult;
    private String failReason;
    private Throwable failThrowable;

    /**
     * Sets then consumer.
     *
     * @param consumer "Then" consumer, will be call when promise had been competed.
     */
    @Override
    public synchronized FuturePromise<T> then(Consumer<T> consumer) {
        if (consumer == null) throw new IllegalArgumentException();
        thenConsumer = consumer;
        if (state == COMPLETE_LAZY) {
            try {
                thenConsumer.accept(completeResult);
                state = COMPLETE;
                completeResult = null;
            } catch (ClassCastException e) {

            }
        }
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
        failConsumer = consumer;
        if (state == FAIL_LAZY) {
            failConsumer.accept(failReason, failThrowable);
            state = FAIL;
            failReason = null;
            failThrowable = null;
        }
        return this;
    }

    /**
     * Sets fail/complete runnable.
     *
     * @param runnable Runnable, will be call when promise had been failed or completed.
     */
    @Override
    public synchronized FuturePromise<T> always(Runnable runnable) {
        if (runnable == null) throw new IllegalArgumentException();
        this.alwaysRunnable = runnable;
        return this;
    }

    /**
     * Add 'then' promise which will be invoke with {@link #doComplete(Object)}
     *
     * @param promise The future promise, not null
     * @return self
     */
    public synchronized FuturePromise<T> then(FuturePromise<T> promise) {
        if (promise == null) throw new IllegalArgumentException();
        // TODO - multiple lazy promises? ... maybe should be only one
        if (state == COMPLETE_LAZY) {
            try {
                promise.doComplete(completeResult);
                state = COMPLETE;
                completeResult = null;
            } catch (ClassCastException e) {

            }
        } else {
            thenPromises.add(promise);
        }
        return this;
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
        // TODO - what if, .after(xx).after(xx)
        if (!(promise instanceof FuturePromise)) {
            throw new IllegalArgumentException();
        }
        ((FuturePromise) promise).then(this);
        return this;
    }

    public synchronized void doComplete(T result) {
        if (state != INIT) {
            return;
        }
        for (FuturePromise<T> promise : thenPromises) {
            promise.doComplete(result);
        }
        if (thenConsumer != null) {
            try {
                thenConsumer.accept(result);
                state = COMPLETE;
            } catch (ClassCastException e) {

            }
        } else {
            completeResult = result;
            state = COMPLETE_LAZY;
        }
        if (alwaysRunnable != null) {
            alwaysRunnable.run();
        }
    }

    public synchronized void doFail(String reason, Throwable throwable) {
        if (state != INIT) {
            return;
        }
        if (failConsumer != null) {
            failConsumer.accept(reason, throwable);
            state = FAIL;
        } else {
            failReason = reason;
            failThrowable = throwable;
            state = FAIL_LAZY;
        }
        if (alwaysRunnable != null) {
            alwaysRunnable.run();
        }
    }

    public void doFail(Throwable throwable) {
        doFail(throwable != null ? throwable.getLocalizedMessage() : "", throwable);
    }

    public boolean isComplete() {
        return state == COMPLETE;
    }

    public boolean isFailed() {
        return state == FAIL;
    }

    public Consumer<T> getThenConsumer() {
        return thenConsumer;
    }

    public static <R> FuturePromise<R> of(Consumer<FuturePromise<R>> consumer) {
        FuturePromise<R> promise = new FuturePromise<>();
        try {
            consumer.accept(promise);
        } catch (Exception e) {
            promise.doFail(e);
        }
        return promise;
    }
}
