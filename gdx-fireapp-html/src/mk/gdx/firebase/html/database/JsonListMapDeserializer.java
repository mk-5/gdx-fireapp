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

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

/**
 * Provides deserialization of json without the class descriptor field inside the document.
 */
class JsonListMapDeserializer extends Json.ReadOnlySerializer {

    private Class<?> objectType;

    /**
     * @param objectType The type of 'Object' inside json string.
     */
    JsonListMapDeserializer(Class<?> objectType) {
        this.objectType = objectType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object read(Json json, JsonValue jsonData, Class type) {
        return processJsonValue(json, jsonData);
    }

    private Object processJsonValue(Json json, JsonValue jsonData) {
        if (jsonData.isObject()) {
            return processObject(json, jsonData);
        } else if (jsonData.isValue()) {
            return processValue(jsonData);
        } else {
            return null;
        }
    }

    private Object processObject(Json json, JsonValue jsonData) {
        return json.fromJson(this.objectType, jsonData.toJson(JsonWriter.OutputType.json));
    }

    private Object processValue(JsonValue jsonData) {
        if (jsonData.isBoolean())
            return jsonData.asBoolean();
        else if (jsonData.isDouble())
            return jsonData.asDouble();
        else if (jsonData.isLong())
            return jsonData.asLong();
        else if (jsonData.isNull())
            return null;
        else if (jsonData.isString())
            return jsonData.asString();
        return null;
    }
}
