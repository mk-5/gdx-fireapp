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

import com.badlogic.gdx.utils.Array;

import mk.gdx.firebase.database.DataListenersManager;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Keeps reference do DataListenersManager and provides static methods for javascript calls.
 */
class GwtDataListenersManager {
    private static final DataListenersManager<DataChangeListener> listenersManager = new DataListenersManager<DataChangeListener>();

    private GwtDataListenersManager() {
        //
    }

    /**
     * @param refPath  Reference path, not null
     * @param listener Listener, may be null
     */
    static void addDataListener(String refPath, DataChangeListener listener) {
        if (listenersManager.hasListeners(refPath)) {
            if (listener == null) {
                listenersManager.removeListenersForPath(refPath);
            }
        } else if (listener != null) {
            listenersManager.addNewListener(refPath, listener);
        }
    }

    /**
     * Gets true if listener for given path is already attached.
     *
     * @param referencePath Reference path, not null
     * @return True if listener is already attached.
     */
    public static boolean hasListener(String referencePath) {
        return listenersManager.hasListeners(referencePath);
    }

    /**
     * Calls listener added by {@code addDataListener}.
     *
     * @param refPath  Database ref path
     * @param newValue New value as json string
     */
    static void callListener(String refPath, String newValue) {
        if (!listenersManager.hasListeners(refPath))
            return;
        Array<DataChangeListener> listeners = listenersManager.getListeners(refPath);
        for (DataChangeListener listener : listeners)
            listener.onChange(newValue);
    }

    /**
     * Remove listener.
     *
     * @param refPath Database reference path
     */
    static void removeDataListener(String refPath) {
        if (listenersManager.hasListeners(refPath))
            listenersManager.removeListenersForPath(refPath);
    }

}
