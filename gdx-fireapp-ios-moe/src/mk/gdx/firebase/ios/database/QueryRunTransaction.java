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

package mk.gdx.firebase.ios.database;

import apple.foundation.NSError;
import apple.foundation.NSNull;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import bindings.google.firebasedatabase.FIRMutableData;
import bindings.google.firebasedatabase.FIRTransactionResult;
import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.RunTransactionValidator;

/**
 * Provides call to {@link FIRDatabaseReference#runTransactionBlockAndCompletionBlock(FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0, FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1)}.
 */
class QueryRunTransaction extends IosDatabaseQuery<Void> {
    private static final String TRANSACTION_NULL_VALUE_RETRIEVED = "Null value retrieved from database for transaction - aborting";
    private static final String TRANSACTION_NOT_ABLE_TO_COMMIT = "The database value at given path was not be able to commit";
    private static final String TRANSACTION_ERROR = "Null value retrieved from database for transaction - aborting";

    QueryRunTransaction(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void prepare() {
        super.prepare();
        if (!(query instanceof FIRDatabaseReference))
            throw new IllegalStateException(SHOULD_BE_RUN_WITH_DATABASE_REFERENCE);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator() {
        return new RunTransactionValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run() {
        ((FIRDatabaseReference) query).runTransactionBlockAndCompletionBlock(
                new RunTransactionBlock((Class) arguments.get(0), (TransactionCallback) arguments.get(1)),
                new TransactionCompleteBlock(arguments.size == 3 ? (CompleteCallback) arguments.get(2) : null));
        return null;
    }

    private class RunTransactionBlock implements FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0 {

        private Class type;
        private TransactionCallback transactionCallback;

        private RunTransactionBlock(Class type, TransactionCallback transactionCallback) {
            this.type = type;
            this.transactionCallback = transactionCallback;
        }

        @Override
        public FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(FIRMutableData arg0) {
            if (NSNull.class.isAssignableFrom(arg0.value().getClass())) {
                GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
                return FIRTransactionResult.abort();
            }
            Object transactionObject = DataProcessor.iosDataToJava(arg0.value(), type);
            arg0.setValue(DataProcessor.javaDataToIos(transactionCallback.run(transactionObject)));
            return FIRTransactionResult.successWithValue(arg0);
        }
    }

    private class TransactionCompleteBlock implements FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1 {

        private CompleteCallback completeCallback;

        private TransactionCompleteBlock(CompleteCallback completeCallback) {
            this.completeCallback = completeCallback;
        }

        @Override
        public void call_runTransactionBlockAndCompletionBlock_1(NSError arg0, boolean arg1, FIRDataSnapshot arg2) {
            if (arg0 != null) {
                if (completeCallback != null) {
                    completeCallback.onError(new Exception(arg0.localizedDescription()));
                } else {
                    GdxFIRLogger.error(TRANSACTION_ERROR, new Exception(arg0.localizedDescription()));
                }
            } else {
                if (arg1) {
                    if (completeCallback != null)
                        completeCallback.onSuccess();
                } else {
                    if (completeCallback != null) {
                        completeCallback.onError(new Exception(TRANSACTION_NOT_ABLE_TO_COMMIT));
                    } else {
                        GdxFIRLogger.log(TRANSACTION_NOT_ABLE_TO_COMMIT);
                    }
                }
            }
        }
    }
}
