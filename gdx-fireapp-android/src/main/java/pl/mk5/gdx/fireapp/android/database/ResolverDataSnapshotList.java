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

package pl.mk5.gdx.fireapp.android.database;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Gets data from DatabaseSnapshot with ordering preserved.
 */
class ResolverDataSnapshotList {

    private ResolverDataSnapshotList() {
        //
    }

    /**
     * Gets children's from DataSnapshot and puts them into new ArrayList.
     *
     * @param dataSnapshot DataSnapshot, not null
     * @return New instance of List with ordering preserved from DataSnapshot
     */
    @SuppressWarnings("unchecked")
    static List resolve(DataSnapshot dataSnapshot) {
        if (dataSnapshot.getValue() == null) {
            throw new IllegalStateException();
        }
        List result = new ArrayList<>();
        Iterable<DataSnapshot> dataSnapshots;
        if (ClassReflection.isAssignableFrom(Map.class, dataSnapshot.getValue().getClass())) {
            dataSnapshots = ((Map) dataSnapshot.getValue()).values();
        } else {
            dataSnapshots = dataSnapshot.getChildren();
        }
        for (Object o : dataSnapshots) {
            if (o instanceof DataSnapshot) {
                result.add(((DataSnapshot) o).getValue());
            } else {
                result.add(o);
            }
        }
        return result;
    }

    /**
     * Decides if DataSnapshot value should be converted to ordered list.
     *
     * @param dataType     Predicted data type of given snapshot value, not null
     * @param dataSnapshot DataSnapshot to check, not null
     * @return True if ordering should be preserved
     */
    static boolean shouldResolveOrderBy(Class<?> dataType, DataSnapshot dataSnapshot) {
        return (ClassReflection.isAssignableFrom(List.class, dataType) || ClassReflection.isAssignableFrom(Map.class, dataType))
                && dataSnapshot.getChildrenCount() > 0;
    }
}
