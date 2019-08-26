/*
 * Copyright 2019 mk
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

import apple.foundation.NSNull;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import bindings.google.firebasedatabase.FIRMutableData;
import bindings.google.firebasedatabase.FIRTransactionResult;
import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.reflection.DefaultTypeRecognizer;

import static pl.mk5.gdx.fireapp.ios.database.QueryRunTransaction.TRANSACTION_ERROR;

class RunTransactionBlock<R> implements FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0 {

    private static final String CANT_FIND_DEFAULT_VALUE_FOR_GIVEN_TYPE = "Cant find create default transaction value for given type";
    private Class type;
    private Function<R, R> transactionFunction;

    RunTransactionBlock(Class type, Function<R, R> transactionFunction) {
        this.type = type;
        this.transactionFunction = transactionFunction;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(FIRMutableData arg0) {
        try {
            if (arg0.value() == null || NSNull.class.isAssignableFrom(arg0.value().getClass())) {
                arg0.setValue(transactionFunction.apply((R) DefaultTypeRecognizer.getDefaultValue(type)));
                return FIRTransactionResult.successWithValue(arg0);
            }
            Object transactionObject = DataProcessor.iosDataToJava(arg0.value(), type);
            if (transactionObject == null) {
                transactionObject = DefaultTypeRecognizer.getDefaultValue(type);
            }
            arg0.setValue(DataProcessor.javaDataToIos(transactionFunction.apply((R) transactionObject)));
            return FIRTransactionResult.successWithValue(arg0);
        } catch (Exception e) {
            GdxFIRLogger.error(TRANSACTION_ERROR);
            return FIRTransactionResult.abort();
        }
    }
}
