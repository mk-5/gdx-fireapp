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

import org.robovm.apple.foundation.NSNumber;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.pods.firebase.database.FIRDataEventType;
import org.robovm.pods.firebase.database.FIRDataSnapshot;
import org.robovm.pods.firebase.database.FIRDatabase;
import org.robovm.pods.firebase.database.FIRDatabaseQuery;

import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnConnectionValidator;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class QueryConnectionStatus extends DatabaseQuery<ConnectionStatus> {
    private static final String CONNECTED_REFERENCE = ".info/connected";

    QueryConnectionStatus(Database databaseDistribution) {
        super(databaseDistribution, null);
    }

    @Override
    protected void prepare() {
        query = FIRDatabase.database().reference(CONNECTED_REFERENCE);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnConnectionValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected ConnectionStatus run() {
        final long handle = query.observeEvent(FIRDataEventType.Value, new ConnectionBlock((FuturePromise) promise));
        ((FutureListenerPromise) promise).onCancel(new CancelAction(handle, query));
        return null;
    }

    static class ConnectionBlock implements VoidBlock1<FIRDataSnapshot> {

        private FuturePromise promise;

        private ConnectionBlock(FuturePromise promise) {
            this.promise = promise;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void invoke(FIRDataSnapshot arg0) {
            boolean connected = ((NSNumber)arg0.getValue()).booleanValue();
            if (connected) {
                promise.doComplete(ConnectionStatus.CONNECTED);
            } else {
                promise.doComplete(ConnectionStatus.DISCONNECTED);
            }
        }
    }
}
