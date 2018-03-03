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

package mk.gdx.firebase.android.database.proxies;

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.android.database.DatabaseReferenceFilterProvider;
import mk.gdx.firebase.android.database.QueryFilterProvider;
import mk.gdx.firebase.database.pojos.Filter;

/**
 * Provides decision between call {@code DatabaseReference} or {@code Query} based at current query context.
 */
public class DatabaseReferenceQueryProxy implements DbRefQueryProxyMethods
{
    private DatabaseReferenceFilterProvider refFilterProvider;
    private QueryFilterProvider queryFilterProvider;
    private DatabaseReference databaseReference;
    private Array<Filter> filters;

    /**
     * @param filters           Current filters, may empty
     * @param databaseReference DatabaseReference instance, not null
     */
    public DatabaseReferenceQueryProxy(Array<Filter> filters, DatabaseReference databaseReference)
    {
        this.databaseReference = databaseReference;
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        this.filters = new Array<>(filters);
        refFilterProvider = new DatabaseReferenceFilterProvider();
        queryFilterProvider = new QueryFilterProvider();
    }

    @Override
    public void addListenerForSingleValueEvent(ValueEventListener valueEventListener)
    {
        if (filters.size > 0) {
            Query query = processQuery();
            assert query != null;
            query.addListenerForSingleValueEvent(valueEventListener);
        } else {
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
        }
    }

    @Override
    public ValueEventListener addValueEventListener(ValueEventListener valueEventListener)
    {
        if (filters.size > 0) {
            Query query = processQuery();
            assert query != null;
            return query.addValueEventListener(valueEventListener);
        } else {
            return databaseReference.addValueEventListener(valueEventListener);
        }
    }

    private Query processQuery()
    {
        Filter filter;
        Query query = null;
        while (filters.size > 0) {
            filter = filters.pop();
            query = refFilterProvider.apply(filter.getFilterType(), databaseReference, filter.getFilterArguments());
        }
        return query;
    }
}
