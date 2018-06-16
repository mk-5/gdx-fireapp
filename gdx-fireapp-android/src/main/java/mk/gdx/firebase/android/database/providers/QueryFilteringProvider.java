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

import com.google.firebase.database.Query;

import mk.gdx.firebase.android.database.resolvers.QueryFilterResolver;
import mk.gdx.firebase.android.database.resolvers.QueryOrderByResolver;
import mk.gdx.firebase.database.FilteringProvider;
import mk.gdx.firebase.database.pojos.Filter;

/**
 * Provides filter and order-by application into given {@code Query} instance.
 */
public class QueryFilteringProvider extends FilteringProvider<Query, QueryFilterResolver, QueryOrderByResolver> {

    @Override
    public Query applyFiltering() {
        Filter filter = null;
        if (orderByClause != null)
            query = orderByResolver.resolve(orderByClause, query);
        while (filters.size > 0) {
            filter = filters.pop();
            query = filterResolver.resolve(filter.getFilterType(), query, filter.getFilterArguments());
        }
        return query;
    }

    @Override
    public QueryFilterResolver createFilterResolver() {
        return new QueryFilterResolver();
    }

    @Override
    public QueryOrderByResolver createOrderByResolver() {
        return new QueryOrderByResolver();
    }
}
