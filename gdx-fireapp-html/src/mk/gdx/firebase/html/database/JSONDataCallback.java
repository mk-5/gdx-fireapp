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

import mk.gdx.firebase.callbacks.DataCallback;

/**
 * Wraps data callback into callback with String as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so we need to wrap our {@link DataCallback} and parse
 * json string from javascript to Java object.
 */
public class JSONDataCallback implements DataCallback<String>
{
    private Class<?> wantedType;
    private DataCallback dataCallback;

    /**
     * @param dataCallback Callback, not null
     */
    public JSONDataCallback(Class<?> wantedType, DataCallback dataCallback)
    {
        this.wantedType = wantedType;
        this.dataCallback = dataCallback;
    }

    /**
     * Converts string data to java object and calls next data callback.
     * <p>
     * FIXME - converting not working now. Example error:
     * Error: com.badlogic.gdx.utils.SerializationException: Unable to convert value to required type:
     * [ null { 	email: bob@bob.com 	username: Bob Change } { 	email: john@john.com 	username: John } ] (java.util.List)
     *
     * @param data Data should be JSON string representation of firebase value, not null.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onData(String data)
    {
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        json.setTypeName(null);
        Object o = json.fromJson(wantedType, data);
        // TODO - when wantedType is List or Map? Need to 'deserialize' each class inside.
        // TODO - own deserializer? deserialize each unknown object to hash map?
        dataCallback.onData(o);
    }

    @Override
    public void onError(Exception e)
    {
        dataCallback.onError(e);
    }

//    private class Deserializer implements Json.Serializer
//    {
//
//        @Override
//        public void write(Json json, Object object, Class knownType)
//        {
//        }
//
//        @Override
//        public Object read(Json json, JsonValue jsonData, Class type)
//        {
//
//            return null;
//        }
//    }
}
