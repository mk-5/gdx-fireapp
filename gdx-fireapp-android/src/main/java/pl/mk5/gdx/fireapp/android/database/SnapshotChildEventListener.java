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
import android.support.annotation.Nullable;

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;

class SnapshotChildEventListener implements ChildEventListener {

    private final DataSnapshotResolver dataSnapshotResolver;
    private final Array<ChildEventType> eventTypes;

    SnapshotChildEventListener(Class dataType, ChildEventType[] eventTypes, ConverterPromise promise) {
        this.dataSnapshotResolver = new DataSnapshotResolver(dataType, promise);
        this.eventTypes = new Array<>(eventTypes);
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if (!eventTypes.contains(ChildEventType.ADDED, true)) return;
        dataSnapshotResolver.resolve(dataSnapshot);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if (!eventTypes.contains(ChildEventType.CHANGED, true)) return;
        dataSnapshotResolver.resolve(dataSnapshot);
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
        if (!eventTypes.contains(ChildEventType.REMOVED, true)) return;
        dataSnapshotResolver.resolve(dataSnapshot);
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if (!eventTypes.contains(ChildEventType.MOVED, true)) return;
        dataSnapshotResolver.resolve(dataSnapshot);
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        dataSnapshotResolver.getPromise().doFail(databaseError.toException());
    }
}
