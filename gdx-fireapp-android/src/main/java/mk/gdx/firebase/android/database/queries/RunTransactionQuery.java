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

package mk.gdx.firebase.android.database.queries;

import com.google.firebase.database.DatabaseReference;

import mk.gdx.firebase.android.database.AndroidDatabaseQuery;
import mk.gdx.firebase.android.database.Database;
import mk.gdx.firebase.android.database.handlers.TransactionHandler;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;

/**
 * Provides setValue execution with firebase database reference.
 */
public class RunTransactionQuery extends AndroidDatabaseQuery<Void>
{
    public RunTransactionQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
    }

    @Override
    protected void prepare()
    {
        super.prepare();
        if (arguments.size < 2)
            throw new IllegalStateException();
        if (!(query instanceof DatabaseReference))
            throw new IllegalStateException(SHOULD_BE_RUN_WITH_DATABASE_REFERENCE);
        if (!(arguments.get(0) instanceof Class))
            throw new IllegalArgumentException();
        if (!(arguments.get(1) instanceof TransactionCallback))
            throw new IllegalArgumentException();
        if (arguments.size > 2 && arguments.get(2) != null && !(arguments.get(2) instanceof CompleteCallback))
            throw new IllegalArgumentException();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run()
    {
        ((DatabaseReference) query).runTransaction(new TransactionHandler((TransactionCallback) arguments.get(1), arguments.get(2) != null ? (CompleteCallback) arguments.get(2) : null));
        return null;
    }
}
