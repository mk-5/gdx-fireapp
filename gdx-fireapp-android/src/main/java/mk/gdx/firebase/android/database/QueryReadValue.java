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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.ReadValueValidator;

/**
 * Provides call to {@link com.google.firebase.database.Query#addListenerForSingleValueEvent(ValueEventListener)}.
 */
class QueryReadValue extends AndroidDatabaseQuery<Void> {

    QueryReadValue(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new ReadValueValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run() {
        filtersProvider.applyFiltering()
                .addListenerForSingleValueEvent(new SingleValueListener((Class) arguments.get(0), (DataCallback) arguments.get(1), orderByClause));
        return null;
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)}
     *
     * @param <T> Class of object that we want to listen for change. For ex. List
     * @param <E> Return type of object that we want to listen for change. For ex. List<MyClass>
     */
    private class SingleValueListener<T, E extends T> implements ValueEventListener {

        private Class<T> dataType;
        private DataCallback<E> callback;
        private OrderByClause orderByClause;

        private SingleValueListener(Class<T> dataType, DataCallback<E> callback, OrderByClause orderByClause) {
            this.dataType = dataType;
            this.callback = callback;
            this.orderByClause = orderByClause;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ResolverDataCallbackOnData.resolve(dataType, orderByClause, dataSnapshot, callback);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            callback.onError(databaseError.toException());
        }
    }
}
