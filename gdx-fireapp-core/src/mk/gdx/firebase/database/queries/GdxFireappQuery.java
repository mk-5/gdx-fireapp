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

package mk.gdx.firebase.database.queries;

import com.badlogic.gdx.utils.Array;

import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.distributions.DatabaseDistribution;

/**
 * Abstraction for firebase database query.
 * <p>
 * Holds basic shared features of all queries: filtering and terminate operation after each query.
 */
public abstract class GdxFireappQuery<T extends DatabaseDistribution, R>
{

    protected T databaseDistribution;
    protected Array<Filter> filters;
    protected OrderByClause orderByClause;
    protected Array<Object> arguments;

    public GdxFireappQuery(T databaseDistribution)
    {
        this.databaseDistribution = databaseDistribution;
        filters = new Array<>();
        arguments = new Array<>();
    }

    public GdxFireappQuery with(Object... arguments)
    {
        this.arguments.addAll(arguments);
        return this;
    }

    public GdxFireappQuery with(Filter... filters)
    {
        this.filters.addAll(filters);
        return this;
    }

    public GdxFireappQuery with(OrderByClause orderByClause)
    {
        this.orderByClause = orderByClause;
        return this;
    }

    public final R execute()
    {
        prepare();
        applyFilters();
        R result = run();
        terminate();
        filters.clear();
        arguments.clear();
        return result;
    }

    protected void prepare()
    {
        // To overwrite
    }

    protected abstract void applyFilters();

    protected abstract R run();

    protected abstract void terminate();
}
