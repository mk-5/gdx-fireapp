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
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.distributions.DatabaseDistribution;

/**
 * Abstraction for firebase database query.
 * <p>
 * Holds basic shared features of all queries: filtering and terminate operation after each query.
 *
 * @param <T> Target DatabaseDistribution
 * @param <R> Query execution return type
 */
public abstract class GdxFireappQuery<T extends DatabaseDistribution, R>
{

    protected T databaseDistribution;
    protected Array<Filter> filters;
    protected OrderByClause orderByClause;
    protected Array<Object> arguments;
    protected ArgumentsValidator argumentsValidator;

    public GdxFireappQuery(T databaseDistribution)
    {
        this.databaseDistribution = databaseDistribution;
        filters = new Array<>();
        arguments = new Array<>();
        argumentsValidator = createArgumentsValidator();
    }

    public GdxFireappQuery withArgs(Object... arguments)
    {
        this.arguments.addAll(arguments);
        return this;
    }

    public GdxFireappQuery with(Array<Filter> filters)
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
        if (argumentsValidator != null)
            argumentsValidator.validate(arguments);
        prepare();
        if (filters.size > 0 || orderByClause != null)
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

    /**
     * @return ArgumentsValidator, may be null
     */
    protected abstract ArgumentsValidator createArgumentsValidator();

    /**
     * Applies the filters/order-by only if they are present.
     */
    protected abstract void applyFilters();

    protected abstract R run();

    protected abstract void terminate();
}