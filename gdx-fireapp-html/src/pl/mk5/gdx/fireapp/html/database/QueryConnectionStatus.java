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

package pl.mk5.gdx.fireapp.html.database;

import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnConnectionValidator;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

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
        onConnect((FuturePromise) promise);
        ((FutureListenerPromise) promise).onCancel(new Runnable() {
            @Override
            public void run() {
                offConnect();
            }
        });
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new OnConnectionValidator();
    }

    /**
     * Set connected/disconnected listener.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state">https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state</a>
     * <p>
     * TODO - check doComplete with enum here
     *
     * @param promise FuturePromise, not null
     */
    public static native void onConnect(FuturePromise promise) /*-{
        $wnd.firebase.database().ref(".info/connected").on("value", function(snap){
            if (snap.val() === true) {
                promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Lmk/gdx/firebase/database/ConnectionStatus)(ConnectionStatus.CONNECTED);
            } else {
                promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Lmk/gdx/firebase/database/ConnectionStatus)(ConnectionStatus.DISCONNECTED);
            }
        });
    }-*/;

    public static native void offConnect() /*-{
        $wnd.firebase.database().ref(".info/connected").off("value");
    }-*/;

}
