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

package mk.gdx.firebase.android.database;

import com.google.firebase.database.Query;

import mk.gdx.firebase.android.database.providers.QueryFilteringProvider;
import mk.gdx.firebase.android.database.resolvers.QueryFilterResolver;
import mk.gdx.firebase.android.database.resolvers.QueryOrderByResolver;
import mk.gdx.firebase.database.FilteringProvider;
import mk.gdx.firebase.database.queries.GdxFireappQuery;

/**
 * Provides flow for android firebase database call.
 * <p>
 * Flow is as follow:
 * <p>
 * - Gets {@code Query} instance and databasePath from {@link Database}.
 * - Applies filters if needed it
 * - Do some action on db
 * - Terminate query: clear given filters - if any - and call {@link Database#terminateOperation()}
 *
 * @param <R> Return type of {@link GdxFireappQuery#run()} method.
 */
public abstract class AndroidDatabaseQuery<R> extends GdxFireappQuery<Database, R>
{
    protected static final String SHOULD_BE_RUN_WITH_DATABASE_REFERENCE = "Set value should be call with DatabaseReference instance.";

    protected String databasePath;
    protected Query query;
    protected QueryFilteringProvider filtersProvider;

    public AndroidDatabaseQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
        filtersProvider = new QueryFilteringProvider();
    }

    @Override
    protected void prepare()
    {
        query = databaseDistribution.databaseReference();
        databasePath = databaseDistribution.getDatabasePath();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void applyFilters()
    {
        filtersProvider.setFilters(filters)
                .setOrderByClause(orderByClause)
                .setQuery(query);
    }

    @Override
    protected void terminate()
    {
        databaseDistribution.terminateOperation();
        orderByClause = null;
        filtersProvider.clear();
    }
}
