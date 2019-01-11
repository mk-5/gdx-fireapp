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
 * Promise implementation.
 *
 * @param <T> The promise subject type
 */
public class FuturePromise<T> implements Promise<T> {

    private static final int COMPLETE_LAZY = 4;
    private static final int COMPLETE = 3;
    private static final int FAIL_LAZY = 2;
    private static final int FAIL = 1;
    private static final int INIT = 0;

    protected Consumer<T> thenConsumer;
    private BiConsumer<String, ? super Throwable> failConsumer;
    private Runnable alwaysRunnable;
    private Array<FuturePromise<T>> thenPromises = new Array<>();
    private int state;
    // Following vars is only for lazy consumers call.
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
        thenConsumer = consumer;
        if (thenConsumer != null && state == COMPLETE_LAZY) {
            thenConsumer.accept(completeResult);
            state = COMPLETE;
            completeResult = null;
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
        failConsumer = consumer;
        if (failConsumer != null && state == FAIL_LAZY) {
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
        this.alwaysRunnable = runnable;
        return this;
    }

    public synchronized FuturePromise<T> with(FuturePromise<T> promise) {
        thenPromises.add(promise);
        return this;
    }

    public synchronized void doComplete(T result) {
        if (state != INIT) {
            return;
        }

        if (thenPromises.size > 0) {
            for (FuturePromise<T> promise : thenPromises) {
                promise.doComplete(result);
            }
        }

        if (thenConsumer != null) {
            thenConsumer.accept(result);
            state = COMPLETE;
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
        if (thenPromises.size > 0) {
            for (FuturePromise<T> promise : thenPromises) {
                promise.doFail(reason, throwable);
            }
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

    public static <R> FuturePromise<R> of(Consumer<FuturePromise<R>> consumer) {
        FuturePromise<R> promise = new FuturePromise<>();
        consumer.accept(promise);
        return promise;
    }
}
