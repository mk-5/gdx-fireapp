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

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock3;
import org.robovm.pods.firebase.database.FIRDataSnapshot;

import pl.mk5.gdx.fireapp.promises.FuturePromise;

import static pl.mk5.gdx.fireapp.ios.database.QueryRunTransaction.TRANSACTION_NOT_ABLE_TO_COMMIT;

class TransactionCompleteBlock implements VoidBlock3<NSError, Boolean, FIRDataSnapshot> {

    private FuturePromise<Void> promise;

    TransactionCompleteBlock(FuturePromise<Void> promise) {
        this.promise = promise;
    }

    @Override
    public void invoke(NSError arg0, Boolean arg1, FIRDataSnapshot arg2) {
        if (arg0 != null) {
            promise.doFail(new Exception(arg0.getLocalizedDescription()));
        } else {
            if (arg1) {
                promise.doComplete(null);
            } else {
                promise.doFail(new Exception(TRANSACTION_NOT_ABLE_TO_COMMIT));
            }
        }
    }
}