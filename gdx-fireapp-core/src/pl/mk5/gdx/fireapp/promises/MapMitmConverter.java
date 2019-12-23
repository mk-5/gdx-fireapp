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

package pl.mk5.gdx.fireapp.promises;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.database.FirebaseMapConverter;
import pl.mk5.gdx.fireapp.database.JavaCoreClassDetector;
import pl.mk5.gdx.fireapp.exceptions.MapConversionNotPossibleException;

class MapMitmConverter {
    private static final String CANT_DO_MAP_CONVERSION_FROM_TYPE = "Can't do map conversion from type: ";

    private final FirebaseMapConverter mapConverter;

    /**
     * @param mapConverter Map converter implementation, not null
     */
    MapMitmConverter(FirebaseMapConverter mapConverter) {
        this.mapConverter = mapConverter;
    }

    @SuppressWarnings("unchecked")
    Object doMitmConversion(Class<?> wantedType, Object data) {
        if (data == null) return null;
        // If client tell us to do map conversion and is not possible - throw exception.
        if (!isConversionPossible(data))
            throw new MapConversionNotPossibleException(CANT_DO_MAP_CONVERSION_FROM_TYPE + data.getClass().getName());
        // Check if Map is covered by data in some way, otherwise throw exception.
        if (ClassReflection.isAssignableFrom(Map.class, data.getClass())) {
            // First case - Map, do simple conversion.
            data = mapConverter.convert((Map) data, wantedType);
        } else if (ClassReflection.isAssignableFrom(List.class, data.getClass())) {
            // Second case - List, go through all elements and convert it to map.
            for (int i = ((List) data).size() - 1; i >= 0; i--) {
                Object element = ((List) data).get(i);
                if (element == null || !ClassReflection.isAssignableFrom(Map.class, element.getClass())) {
                    GdxFIRLogger.log("@MapConversion: One of list value are not a Map - value was dropped. Element type: " + (element != null ? element.getClass() : "null"));
                    ((List) data).remove(i);
                } else {
                    ((List) data).set(i, mapConverter.convert((Map) element, wantedType));
                }
            }
        }
        return data;
    }

    boolean isConversionPossible(Object data) {
        return ClassReflection.isAssignableFrom(Map.class, data.getClass()) || ClassReflection.isAssignableFrom(List.class, data.getClass());
    }

    boolean isPojo(Class<?> type) {
        return !JavaCoreClassDetector.isJavaCoreClass(type)
                && !ClassReflection.isAssignableFrom(List.class, type)
                && !ClassReflection.isAssignableFrom(Map.class, type);
    }

}
