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

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnDataValidator;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;

/**
 * Provides call to {@link com.google.firebase.database.Query#addValueEventListener(ValueEventListener)}.
 */
class QueryOnDataChange<R> extends AndroidDatabaseQuery<R> {

    QueryOnDataChange(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnDataValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R run() {
        SnapshotValueListener dataChangeListener = new SnapshotValueListener((Class) arguments.get(0), (ConverterPromise) promise);
        filtersProvider.applyFiltering().addValueEventListener(dataChangeListener);
        ((FutureListenerPromise) promise).onCancel(new CancelListenerAction(dataChangeListener, query));
        return null;
    }

    private static class CancelListenerAction implements Runnable {

        private final ValueEventListener listener;
        private final Query query;

        private CancelListenerAction(ValueEventListener listener, Query query) {
            this.listener = listener;
            this.query = query;
        }

        @Override
        public void run() {
            query.removeEventListener(listener);
        }
    }
}
