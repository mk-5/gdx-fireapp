/*
 * Copyright 2019 mk
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

import android.support.annotation.NonNull;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;
import java.util.List;

import pl.mk5.gdx.fireapp.promises.ConverterPromise;

class SnapshotValueListener implements ValueEventListener {

    private Class dataType;
    private ConverterPromise promise;

    SnapshotValueListener(Class dataType, ConverterPromise promise) {
        this.promise = promise;
        this.dataType = dataType;
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if (ClassReflection.isAssignableFrom(List.class, dataType) && dataSnapshot.getValue() == null) {
            promise.doComplete(Collections.emptyList());
        } else if (ClassReflection.isAssignableFrom(List.class, dataType)
                && ResolverDataSnapshotList.shouldResolveOrderBy(dataType, dataSnapshot)) {
            promise.doComplete(ResolverDataSnapshotList.resolve(dataSnapshot));
        } else {
            promise.doComplete(dataSnapshot.getValue());
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        promise.doFail(databaseError.toException());
    }
}
