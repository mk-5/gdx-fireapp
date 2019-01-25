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

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.ArrayList;
import java.util.List;

import apple.foundation.NSArray;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import mk.gdx.firebase.database.OrderByClause;

/**
 * Gets data from FIRDataSnapshot with ordering preserved.
 */
class ResolverFIRDataSnapshotOrderBy {

    /**
     * Gets children's from FIRDataSnapshot and puts them into new ArrayList.
     *
     * @param dataSnapshot DataSnapshot, not null
     * @return New instance of List with ordering preserved from DataSnapshot
     */
    @SuppressWarnings("unchecked")
    static List resolve(FIRDataSnapshot dataSnapshot) {
        List result = new ArrayList<>();
        NSArray nsArray = dataSnapshot.children().allObjects();
        for (Object object : nsArray) {
            if (object instanceof FIRDataSnapshot) {
                result.add(DataProcessor.iosDataToJava(((FIRDataSnapshot) object).value()));
            } else {
                result.add(DataProcessor.iosDataToJava(object));
            }
        }
        return result;
    }

    /**
     * Decides if FIRDataSnapshot value should be converted to ordered list.
     *
     * @param orderByClause OrderByClause that was applied, may be null
     * @param dataType      Predicted data type of given snapshot value, not null
     * @param dataSnapshot  FIRDataSnapshot to check, not null
     * @return True if ordering should be preserved
     */
    static boolean shouldResolveOrderBy(OrderByClause orderByClause, Class<?> dataType, FIRDataSnapshot dataSnapshot) {
        return orderByClause != null && ClassReflection.isAssignableFrom(List.class, dataType)
                && dataSnapshot.childrenCount() > 0;
    }
}
