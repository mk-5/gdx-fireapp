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

import mk.gdx.firebase.functional.Consumer;

/**
 * Promise implementation.
 *
 * @param <T> The promise subject type
 */
public class FutureListenerPromise<T> extends FuturePromise<T> implements ListenerPromise<T> {

    protected boolean canceled;
    private Runnable onCancel;

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
    public static <R> FutureListenerPromise<R> of(Object consumer) {
        if (!(consumer instanceof Consumer)) throw new IllegalArgumentException();
        FutureListenerPromise<R> promise = new FutureListenerPromise<>();
        ((Consumer<FutureListenerPromise<R>>) consumer).accept(promise);
        return promise;
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

    public void onCancel(Runnable onCancel) {
        this.onCancel = onCancel;
    }
}
