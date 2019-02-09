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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.ReadValueValidator;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;

/**
 * Provides call to {@link com.google.firebase.database.Query#addListenerForSingleValueEvent(ValueEventListener)}.
 */
class QueryReadValue<R> extends AndroidDatabaseQuery<R> {

    QueryReadValue(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new ReadValueValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R run() {
        filtersProvider.applyFiltering()
                .addListenerForSingleValueEvent(new SingleValueListener((Class) arguments.get(0), (ConverterPromise) promise, orderByClause));
        return null;
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)}
     *
     * @param <T> Class of object that we want to listen for change. For ex. List
     * @param <E> Return type of object that we want to listen for change. For ex. List<MyClass>
     */
    private static class SingleValueListener<T, E extends T> implements ValueEventListener {

        private Class<T> dataType;
        private ConverterPromise<T, E> promise;
        private OrderByClause orderByClause;

        private SingleValueListener(Class<T> dataType, ConverterPromise<T, E> promise, OrderByClause orderByClause) {
            this.dataType = dataType;
            this.promise = promise;
            this.orderByClause = orderByClause;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ResolverFuturePromiseOnData.resolve(dataType, orderByClause, dataSnapshot, promise);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            promise.doFail(databaseError.toException());
        }
    }
}
