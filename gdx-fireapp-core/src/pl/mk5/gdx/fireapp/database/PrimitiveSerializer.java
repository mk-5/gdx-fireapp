/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.database;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

class PrimitiveSerializer implements Json.Serializer {
    @Override
    public void write(Json json, Object object, Class knownType) {
        if (!object.getClass().isPrimitive()) {
            throw new IllegalStateException();
        }
        json.writeValue(object);
    }

    @Override
    public Object read(Json json, JsonValue jsonData, Class type) {
        try {
            String value = jsonData.child().asString();
            if (type == null || type == float.class || type == Float.class)
                return Float.parseFloat(value);
            if (type == int.class || type == Integer.class) return Integer.parseInt(value);
            if (type == boolean.class || type == Boolean.class) return Boolean.parseBoolean(value);
            if (type == long.class || type == Long.class) return Long.parseLong(value);
            if (type == double.class || type == Double.class) return Double.parseDouble(value);
            if (type == short.class || type == Short.class) return Short.parseShort(value);
            if (type == byte.class || type == Byte.class) return Byte.parseByte(value);
        } catch (NumberFormatException exception) {
            throw new RuntimeException("Error while deserialize primitive value", exception);
        }
        throw new IllegalStateException();
    }
}
