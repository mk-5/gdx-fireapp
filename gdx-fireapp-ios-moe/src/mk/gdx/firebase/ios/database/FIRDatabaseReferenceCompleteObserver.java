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
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Wraps {@link mk.gdx.firebase.callbacks.CompleteCallback} with ios completion observer.
 */
class FIRDatabaseReferenceCompleteObserver implements FIRDatabaseReference.Block_setValueWithCompletionBlock,
        FIRDatabaseReference.Block_removeValueWithCompletionBlock, FIRDatabaseReference.Block_updateChildValuesWithCompletionBlock {

    private FuturePromise promise;

    FIRDatabaseReferenceCompleteObserver(FuturePromise promise) {
        this.promise = promise;
    }

    protected void process(NSError arg0, FIRDatabaseReference arg1) {
        if (promise == null) return;
        if (arg0 != null) {
            promise.doFail(new Exception(arg0.localizedDescription()));
        } else {
            promise.doComplete(null);
        }
    }

    @Override
    public void call_setValueWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1) {
        process(arg0, arg1);
    }

    @Override
    public void call_removeValueWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1) {
        process(arg0, arg1);
    }

    @Override
    public void call_updateChildValuesWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1) {
        process(arg0, arg1);
    }
}

