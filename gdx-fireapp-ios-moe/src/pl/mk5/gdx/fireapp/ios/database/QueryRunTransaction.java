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

package pl.mk5.gdx.fireapp.ios.database;

import apple.foundation.NSError;
import apple.foundation.NSNull;
import bindings.google.firebasedatabase.FIRDataSnapshot;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import bindings.google.firebasedatabase.FIRMutableData;
import bindings.google.firebasedatabase.FIRTransactionResult;
import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.RunTransactionValidator;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Provides call to {@link FIRDatabaseReference#runTransactionBlockAndCompletionBlock(FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0, FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1)}.
 */
class QueryRunTransaction<R> extends IosDatabaseQuery<R> {
    private static final String TRANSACTION_NULL_VALUE_RETRIEVED = "Null value retrieved from database for transaction - aborting";
    private static final String TRANSACTION_NOT_ABLE_TO_COMMIT = "The database value at given path was not be able to commit";

    QueryRunTransaction(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
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
    protected R run() {
        ((FIRDatabaseReference) query).runTransactionBlockAndCompletionBlock(
                new RunTransactionBlock((Class) arguments.get(0), (Function<R, R>) arguments.get(1)),
                new TransactionCompleteBlock((FuturePromise<Void>) promise));
        return null;
    }

    private static class RunTransactionBlock<R> implements FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0 {

        private Class type;
        private Function<R, R> transactionFunction;

        private RunTransactionBlock(Class type, Function<R, R> transactionFunction) {
            this.type = type;
            this.transactionFunction = transactionFunction;
        }

        @Override
        @SuppressWarnings("unchecked")
        public FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(FIRMutableData arg0) {
            if (arg0.value() == null || NSNull.class.isAssignableFrom(arg0.value().getClass())) {
                GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
                return FIRTransactionResult.abort();
            }
            Object transactionObject = DataProcessor.iosDataToJava(arg0.value(), type);
            arg0.setValue(DataProcessor.javaDataToIos(transactionFunction.apply((R) transactionObject)));
            return FIRTransactionResult.successWithValue(arg0);
        }
    }

    private static class TransactionCompleteBlock implements FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1 {

        private FuturePromise<Void> promise;

        private TransactionCompleteBlock(FuturePromise<Void> promise) {
            this.promise = promise;
        }

        @Override
        public void call_runTransactionBlockAndCompletionBlock_1(NSError arg0, boolean arg1, FIRDataSnapshot arg2) {
            if (arg0 != null) {
                promise.doFail(new Exception(arg0.localizedDescription()));
            } else {
                if (arg1) {
                    promise.doComplete(null);
                } else {
                    promise.doFail(new Exception(TRANSACTION_NOT_ABLE_TO_COMMIT));
                }
            }
        }
    }
}
