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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Wraps data callback into callback with String as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so it is a need to wrap our {@link DataCallback} and parse
 * json string from javascript to Java object.
 *
 * @param <T> Result type after json string converting process
 */
class JsonPromiseWrapper<T> extends FuturePromise<String> {
    private Class<?> wantedType;
    private FuturePromise promise;

    JsonPromiseWrapper(Class<?> wantedType, FuturePromise promise) {
        this.wantedType = wantedType;
        this.promise = promise;
    }

    /**
     * Converts string data to java object and calls next data callback.
     *
     * @param data Data should be JSON string representation of firebase value, not null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void doComplete(String data) {
        T result = JsonProcessor.process(wantedType, data);
        promise.doComplete(result);
    }

    @Override
    public void doFail(Throwable throwable) {
        promise.doFail(throwable);
    }
}
