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

package pl.mk5.gdx.fireapp.ios.database;

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSMutableArray;
import org.robovm.apple.foundation.NSObject;

import java.util.ArrayList;
import java.util.List;

class NSArrayHelper {

    @SuppressWarnings("unchecked")
    static List toList(NSArray<NSObject> nsArray) {
        List list = new ArrayList();
        if (nsArray.size() > 0)
            for (Object value : nsArray) {
                list.add(DataProcessor.iosDataToJava(value));
            }
        return list;
    }

    @SuppressWarnings("unchecked")
    static NSMutableArray<NSObject> toArray(List list) {
        NSMutableArray<NSObject> nsMutableArray = new NSMutableArray<>();
        for (Object value : list) {
            nsMutableArray.add(DataProcessor.javaDataToIos(value));
        }
        return nsMutableArray;
    }
}