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

import apple.foundation.NSDictionary;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.UpdateChildrenValidator;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides call to {@link FIRDatabaseReference#updateChildValues(NSDictionary)} ()}.
 */
class QueryUpdateChildren extends IosDatabaseQuery<Void> {
    QueryUpdateChildren(Database databaseDistribution) {
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
        return new UpdateChildrenValidator();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run() {
        ((FIRDatabaseReference) query).updateChildValuesWithCompletionBlock(NSDictionaryHelper.toNSDictionary(arguments.get(0)), new FIRDatabaseReferenceCompleteObserver((FuturePromise) promise));
        return null;
    }
}
