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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnConnectionValidator;
import mk.gdx.firebase.listeners.ConnectedListener;

/**
 * Provides connection-status ask javascript execution.
 */
class QueryConnectionStatus extends GwtDatabaseQuery {
    QueryConnectionStatus(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void prepare() {
        // Do nothing
    }

    @Override
    protected void runJS() {
        onConnect((ConnectedListener) arguments.get(0));
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnConnectionValidator();
    }

    /**
     * Set connected/disconnected listener.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state">https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state</a>
     *
     * @param listener Connected listener, not null
     */
    public static native void onConnect(ConnectedListener listener) /*-{
        $wnd.firebase.database().ref(".info/connected").on("value", function(snap){
            if (snap.val() === true) {
                listener.@mk.gdx.firebase.listeners.ConnectedListener::onConnect()();
            } else {
                listener.@mk.gdx.firebase.listeners.ConnectedListener::onDisconnect()();
            }
        });
    }-*/;

}
