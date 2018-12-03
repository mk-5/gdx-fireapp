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

import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.ReadValueValidator;

/**
 * Provides setValue execution.
 */
class QueryReadValue extends GwtDatabaseQuery {
    private JsonDataCallback jsDataCallback;

    QueryReadValue(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void runJS() {
        jsDataCallback = new JsonDataCallback((Class) arguments.get(0), (DataCallback) arguments.get(1));
        once(databaseReference, jsDataCallback);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new ReadValueValidator();
    }

    /**
     * Gets some data from firebase and returns value parsed by JSON.stringify
     * <p>
     *
     * @param databaseReference DatabaseReference path, not null
     */
    public static native void once(DatabaseReference databaseReference, DataCallback jsDataCallback) /*-{
        var orderByCalled = databaseReference.orderByCalled_;
        databaseReference.once("value").then(function(snapshot){
            var val;
            if( !orderByCalled ){
                val = JSON.stringify(snapshot.val());
            }else{
                var tmp = [];
                snapshot.forEach(function(child){
                    tmp.push(child.val());
                });
                val = JSON.stringify(tmp);
            }
            jsDataCallback.@mk.gdx.firebase.html.database.JsonDataCallback::onData(Ljava/lang/String;)(val);
        })['catch'](function(error){
            jsDataCallback.@mk.gdx.firebase.callbacks.DataCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
