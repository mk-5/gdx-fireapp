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

import java.util.Map;

import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.UpdateChildrenValidator;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides update javascript execution.
 */
class QueryUpdateChildren extends GwtDatabaseQuery {
    QueryUpdateChildren(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void runJS() {
        if (promise == null) {
            update(databaseReferencePath, MapTransformer.mapToJSON((Map<String, Object>) arguments.get(0)));
        } else {
            updateWithPromise(databaseReferencePath, MapTransformer.mapToJSON((Map<String, Object>) arguments.get(0)), (FuturePromise) promise);
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new UpdateChildrenValidator();
    }

    /**
     * Writes multiple values to database at once.
     * <p>
     * JSON.parse is using here. It get object or primitive (for primitives types it is primitive wrapped by string ex. "1", "true") and returns object representation.
     * Is one exception here - when stringValue is actually a string like "abc", JSON.parse will be throw error.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#update">https://firebase.google.com/docs/reference/js/firebase.database.Reference#update</a>
     *
     * @param reference   Reference path, not null
     * @param stringValue String value representation
     */
    static native void update(String reference, String stringValue) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).update(val);
    }-*/;

    /**
     * Update multiple values in database with callback.
     * <p>
     * For more explanation look at {@link #update(String, String)}
     *
     * @param reference   Reference path, not null
     * @param stringValue String value representation
     */
    static native void updateWithPromise(String reference, String stringValue, FuturePromise promise) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).update(val).then(function(){
            promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })['catch'](function(error){
             promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
