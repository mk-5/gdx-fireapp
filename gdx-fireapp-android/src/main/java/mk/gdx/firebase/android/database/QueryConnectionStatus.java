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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.database.ConnectionStatus;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnConnectionValidator;
import mk.gdx.firebase.promises.FutureListenerPromise;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides asking for connection status.
 */
class QueryConnectionStatus extends AndroidDatabaseQuery<ConnectionStatus> {
    private static final String CONNECTED_REFERENCE = ".info/connected";
    private static final String CONNECTION_LISTENER_CANCELED = "Connection listener was canceled";

    QueryConnectionStatus(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void prepare() {
        query = FirebaseDatabase.getInstance().getReference(CONNECTED_REFERENCE);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnConnectionValidator();
    }

    @Override
    protected ConnectionStatus run() {
        final ConnectionValueListener valueListener = new ConnectionValueListener((FuturePromise) promise);
        query.addValueEventListener(valueListener);
        ((FutureListenerPromise) promise).onCancel(new CancelListenerAction(valueListener, query));
        return null;
    }

    private static class CancelListenerAction implements Runnable {

        private final ConnectionValueListener valueListener;
        private final Query query;


        private CancelListenerAction(ConnectionValueListener valueListener, Query query) {
            this.valueListener = valueListener;
            this.query = query;
        }

        @Override
        public void run() {
            query.removeEventListener(valueListener);
        }
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     * and getting information from {@code .info/connected} path.
     */
    private static class ConnectionValueListener implements ValueEventListener {

        private FuturePromise promise;

        ConnectionValueListener(FuturePromise promise) {
            this.promise = promise;
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (promise == null) return;
            Boolean connected = dataSnapshot.getValue(Boolean.class);
            if (connected != null && connected) {
                promise.doComplete(ConnectionStatus.CONNECTED);
            } else {
                promise.doComplete(ConnectionStatus.DISCONNECTED);
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onCancelled(DatabaseError databaseError) {
            GdxFIRLogger.log(CONNECTION_LISTENER_CANCELED, databaseError.toException());
            promise.doFail(CONNECTION_LISTENER_CANCELED, databaseError.toException());
        }
    }
}
