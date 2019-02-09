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

import java.util.Map;

/**
 * Covers interface for converting database Maps to POJO
 */
public interface FirebaseMapConverter {

    /**
     * Transforms {@code Map<String,Object>} to {@code T object}.
     * <p>
     *
     * @param map        Map which we want to transform.
     * @param wantedType Type we want to get
     * @param <T>        Generic type of class we want to get. Needed if type we want have nested generic type.
     * @return Deserialized object, may be null
     */
    <T> T convert(Map<String, Object> map, Class<T> wantedType);

    /**
     * Transforms {@code object} to Map.
     * <p>
     * Should be inverse of {@link #convert(Map, Class)}
     *
     * @param object Should be Pojo
     * @return Serialized object, may be null
     */
    Map<String, Object> unConvert(Object object);
}
