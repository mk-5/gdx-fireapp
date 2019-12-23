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

import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.database.DataListenersManager;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class GwtDataPromisesManager {
    private static final DataListenersManager<FuturePromise> listenersManager = new DataListenersManager<>();

    private GwtDataPromisesManager() {
        //
    }

    static void addDataPromise(String refPath, FuturePromise promise) {
        listenersManager.addNewListener(refPath, promise);
    }

    static boolean hasPromise(String referencePath) {
        return listenersManager.hasListeners(referencePath);
    }

    @SuppressWarnings("unchecked")
    static void callPromise(String refPath, String newValue) {
        if (!listenersManager.hasListeners(refPath))
            return;
        Array<FuturePromise> promises = listenersManager.getListeners(refPath);
        for (FuturePromise promise : promises)
            promise.doComplete(newValue);
    }

    static void removeDataPromise(String refPath) {
        listenersManager.removeListenersForPath(refPath);
    }

}
