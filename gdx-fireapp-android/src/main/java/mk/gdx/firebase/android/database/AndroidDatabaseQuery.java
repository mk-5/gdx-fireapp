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

import mk.gdx.firebase.android.database.providers.DatabaseQueryFiltersProvider;
import mk.gdx.firebase.database.queries.GdxFireappQuery;

/**
 * TODO docs
 */
public abstract class AndroidDatabaseQuery<R> extends GdxFireappQuery<Database, R>
{
    protected Query query;
    protected DatabaseQueryFiltersProvider filtersProvider;

    public AndroidDatabaseQuery(Database databaseDistribution)
    {
        super(databaseDistribution);
        filtersProvider = new DatabaseQueryFiltersProvider();
    }

    @Override
    protected void prepare()
    {
        query = databaseDistribution.databaseReference();
    }

    @Override
    protected void applyFilters()
    {
        if (filters.size > 0 || orderByClause != null) {
            filtersProvider.setFilters(filters)
                    .setOrderByClause(orderByClause)
                    .setQuery(query);
        }
    }

    @Override
    protected void terminate()
    {
        ((Database) databaseDistribution).terminateOperation();
        filtersProvider.setFilters(null)
                .setOrderByClause(null)
                .setQuery(null);
    }
}
