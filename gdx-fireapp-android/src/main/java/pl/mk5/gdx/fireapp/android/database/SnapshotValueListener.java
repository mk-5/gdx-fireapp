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

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import pl.mk5.gdx.fireapp.promises.ConverterPromise;

class SnapshotValueListener implements ValueEventListener {

    private final DataSnapshotResolver dataSnapshotResolver;

    SnapshotValueListener(Class dataType, ConverterPromise promise) {
        this.dataSnapshotResolver = new DataSnapshotResolver(dataType, promise);
    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        dataSnapshotResolver.resolve(dataSnapshot);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        dataSnapshotResolver.getPromise().doFail(databaseError.toException());
    }
}
