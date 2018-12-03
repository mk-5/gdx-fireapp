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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnConnectionValidator;
import mk.gdx.firebase.listeners.ConnectedListener;

/**
 * Provides asking for connection status.
 */
class QueryConnectionStatus extends AndroidDatabaseQuery<Void> {
    private static final String CONNECTED_REFERENCE = ".info/connected";
    private static final String CONNECTION_LISTENER_CANCELED = "Connection listener was canceled";

    private static final Array<ConnectionValueListener> listeners = new Array<>();

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
    protected Void run() {
        synchronized (listeners) {
            if (arguments.get(0) != null) {
                listeners.add(new ConnectionValueListener((ConnectedListener) arguments.get(0)));
                query.addValueEventListener(listeners.peek());
            } else if (arguments.get(0) == null && listeners.size > 0) {
                for (ConnectionValueListener listener : listeners)
                    query.removeEventListener(listener);
                listeners.clear();
            }
        }
        return null;
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     * and getting information from {@code .info/connected} path.
     */
    private static class ConnectionValueListener implements ValueEventListener {

        private ConnectedListener connectedListener;

        ConnectionValueListener(ConnectedListener connectedListener) {
            this.connectedListener = connectedListener;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (connectedListener == null) return;
            boolean connected = dataSnapshot.getValue(Boolean.class);
            if (connected) {
                connectedListener.onConnect();
            } else {
                connectedListener.onDisconnect();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            GdxFIRLogger.log(CONNECTION_LISTENER_CANCELED, databaseError.toException());
        }
    }
}
