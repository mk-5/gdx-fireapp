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

import mk.gdx.firebase.functional.Function;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Gets data as json String and return as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so it is a need to wrap our {@link FuturePromise} and parse
 * json string from javascript to Java object.
 *
 * @param <T> Result type after json string converting process
 */
class JsonPromiseModifier<T> implements Function<String, T> {
    private Class<?> wantedType;

    JsonPromiseModifier(Class<?> wantedType) {
        this.wantedType = wantedType;
    }

    @Override
    public T apply(String var1) {
        return JsonProcessor.process(wantedType, var1);
    }
}
