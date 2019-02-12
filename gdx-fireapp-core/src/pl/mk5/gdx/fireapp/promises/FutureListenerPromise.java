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

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Promise implementation.
 *
 * @param <T> The promise subject type
 */
public class FutureListenerPromise<T> extends FuturePromise<T> implements ListenerPromise<T> {

    boolean canceled;
    private Runnable onCancel;

    FutureListenerPromise() {
        thenConsumer.setCleanAfterAccept(false);
    }

    @Override
    public synchronized void doComplete(T result) {
        if (canceled) return;
        super.doComplete(result);
        state = INIT;
    }

    @Override
    public synchronized void doFail(String reason, Throwable throwable) {
        if (canceled) return;
        super.doFail(reason, throwable);
        state = INIT;
    }

    @SuppressWarnings("unchecked")
    public static <R> FutureListenerPromise<R> whenListener(final Consumer<FutureListenerPromise<R>> consumer) {
        final FutureListenerPromise<R> promise = new FutureListenerPromise<>();
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

    @Override
    @SuppressWarnings("unchecked")
    public ListenerPromise<T> thenListener(Consumer<T> listener) {
        return (ListenerPromise<T>) then(listener);
    }

    @Override
    public ListenerPromise<T> cancel() {
        canceled = true;
        if (onCancel != null) {
            onCancel.run();
            onCancel = null;
        }
        return this;
    }

    @Override
    public ListenerPromise<T> listen() {
        return (ListenerPromise<T>) subscribe();
    }

    public void onCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}
