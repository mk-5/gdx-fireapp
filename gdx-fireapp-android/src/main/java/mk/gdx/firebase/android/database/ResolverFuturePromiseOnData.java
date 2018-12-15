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

package mk.gdx.firebase.android.database;

import com.google.firebase.database.DataSnapshot;

import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Resolves data callback with ordering preserved.
 */
class ResolverFuturePromiseOnData {

    private ResolverFuturePromiseOnData() {
        //
    }

    // TODO - docs
    @SuppressWarnings("unchecked")
    public static <T, E extends T> void resolve(Class<T> dataType, OrderByClause orderByClause, DataSnapshot dataSnapshot, FuturePromise<E> promise) {
        if (ResolverDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, dataSnapshot)) {
            promise.doComplete((E) ResolverDataSnapshotOrderBy.resolve(dataSnapshot));
        } else {
            promise.doComplete((E) dataSnapshot.getValue());
        }
    }
}
