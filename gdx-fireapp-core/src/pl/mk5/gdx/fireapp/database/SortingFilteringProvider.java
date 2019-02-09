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

package pl.mk5.gdx.fireapp.database;

import com.badlogic.gdx.utils.Array;

/**
 * Provides filtering flow.
 *
 * @param <T> Firebase database Query type
 * @param <E> Filter resolver type
 * @param <K> Order-by resolver type
 */
public abstract class SortingFilteringProvider<T, E extends FilterResolver, K extends OrderByResolver> {

    protected E filterResolver;
    protected K orderByResolver;
    protected T query;
    protected final Array<Filter> filters;
    protected OrderByClause orderByClause;

    public SortingFilteringProvider() {
        filters = new Array<>();
        filterResolver = createFilterResolver();
        orderByResolver = createOrderByResolver();
    }

    /**
     * @return The query with filtering and soring applied - if any
     */
    @SuppressWarnings("unchecked")
    public T applyFiltering() {
        Filter filter;
        if (orderByClause != null) {
            query = (T) orderByResolver.resolve(orderByClause, query);
        }
        while (filters.size > 0) {
            filter = filters.pop();
            query = (T) filterResolver.resolve(filter.getFilterType(), query, filter.getFilterArguments());
        }
        return query;
    }

    public abstract E createFilterResolver();

    public abstract K createOrderByResolver();

    public SortingFilteringProvider setQuery(T query) {
        this.query = query;
        return this;
    }

    /**
     * Set filters in new array instance in reverse order.
     *
     * @param filters Filters array, not null
     * @return this
     */
    public SortingFilteringProvider setFilters(Array<Filter> filters) {
        if (filters == null) return this;
        this.filters.clear();
        this.filters.addAll(filters);
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        return this;
    }

    public SortingFilteringProvider setOrderByClause(OrderByClause orderByClause) {
        this.orderByClause = orderByClause;
        return this;
    }

    public void clear() {
        filters.clear();
        query = null;
        orderByClause = null;
    }
}
