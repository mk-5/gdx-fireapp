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

import mk.gdx.firebase.android.database.AndroidOrderByResolver;
import mk.gdx.firebase.android.database.QueryFilterProvider;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;

/**
 * Provides decision between call {@code DatabaseReference} or {@code Query} based at current query context.
 */
public class DatabaseReferenceFiltersProvider implements DbRefQueryProxyMethods
{
    private QueryFilterProvider refFilterProvider;
    private DatabaseReference databaseReference;
    private Array<Filter> filters;
    private OrderByClause orderByClause;

    /**
     * Initialize provider and keep all filters in own array.
     *
     * @param filters           Current filters, may empty
     * @param databaseReference DatabaseReference instance, not null
     */
    public DatabaseReferenceFiltersProvider(Array<Filter> filters, DatabaseReference databaseReference)
    {
        this.databaseReference = databaseReference;
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        this.filters = new Array<>(filters);
        refFilterProvider = new QueryFilterProvider();
    }

    @Override
    public void addListenerForSingleValueEvent(ValueEventListener valueEventListener)
    {
        if (filters.size > 0 || orderByClause != null ) {
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
        if (filters.size > 0 || orderByClause != null ) {
            Query query = processQuery();
            assert query != null;
            return query.addValueEventListener(valueEventListener);
        } else {
            return databaseReference.addValueEventListener(valueEventListener);
        }
    }

    /**
     * Adds order by clause to be processed before filters will be applied.
     *
     * @param orderByClause Order by clause, may be null
     * @return this
     */
    public DatabaseReferenceFiltersProvider with(OrderByClause orderByClause)
    {
        this.orderByClause = orderByClause;
        return this;
    }

    private Query processQuery()
    {
        Filter filter;
        Query query = null;
        if (orderByClause != null)
            query = new AndroidOrderByResolver().resolve(orderByClause, databaseReference);
        while (filters.size > 0) {
            filter = filters.pop();
            query = refFilterProvider.apply(filter.getFilterType(), query == null ? databaseReference : query, filter.getFilterArguments());
        }
        return query;
    }
}
