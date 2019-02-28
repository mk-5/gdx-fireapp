/*
 * Copyright 2017 mk
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

package pl.mk5.gdx.fireapp.html.firebase;

import com.badlogic.gdx.utils.Array;

/**
 * Keeps information about Firebase .js script was loaded or not.
 */
public class FirebaseScriptInformant {
    private static boolean isLoaded;

    private FirebaseScriptInformant() {
        //
    }

    /**
     * Actions waiting for firebase.js
     */
    private static Array<Runnable> waitingActions = new Array<Runnable>();

    public static boolean isFirebaseScriptLoaded() {
        return isLoaded;
    }

    static void addWaitingAction(Runnable action) {
        if (!waitingActions.contains(action, true))
            waitingActions.add(action);
    }

    /**
     * Sets firebase.js loading status and run all waiting actions.
     *
     * @param isLoaded Firebase.js loading status.
     */
    static void setIsLoaded(boolean isLoaded) {
        if (FirebaseScriptInformant.isLoaded == isLoaded) return;
        FirebaseScriptInformant.isLoaded = isLoaded;
        if (isLoaded) {
            for (Runnable action : waitingActions)
                action.run();
            waitingActions.clear();
        }
    }
}
