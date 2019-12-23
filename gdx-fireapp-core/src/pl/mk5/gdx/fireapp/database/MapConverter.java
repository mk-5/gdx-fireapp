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

package pl.mk5.gdx.fireapp.database;

import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

public class MapConverter implements FirebaseMapConverter {

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
