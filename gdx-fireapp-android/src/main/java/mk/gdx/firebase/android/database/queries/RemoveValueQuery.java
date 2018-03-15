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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import mk.gdx.firebase.android.database.AndroidDatabaseQuery;
import mk.gdx.firebase.android.database.Database;
import mk.gdx.firebase.callbacks.CompleteCallback;

/**
 * Provides call to {@link DatabaseReference#removeValue()} and {@link DatabaseReference#removeValue(DatabaseReference.CompletionListener)}.
 */
public class RemoveValueQuery extends AndroidDatabaseQuery<Void>
{
    public RemoveValueQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
    }

    @Override
    protected void prepare()
    {
        super.prepare();
        if (!(query instanceof DatabaseReference))
            throw new IllegalStateException(SHOULD_BE_RUN_WITH_DATABASE_REFERENCE);
        if (arguments.size > 0  && arguments.get(0) != null && !(arguments.get(0) instanceof CompleteCallback))
            throw new IllegalArgumentException();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected Void run()
    {
        if (arguments.size == 0) {
            ((DatabaseReference) query).removeValue();
        } else if (arguments.size == 1) {
            ((DatabaseReference) query).removeValue(new DatabaseReference.CompletionListener()
            {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
                {
                    if (databaseError != null) {
                        ((CompleteCallback) arguments.get(0)).onError(databaseError.toException());
                    } else {
                        ((CompleteCallback) arguments.get(0)).onSuccess();
                    }
                }
            });
        } else {
            throw new IllegalStateException();
        }
        return null;
    }

}
