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

import org.robovm.apple.foundation.NSNull;
import org.robovm.apple.foundation.NSObject;
import org.robovm.objc.block.Block1;
import org.robovm.pods.firebase.database.FIRMutableData;
import org.robovm.pods.firebase.database.FIRTransactionResult;

import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.reflection.DefaultTypeRecognizer;

import static pl.mk5.gdx.fireapp.ios.database.QueryRunTransaction.TRANSACTION_ERROR;

class RunTransactionBlock<R extends NSObject> implements Block1<FIRMutableData, FIRTransactionResult> {

    private Class type;
    private Function transactionFunction;

    RunTransactionBlock(Class type, Function transactionFunction) {
        this.type = type;
        this.transactionFunction = transactionFunction;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FIRTransactionResult invoke(FIRMutableData arg0) {
        try {
            if (arg0.getValue() == null || NSNull.class.isAssignableFrom(arg0.getValue().getClass())) {
                arg0.setValue(DataProcessor.javaDataToIos(transactionFunction.apply(DefaultTypeRecognizer.getDefaultValue(type))));
                return FIRTransactionResult.success(arg0);
            }
            Object transactionObject = DataProcessor.iosDataToJava(arg0.getValue(), type);
            if (transactionObject == null) {
                transactionObject = DefaultTypeRecognizer.getDefaultValue(type);
            }
            arg0.setValue(DataProcessor.javaDataToIos(transactionFunction.apply(transactionObject)));
            return FIRTransactionResult.success(arg0);
        } catch (Exception e) {
            GdxFIRLogger.error(TRANSACTION_ERROR, e);
            return FIRTransactionResult.abort();
        }
    }
}
