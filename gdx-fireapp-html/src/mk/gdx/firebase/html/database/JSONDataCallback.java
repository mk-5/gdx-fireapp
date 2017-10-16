/*
 * Copyright 2017 mk
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

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.List;
import java.util.Map;

import mk.gdx.firebase.annotations.NestedGenericType;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.html.exceptions.NestedGenericTypeAnnotationMissingException;
import mk.gdx.firebase.html.reflection.AnnotationProcessor;

/**
 * Wraps data callback into callback with String as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so it is a need to wrap our {@link DataCallback} and parse
 * json string from javascript to Java object.
 *
 * @param <R> Result type after json string converting process
 */
class JsonDataCallback<R> implements DataCallback<String>
{
    private Class<?> wantedType;
    private DataCallback dataCallback;

    /**
     * @param dataCallback Callback, not null
     */
    public JsonDataCallback(Class<?> wantedType, DataCallback dataCallback)
    {
        this.wantedType = wantedType;
        this.dataCallback = dataCallback;
    }

    /**
     * Converts string data to java object and calls next data callback.
     *
     * @param data Data should be JSON string representation of firebase value, not null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onData(String data)
    {
        R result = JsonProcessor.<R>process(wantedType, dataCallback, data);
        dataCallback.onData(result);
    }

    @Override
    public void onError(Exception e)
    {
        dataCallback.onError(e);
    }
}
