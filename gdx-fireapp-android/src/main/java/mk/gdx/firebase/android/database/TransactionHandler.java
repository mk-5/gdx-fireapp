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

package mk.gdx.firebase.android.database;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;

/**
 * Provides transaction invocation
 */
class TransactionHandler<R> implements Transaction.Handler {

    private static final String TRANSACTION_NULL_VALUE_RETRIEVED = "Null value retrieved from database for transaction - aborting";
    private static final String TRANSACTION_NOT_ABLE_TO_COMMIT = "The database value at given path was not be able to commit";
    private static final String TRANSACTION_ERROR = "Null value retrieved from database for transaction - aborting";


    private TransactionCallback<R> transactionCallback;
    private CompleteCallback completeCallback;


    /**
     * @param transactionCallback Transaction callback, not null
     * @param completeCallback    Complete callback, may be null
     */
    TransactionHandler(TransactionCallback<R> transactionCallback, CompleteCallback completeCallback) {
        this.transactionCallback = transactionCallback;
        this.completeCallback = completeCallback;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Transaction.Result doTransaction(MutableData mutableData) {
        if (mutableData.getValue() == null) {
            GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
            return Transaction.abort();
        }
        R transactionData = (R) mutableData.getValue();
        mutableData.setValue(transactionCallback.run(transactionData));
        return Transaction.success(mutableData);
    }

    @Override
    public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
        if (databaseError != null) {
            if (completeCallback != null) {
                completeCallback.onError(databaseError.toException());
            } else {
                GdxFIRLogger.error(TRANSACTION_ERROR, databaseError.toException());
            }
        } else {
            if (b) {
                if (completeCallback != null) {
                    completeCallback.onSuccess();
                }
            } else {
                if (completeCallback != null) {
                    completeCallback.onError(new Exception(TRANSACTION_NOT_ABLE_TO_COMMIT));
                } else {
                    GdxFIRLogger.error(TRANSACTION_NOT_ABLE_TO_COMMIT);
                }
            }
        }
    }
}
