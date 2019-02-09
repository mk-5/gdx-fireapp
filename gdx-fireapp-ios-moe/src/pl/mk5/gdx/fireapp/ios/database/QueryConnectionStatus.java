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

package pl.mk5.gdx.fireapp.ios.database;

import apple.foundation.NSNumber;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import bindings.google.firebasedatabase.enums.FIRDataEventType;
import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnConnectionValidator;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Provides call to {@link FIRDatabaseReference#observeEventTypeWithBlock(long, FIRDatabaseReference.Block_observeEventTypeWithBlock)}  ()} for "".info/connected""
 */
class QueryConnectionStatus extends IosDatabaseQuery<ConnectionStatus> {
    private static final String CONNECTED_REFERENCE = ".info/connected";

    QueryConnectionStatus(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void prepare() {
        query = FIRDatabase.database().referenceWithPath(CONNECTED_REFERENCE);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnConnectionValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ConnectionStatus run() {
        final long handle = query.observeEventTypeWithBlock(FIRDataEventType.Value, new ConnectionBlock((FuturePromise) promise));
        ((FutureListenerPromise) promise).onCancel(new CancelBlockAction(query, handle));
        return null;
    }

    private static class CancelBlockAction implements Runnable {

        private final FIRDatabaseQuery query;
        private final long handle;

        private CancelBlockAction(FIRDatabaseQuery query, long handle) {
            this.query = query;
            this.handle = handle;
        }

        @Override
        public void run() {
            query.removeObserverWithHandle(handle);
        }
    }

    /**
     * Wraps {@link FuturePromise} with ios connection callback.
     */
    static class ConnectionBlock implements FIRDatabaseQuery.Block_observeEventTypeWithBlock {

        private FuturePromise promise;

        private ConnectionBlock(FuturePromise promise) {
            this.promise = promise;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void call_observeEventTypeWithBlock(FIRDataSnapshot arg0) {
            boolean connected = ((NSNumber) arg0.value()).boolValue();
            if (connected) {
                promise.doComplete(ConnectionStatus.CONNECTED);
            } else {
                promise.doComplete(ConnectionStatus.DISCONNECTED);
            }
        }
    }
}
