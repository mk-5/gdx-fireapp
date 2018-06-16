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

package mk.gdx.firebase.html.database.queries;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.RemoveValueValidator;
import mk.gdx.firebase.html.database.Database;
import mk.gdx.firebase.html.database.GwtDatabaseQuery;

/**
 * Provides removeValue javascript execution.
 */
public class RemoveValueQuery extends GwtDatabaseQuery {
    public RemoveValueQuery(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void runJS() {
        if (arguments.size == 0) {
            remove(databaseReference);
        } else if (arguments.size == 1) {
            removeWithCallback(databaseReference, (CompleteCallback) arguments.get(1));
        } else {
            throw new IllegalStateException();
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new RemoveValueValidator();
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
     * Calls firebase.database().remove method with callback.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Reference path, not null
     * @param callback  Callback
     */
    public static native void removeWithCallback(String reference, CompleteCallback callback) /*-{
        $wnd.firebase.database().ref(reference).remove().then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })['catch'](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
