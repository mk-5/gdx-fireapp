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

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.database.DataListenersManager;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnDataValidator;
import mk.gdx.firebase.promises.ConverterPromise;

/**
 * Provides call to {@link com.google.firebase.database.Query#addValueEventListener(ValueEventListener)}.
 */
class QueryOnDataChange<R> extends AndroidDatabaseQuery<R> {

    private final static DataListenersManager<ValueEventListener> DATA_PROMISES_MANAGER = new DataListenersManager<>();

    QueryOnDataChange(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnDataValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R run() {
        if (arguments.get(0) != null) {
            DataChangeValueListener dataChangeListener = new DataChangeValueListener<>((Class) arguments.get(0), (ConverterPromise) promise, orderByClause);
            DATA_PROMISES_MANAGER.addNewListener(databasePath, dataChangeListener);
            filtersProvider.applyFiltering().addValueEventListener(dataChangeListener);
        } else {
            Array<ValueEventListener> listeners = DATA_PROMISES_MANAGER.getListeners(databasePath);
            for (ValueEventListener v : listeners) {
                query.removeEventListener(v);
            }
            DATA_PROMISES_MANAGER.removeListenersForPath(databasePath);
        }
        return null;
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     *
     * @param <T> Class of object that we want to listen for change. For ex. List
     * @param <R> Return type of object that we want to listen for change. For ex. List<MyClass>
     */
    private static class DataChangeValueListener<T, R extends T> implements ValueEventListener {

        private Class<T> dataType;
        private ConverterPromise<T, R> promise;
        private OrderByClause orderByClause;

        DataChangeValueListener(Class<T> dataType, ConverterPromise<T, R> promise, OrderByClause orderByClause) {
            this.promise = promise;
            this.dataType = dataType;
            this.orderByClause = orderByClause;
        }


        @Override
        @SuppressWarnings("unchecked")
        public void onDataChange(DataSnapshot dataSnapshot) {
            ResolverDataListenerOnDataChange.resolve(dataType, orderByClause, dataSnapshot, promise);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            promise.doFail(databaseError.toException());
        }


    }
}
