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

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.RunTransactionValidator;

/**
 * Provides transaction javascript execution.
 */
class QueryRunTransaction extends GwtDatabaseQuery {
    QueryRunTransaction(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void runJS() {
        transaction(databaseReferencePath, new JsonDataModifier((Class) arguments.get(0), (TransactionCallback) arguments.get(1)), (CompleteCallback) arguments.get(2));
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new RunTransactionValidator();
    }

    /**
     * Modify database data in single transaction.
     *
     * @param reference        Reference path, not null
     * @param dataModifier     Json data modifier, not null
     * @param completeCallback Complete callback, not null
     */
    public static native void transaction(String reference, JsonDataModifier dataModifier, CompleteCallback completeCallback) /*-{
        $wnd.firebase.database().ref(reference).transaction(function(currentData){
            var newData = dataModifier.@mk.gdx.firebase.html.database.JsonDataModifier::modify(Ljava/lang/String;)(JSON.stringify(currentData));
            var val;
            try{
                val = JSON.parse(newData);
            }catch(err){
                val = newData;
            }
            return val;
        }, function(error, committed, snapshot){
            if( error || !committed ){
                var message = "";
                if( error ) message = error.message;
                else if( !committed ) message = "Transaction was aborted.";
                callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(message));
            }else{
                callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
            }
        });
    }-*/;
}
