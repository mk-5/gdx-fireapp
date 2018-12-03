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

package mk.gdx.firebase.ios.database;

import java.util.ArrayList;
import java.util.List;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSMutableArray;

/**
 * Transforms {@code NSArray} and {@code List}.
 * <p>
 * Helper class that provides transformation from {@link NSArray} to {@link List}
 * and {@link List} to {@link NSArray}
 */
class NSArrayHelper {

    /**
     * Transforms {@code nsArray} to java List object.
     * <p>
     * {@link DataProcessor#iosDataToJava(Object)} method will be call to transform each {@code nsArray} value.
     *
     * @param nsArray {@link NSArray} you want to transform.
     * @return {@code nsArray} object  transformed to {@code List} with normal java Object instances inside, not null.
     */
    @SuppressWarnings("unchecked")
    static List toList(NSArray<NSObject> nsArray) {
        List list = new ArrayList();
        if (nsArray.size() > 0)
            for (Object value : nsArray) {
                list.add(DataProcessor.iosDataToJava(value));
            }
        return list;
    }

    /**
     * Transforms {@code list} to NSArray object.
     * <p>
     * {@link DataProcessor#javaDataToIos(Object)} method will be call to transform each {@code list} value.
     *
     * @param list {@link List} you want to transform.
     * @return {@code list} object transformed to {@code NSMutableArray} with NSObject instances inside, not null.
     */
    @SuppressWarnings("unchecked")
    static NSMutableArray<NSObject> toArray(List list) {
        NSMutableArray<NSObject> nsMutableArray = (NSMutableArray<NSObject>) NSMutableArray.alloc().init();
        for (Object value : list) {
            nsMutableArray.add(DataProcessor.javaDataToIos(value));
        }
        return nsMutableArray;
    }
}