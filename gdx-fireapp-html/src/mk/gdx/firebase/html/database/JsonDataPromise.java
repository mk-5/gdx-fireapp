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

import mk.gdx.firebase.promises.FuturePromise;

/**
 * Wraps data listener into callback with String as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so it is a need to wrap our {@link FuturePromise} and parse
 * json string from javascript to Java object.
 *
 * @param <R> Result type after json string converting process
 */
class JsonDataPromise<R> extends FuturePromise<String> {

    private Class<R> wantedType;
    private FuturePromise<R> promise;

    /**
     * @param promise Listener, not null
     */
    JsonDataPromise(Class<R> wantedType, FuturePromise<R> promise) {
        this.wantedType = wantedType;
        this.promise = promise;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void doComplete(String data) {
        R result = JsonProcessor.process(wantedType, data);
        promise.doComplete(result);
    }

    @Override
    public void doFail(Throwable t) {
        promise.doFail(t);
    }
}
