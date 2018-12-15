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
import mk.gdx.firebase.database.validators.SetValueValidator;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides setValue execution.
 */
class QuerySetValue extends GwtDatabaseQuery {
    QuerySetValue(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void runJS() {
        if (promise == null) {
            set(databaseReferencePath, StringGenerator.dataToString(arguments.get(0)));
        } else {
            setWithPromise(databaseReferencePath, StringGenerator.dataToString(arguments.get(0)), (FuturePromise) promise);
        }
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new SetValueValidator();
    }

    /**
     * Set value to database.
     * <p>
     * JSON.parse is using here. It's getting object or primitive (for primitives types it is primitive wrapped by string ex. "1", "true") and returns object representation.
     * Is one exception here - when stringValue is actually a string like "abc", JSON.parse will be throw error.
     *
     * @param reference   Reference path, not null
     * @param stringValue String value representation
     */
    public static native void set(String reference, String stringValue) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).set(val);
    }-*/;

    /**
     * Set value to database.
     * <p>
     * For more explanation look at {@link #set(String, String)}
     *
     * @param reference   Reference path, not null
     * @param stringValue String value representation
     */
    public static native void setWithPromise(String reference, String stringValue, FuturePromise promise) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).set(val).then(function(){
            promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })['catch'](function(error){
            promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
