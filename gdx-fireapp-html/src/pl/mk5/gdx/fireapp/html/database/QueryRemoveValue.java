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
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Provides removeValue javascript execution.
 */
class QueryRemoveValue extends GwtDatabaseQuery {

    QueryRemoveValue(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
    }

    @Override
    protected void runJS() {
        if (promise == null) {
            remove(databasePath);
        } else {
            removeWithPromise(databasePath, (FuturePromise) promise);
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return null;
    }

    /**
     * Calls firebase.database().remove method.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Reference path, not null
     */
    public static native void remove(String reference) /*-{
        $wnd.firebase.database().ref(reference).remove();
    }-*/;

    /**
     * Calls firebase.database().remove
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Reference path, not null
     */
    public static native void removeWithPromise(String reference, FuturePromise promise) /*-{
        $wnd.firebase.database().ref(reference).remove().then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })['catch'](function(error){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
