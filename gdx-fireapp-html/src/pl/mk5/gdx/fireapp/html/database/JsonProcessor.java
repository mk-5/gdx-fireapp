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

package pl.mk5.gdx.fireapp.html.database;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Process json string to generic type.
 */
class JsonProcessor {

    private JsonProcessor() {
        //
    }

    /**
     * Converts json string into java object.
     * <p>
     * If wantedType is exactly List.class, ArrayList instance will be created instead
     * If wantedType is exactly Map.class, HashMap instance will be created instead
     *
     * @param wantedType Wanted type
     * @param jsonString Json string data
     * @param <R>        Return type
     * @return Instance of wanted type parsed from given json string, not null
     */
    static <R> R process(Class<?> wantedType, String jsonString) {
        Json json = new Json();
        json.setIgnoreUnknownFields(true);
        json.setTypeName(null);
        R result = null;
        if (ClassReflection.isAssignableFrom(List.class, wantedType)
                || ClassReflection.isAssignableFrom(Map.class, wantedType)) {
            if (wantedType == List.class) {
                wantedType = ArrayList.class;
            } else if (wantedType == Map.class) {
                wantedType = HashMap.class;
            }
            json.setDefaultSerializer(new JsonListMapDeserializer(HashMap.class));
            result = (R) json.fromJson(wantedType, jsonString);
        } else {
            result = (R) json.fromJson(wantedType, jsonString);
        }
        return result;
    }
}
