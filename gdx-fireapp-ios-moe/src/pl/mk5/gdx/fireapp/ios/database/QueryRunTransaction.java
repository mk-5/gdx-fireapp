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

import bindings.google.firebasedatabase.FIRDatabaseReference;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.RunTransactionValidator;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Provides call to {@link FIRDatabaseReference#runTransactionBlockAndCompletionBlock(FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0, FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1)}.
 */
class QueryRunTransaction<R> extends DatabaseQuery<R> {

    static final String TRANSACTION_ERROR = "Transaction error - aborting";
    static final String TRANSACTION_NOT_ABLE_TO_COMMIT = "Transaction has not been committed";

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

}
