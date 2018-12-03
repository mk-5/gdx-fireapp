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

package mk.gdx.firebase.ios.database;

import com.badlogic.gdx.utils.Array;

import apple.foundation.NSError;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.enums.FIRDataEventType;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnDataValidator;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Provides call to {@link FIRDatabaseQuery#observeEventTypeWithBlockWithCancelBlock(long, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_2)}.
 */
class QueryOnDataChange extends IosDatabaseQuery<Void> {
    private static final DataObserversManager observersManager = new DataObserversManager();

    QueryOnDataChange(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnDataValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run() {
        if (arguments.get(1) != null) {
            long handle = filtersProvider.applyFiltering().observeEventTypeWithBlockWithCancelBlock(FIRDataEventType.Value,
                    new DataChangeBlock((Class) arguments.get(0), orderByClause, (DataChangeListener) arguments.get(1)),
                    new DataChangeCancelBlock((DataChangeListener) arguments.get(1)));
            observersManager.addNewListener(databasePath, handle);
        } else {
            if (observersManager.hasListeners(databasePath)) {
                Array<Long> handles = observersManager.getListeners(databasePath);
                for (Long handle : handles)
                    query.removeObserverWithHandle(handle);
                observersManager.removeListenersForPath(databasePath);
            }
        }
        return null;
    }

    /**
     * Observer for data change. Wraps {@code DataChangeListener}
     */
    private class DataChangeBlock implements FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1 {

        private Class type;
        private DataChangeListener dataChangeListener;
        private OrderByClause orderByClause;

        private DataChangeBlock(Class type, OrderByClause orderByClause, DataChangeListener dataChangeListener) {
            this.type = type;
            this.orderByClause = orderByClause;
            this.dataChangeListener = dataChangeListener;
        }


        @Override
        public void call_observeEventTypeWithBlockWithCancelBlock_1(FIRDataSnapshot arg0) {
            if (arg0.value() == null) {
                dataChangeListener.onCanceled(new Exception(GIVEN_DATABASE_PATH_RETURNED_NULL_VALUE));
            } else {
                Object data = null;
                try {
                    if (!ResolverFIRDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, type, arg0)) {
                        data = DataProcessor.iosDataToJava(arg0.value(), type);
                    } else {
                        data = ResolverFIRDataSnapshotOrderBy.resolve(arg0);
                    }
                } catch (Exception e) {
                    dataChangeListener.onCanceled(e);
                    return;
                }
                dataChangeListener.onChange(data);
            }
        }
    }

    /**
     * Observer for data change cancel. Wraps {@code DataChangeListener}
     */
    private class DataChangeCancelBlock implements FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_2 {

        private DataChangeListener dataChangeListener;

        private DataChangeCancelBlock(DataChangeListener dataChangeListener) {
            this.dataChangeListener = dataChangeListener;
        }

        @Override
        public void call_observeEventTypeWithBlockWithCancelBlock_2(NSError arg0) {
            dataChangeListener.onCanceled(new Exception(arg0.localizedDescription()));
        }
    }
}
