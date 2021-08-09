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

import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSDictionary;
import org.robovm.pods.firebase.database.FIRDataSnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

class ResolverFIRDataSnapshotList {

    private ResolverFIRDataSnapshotList() {
    }

    @SuppressWarnings("unchecked")
    static List resolve(FIRDataSnapshot dataSnapshot) {
        List result = new ArrayList<>();
        if (dataSnapshot.getValue() == null) {
            throw new IllegalStateException();
        }
        NSArray<FIRDataSnapshot> nsArray = dataSnapshot.getChildren().getAllObjects();
        for (Object object : nsArray) {
            result.add(DataProcessor.iosDataToJava(((FIRDataSnapshot) object).getValue()));
        }
        return result;
    }

    static boolean shouldResolveList(FIRDataSnapshot dataSnapshot) {
        GdxFIRLogger.log("Should resolve list, "
                + "children: " + dataSnapshot.getChildren() + " - " + dataSnapshot.getChildren().getAllObjects());
        return (ClassReflection.isAssignableFrom(NSArray.class, dataSnapshot.getValue().getClass())
                || ClassReflection.isAssignableFrom(Collection.class, dataSnapshot.getValue().getClass())
                || ClassReflection.isAssignableFrom(NSDictionary.class, dataSnapshot.getValue().getClass()))
                && dataSnapshot.getChildrenCount() > 0;
    }
}
