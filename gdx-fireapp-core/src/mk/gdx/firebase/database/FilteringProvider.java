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

package mk.gdx.firebase.database;

import com.badlogic.gdx.utils.Array;

import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;

/**
 * Provides filtering flow.
 *
 * @param <T> Firebase database Query type
 * @param <E> Filter resolver instance
 */
public abstract class FilteringProvider<T, E extends FilterResolver, K extends OrderByResolver>
{

    protected E filterResolver;
    protected K orderByResolver;
    protected T query;
    protected Array<Filter> filters;
    protected OrderByClause orderByClause;

    public FilteringProvider()
    {
        filterResolver = createFilterResolver();
        orderByResolver = createOrderByResolver();
    }

    public abstract T applyFiltering();

    public abstract E createFilterResolver();

    public abstract K createOrderByResolver();

    public FilteringProvider setQuery(T query)
    {
        this.query = query;
        return this;
    }

    /**
     * Set filters in new array instance in reverse order.
     *
     * @param filters Filters array, not null
     * @return this
     */
    public FilteringProvider setFilters(Array<Filter> filters)
    {
        this.filters = new Array<>(filters);
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        return this;
    }

    public FilteringProvider setOrderByClause(OrderByClause orderByClause)
    {
        this.orderByClause = orderByClause;
        return this;
    }

    public void clear()
    {
        if (filters != null)
            filters.clear();
        query = null;
        orderByClause = null;
    }
}
