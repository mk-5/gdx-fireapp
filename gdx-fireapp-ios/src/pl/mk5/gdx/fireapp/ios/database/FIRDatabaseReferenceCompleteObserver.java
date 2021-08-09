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

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.database.FIRDatabaseReference;

import pl.mk5.gdx.fireapp.promises.FuturePromise;

class FIRDatabaseReferenceCompleteObserver implements VoidBlock2<NSError, FIRDatabaseReference> {

    private final FuturePromise promise;

    FIRDatabaseReferenceCompleteObserver(FuturePromise promise) {
        this.promise = promise;
    }

    @Override
    public void invoke(NSError arg0, FIRDatabaseReference arg1) {
        if (promise == null) return;
        if (arg0 != null) {
            promise.doFail(new Exception(arg0.getLocalizedDescription()));
        } else {
            promise.doComplete(null);
        }
    }

}

