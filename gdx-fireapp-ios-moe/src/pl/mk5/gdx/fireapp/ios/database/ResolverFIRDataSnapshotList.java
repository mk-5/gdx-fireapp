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

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.ArrayList;
import java.util.List;

import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import pl.mk5.gdx.fireapp.GdxFIRLogger;

class ResolverFIRDataSnapshotList {

    @SuppressWarnings("unchecked")
    static List resolve(FIRDataSnapshot dataSnapshot) {
        List result = new ArrayList<>();
        if (dataSnapshot.value() == null) {
            throw new IllegalStateException();
        }
        NSArray nsArray;
        if (ClassReflection.isAssignableFrom(NSArray.class, dataSnapshot.value().getClass())) {
            nsArray = dataSnapshot.children().allObjects();
        } else if (ClassReflection.isAssignableFrom(NSDictionary.class, dataSnapshot.value().getClass())) {
            nsArray = ((NSDictionary) dataSnapshot.value()).allValues();
        } else {
            throw new IllegalStateException();
        }
        for (Object object : nsArray) {
            if (object instanceof FIRDataSnapshot) {
                result.add(DataProcessor.iosDataToJava(((FIRDataSnapshot) object).value()));
            } else {
                result.add(DataProcessor.iosDataToJava(object));
            }
        }
        return result;
    }

    static boolean shouldResolveList(FIRDataSnapshot dataSnapshot) {
        GdxFIRLogger.log("Should resolve list, "
                + "children: " + dataSnapshot.childrenCount());
        return (ClassReflection.isAssignableFrom(NSArray.class, dataSnapshot.value().getClass())
                || ClassReflection.isAssignableFrom(NSDictionary.class, dataSnapshot.value().getClass()))
                && dataSnapshot.childrenCount() > 0;
    }
}
