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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Wraps {@link FuturePromise} with {@link DatabaseReference.CompletionListener}
 */
class QueryCompletionListener implements DatabaseReference.CompletionListener {

    private FuturePromise promise;

    QueryCompletionListener(FuturePromise promise) {
        this.promise = promise;
    }

    @Override
    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
        if (promise == null) return;
        if (databaseError != null) {
            promise.doFail(databaseError.toException());
        } else {
            promise.doComplete(null);
        }
    }
}
