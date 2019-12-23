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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.Query;

import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnChildValidator;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;

class QueryOnChildChange<R> extends AndroidDatabaseQuery<R> {

    QueryOnChildChange(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnChildValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R run() {
        SnapshotChildEventListener childEventListener = new SnapshotChildEventListener((Class) arguments.get(0), (ChildEventType[]) arguments.get(1), (ConverterPromise) promise);
        filtersProvider.applyFiltering().addChildEventListener(childEventListener);
        ((FutureListenerPromise) promise).onCancel(new CancelListenerAction(childEventListener, query));
        return null;
    }

    private static class CancelListenerAction implements Runnable {

        private final ChildEventListener listener;
        private final Query query;

        private CancelListenerAction(ChildEventListener listener, Query query) {
            this.listener = listener;
            this.query = query;
        }

        @Override
        public void run() {
            query.removeEventListener(listener);
        }
    }
}
