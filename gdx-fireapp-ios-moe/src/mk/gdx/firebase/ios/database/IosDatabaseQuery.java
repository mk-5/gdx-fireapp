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

package mk.gdx.firebase.ios.database;

import com.google.firebasedatabase.FIRDatabaseQuery;

import mk.gdx.firebase.database.queries.GdxFireappQuery;
import mk.gdx.firebase.ios.database.providers.FIRDatabaseQueryFilteringProvider;

/**
 *
 */
public abstract class IosDatabaseQuery<R> extends GdxFireappQuery<Database, R>
{
    protected static final String SHOULD_BE_RUN_WITH_DATABASE_REFERENCE = "Set value should be call with FIRDatabaseReference instance.";

    protected FIRDatabaseQuery query;
    protected FIRDatabaseQueryFilteringProvider filtersProvider;

    public IosDatabaseQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
    }

    @Override
    protected void prepare()
    {
        query = databaseDistribution.dbReference();
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
        filters.clear();
        orderByClause = null;
    }
}
