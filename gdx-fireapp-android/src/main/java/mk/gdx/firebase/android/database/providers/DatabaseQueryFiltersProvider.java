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

package mk.gdx.firebase.android.database.providers;

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import mk.gdx.firebase.android.database.proxies.FiltersAffectedQuery;
import mk.gdx.firebase.android.database.resolvers.AndroidOrderByResolver;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;

/**
 * Provides decision between call {@code DatabaseReference} or {@code Query} based at current query context.
 */
public class DatabaseQueryFiltersProvider implements FiltersAffectedQuery
{
    private QueryFilterProvider refFilterProvider;
    private Query query;
    private Array<Filter> filters;
    private OrderByClause orderByClause;

    /**
     * Initialize provider and keep all filters in own array.
     *
     * @param filters       Current filters, may empty
     * @param orderByClause Order-by clause, may be null
     * @param query         DatabaseReference instance, not null
     */
    public DatabaseQueryFiltersProvider(Array<Filter> filters, OrderByClause orderByClause, Query query)
    {
        this.query = query;
        this.orderByClause = orderByClause;
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        this.filters = new Array<>(filters);
        refFilterProvider = new QueryFilterProvider();
    }

    @Override
    public void addListenerForSingleValueEvent(ValueEventListener valueEventListener)
    {
        query = processQuery();
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    @Override
    public ValueEventListener addValueEventListener(ValueEventListener valueEventListener)
    {
        query = processQuery();
        return query.addValueEventListener(valueEventListener);
    }

    private Query processQuery()
    {
        Filter filter = null;
        if (orderByClause != null)
            query = new AndroidOrderByResolver().resolve(orderByClause, query);
        while (filters.size > 0) {
            filter = filters.pop();
            query = refFilterProvider.apply(filter.getFilterType(), query, filter.getFilterArguments());
        }
        return query;
    }
}
