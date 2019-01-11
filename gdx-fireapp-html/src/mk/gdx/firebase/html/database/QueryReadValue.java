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
import mk.gdx.firebase.database.validators.ReadValueValidator;
import mk.gdx.firebase.promises.ConverterPromise;

/**
 * Provides setValue execution.
 */
class QueryReadValue<R> extends GwtDatabaseQuery<R> {

    QueryReadValue(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void runJS() {
        ((ConverterPromise) promise).with(new JsonPromiseModifier((Class) arguments.get(0)));
        once(databaseReference, (ConverterPromise) promise);
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
    static native void once(DatabaseReference databaseReference, ConverterPromise promise) /*-{
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
            promise.@mk.gdx.firebase.promises.ConverterPromise::doComplete(Ljava/lang/String;)(val);
        })['catch'](function(error){
         promise.@mk.gdx.firebase.promises.ConverterPromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
