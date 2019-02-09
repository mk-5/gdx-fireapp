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

package pl.mk5.gdx.fireapp.deserialization;

import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

/**
 * Converts {@code Map<String, Object>} to POJO.
 */
public class MapConverter implements FirebaseMapConverter {

    /**
     * Transforms {@code Map<String,Object>} to {@code T object}.
     * <p>
     * Transformation flow is as follow:
     * <ul>
     * <li>Transform {@code map} to Json string by {@link Json#toJson(Object)}
     * <li>Transform Json string to POJO object by {@link Json#fromJson(Class, String)}
     * </ul>
     *
     * @param map        Map which we want to transform.
     * @param wantedType Class type we want to get
     * @param <T>        Generic type of class we want to get. Needed if type we want have nested generic type.
     * @return Deserialized object, may be null
     */
    public <T> T convert(Map<String, Object> map, Class<T> wantedType) {
        try {
            String jsonString = new Json().toJson(map);
            Json json = new Json();
            json.setIgnoreUnknownFields(true);
            json.setTypeName(null);
            json.setQuoteLongValues(true);
            return json.fromJson(wantedType, jsonString);
        } catch (Exception e) {
            GdxFIRLogger.error("Can't deserialize Map to " + wantedType.getSimpleName(), e);
            return null;
        }
    }

    /**
     * Transforms {@code object} to Map.
     * <p>
     * Should be inverse of {@link #convert(Map, Class)}
     *
     * @param object Should be Pojo
     * @return Serialized object, may be null
     */
    @Override
    @SuppressWarnings("unchecked")
    public Map<String, Object> unConvert(Object object) {
        try {
            String jsonString = new Json().toJson(object);
            Json json = new Json();
            json.setIgnoreUnknownFields(true);
            json.setTypeName(null);
            json.setQuoteLongValues(true);
            return json.fromJson(HashMap.class, jsonString);
        } catch (Exception e) {
            GdxFIRLogger.error("Can't serialize " + object.getClass().getSimpleName() + " to Map.", e);
            return null;
        }
    }
}
