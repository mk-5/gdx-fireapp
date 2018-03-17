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

package mk.gdx.firebase.ios.database.queries;

import com.google.firebasedatabase.FIRDataSnapshot;
import com.google.firebasedatabase.FIRDatabaseQuery;
import com.google.firebasedatabase.FIRDatabaseReference;
import com.google.firebasedatabase.FIRMutableData;
import com.google.firebasedatabase.FIRTransactionResult;

import apple.foundation.NSError;
import apple.foundation.NSNull;
import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.RunTransactionValidator;
import mk.gdx.firebase.ios.database.DataProcessor;
import mk.gdx.firebase.ios.database.Database;
import mk.gdx.firebase.ios.database.IosDatabaseQuery;

/**
 * Provides call to {@link FIRDatabaseQuery#observeEventTypeWithBlockWithCancelBlock(long, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_2)}.
 */
public class RunTransactionQuery extends IosDatabaseQuery<Void>
{
    private static final String TRANSACTION_NULL_VALUE_RETRIEVED = "Null value retrieved from database for transaction - aborting";
    private static final String TRANSACTION_NOT_ABLE_TO_UPDATE = "\"The database value at given path was not be able to update";
    private static final String TRANSACTION_ERROR = "Null value retrieved from database for transaction - aborting";

    public RunTransactionQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
    }

    @Override
    protected void prepare()
    {
        super.prepare();
        if (!(query instanceof FIRDatabaseReference))
            throw new IllegalStateException(SHOULD_BE_RUN_WITH_DATABASE_REFERENCE);
    }

    @Override
    protected ArgumentsValidator createArgumentsValidator()
    {
        return new RunTransactionValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run()
    {
        ((FIRDatabaseReference) query).runTransactionBlockAndCompletionBlock(new FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0()
        {
            @Override
            public FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(FIRMutableData arg0)
            {
                if (NSNull.class.isAssignableFrom(arg0.value().getClass())) {
                    GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
                    return FIRTransactionResult.abort();
                }
                Object transactionObject = DataProcessor.iosDataToJava(arg0.value(), (Class) arguments.get(0));
                arg0.setValue(DataProcessor.javaDataToIos(((TransactionCallback) arguments.get(1)).run(transactionObject)));
                return FIRTransactionResult.successWithValue(arg0);
            }
        }, new FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1()
        {
            @Override
            public void call_runTransactionBlockAndCompletionBlock_1(NSError arg0, boolean arg1, FIRDataSnapshot arg2)
            {
                if (arg0 != null) {
                    if (arguments.get(2) != null) {
                        ((CompleteCallback) arguments.get(2)).onError(new Exception(arg0.localizedDescription()));
                    } else {
                        GdxFIRLogger.error(TRANSACTION_ERROR, new Exception(arg0.localizedDescription()));
                    }
                } else {
                    if (arg1) {
                        if (arguments.get(2) != null)
                            ((CompleteCallback) arguments.get(2)).onSuccess();
                    } else {
                        if (arguments.get(2) != null) {
                            ((CompleteCallback) arguments.get(2)).onError(new Exception(TRANSACTION_NOT_ABLE_TO_UPDATE));
                        } else {
                            GdxFIRLogger.log(TRANSACTION_NOT_ABLE_TO_UPDATE);
                        }
                    }
                }
            }
        });
        return null;
    }
}
