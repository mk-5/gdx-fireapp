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

import com.badlogic.gdx.utils.LongArray;

import apple.foundation.NSNumber;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import bindings.google.firebasedatabase.enums.FIRDataEventType;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnConnectionValidator;
import mk.gdx.firebase.listeners.ConnectedListener;

/**
 * Provides call to {@link FIRDatabaseReference#observeEventTypeWithBlock(long, FIRDatabaseReference.Block_observeEventTypeWithBlock)}  ()} for "".info/connected""
 */
class QueryConnectionStatus extends IosDatabaseQuery<Void> {
    private static final String CONNECTED_REFERENCE = ".info/connected";
    private static final LongArray handles = new LongArray();


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
    protected Void run() {
        synchronized (handles) {
            if (arguments.get(0) != null) {
                handles.add(query.observeEventTypeWithBlock(FIRDataEventType.Value, new ConnectionBlock((ConnectedListener) arguments.get(0))));
            } else {
                for (int i = handles.size - 1; i >= 0; i--) {
                    query.removeObserverWithHandle(handles.get(i));
                }
                handles.clear();
            }
        }
        return null;
    }

    /**
     * Wraps {@link ConnectedListener} with ios connection callback.
     */
    private class ConnectionBlock implements FIRDatabaseQuery.Block_observeEventTypeWithBlock {

        private ConnectedListener connectedListener;

        private ConnectionBlock(ConnectedListener connectedListener) {
            this.connectedListener = connectedListener;
        }

        @Override
        public void call_observeEventTypeWithBlock(FIRDataSnapshot arg0) {
            boolean connected = ((NSNumber) arg0.value()).boolValue();
            if (connected) {
                connectedListener.onConnect();
            } else {
                connectedListener.onDisconnect();
            }
        }
    }
}
