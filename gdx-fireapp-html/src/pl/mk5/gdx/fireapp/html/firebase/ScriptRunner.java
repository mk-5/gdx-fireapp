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

/**
 * Provides awaiting for firebase.js script loaded.
 */
public class ScriptRunner {

    private ScriptRunner() {

    }

    /**
     * Run action or add it into waiting queue.
     *
     * @param action Action with firebase.js script
     */
    public static synchronized void firebaseScript(Runnable action) {
        if (FirebaseScriptInformant.isFirebaseScriptLoaded())
            action.run();
        else FirebaseScriptInformant.addWaitingAction(action);
    }

    public static abstract class ScriptDBAction implements Runnable {
        protected String scriptRefPath;

        public ScriptDBAction(String scriptRefPath) {
            this.scriptRefPath = scriptRefPath;
        }
    }

    public static abstract class ScriptStorageAction implements Runnable {
        protected String scriptBucketUrl;

        public ScriptStorageAction(String scriptBucketUrl) {
            this.scriptBucketUrl = scriptBucketUrl;
        }
    }
}
