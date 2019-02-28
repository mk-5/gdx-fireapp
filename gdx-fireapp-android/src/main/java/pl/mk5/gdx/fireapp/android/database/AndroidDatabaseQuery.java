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

package pl.mk5.gdx.fireapp.android.database;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import pl.mk5.gdx.fireapp.database.queries.GdxFireappQuery;

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
abstract class AndroidDatabaseQuery<R> extends GdxFireappQuery<Database, R> {
    static final String SHOULD_BE_RUN_WITH_DATABASE_REFERENCE = "Set value should be call with DatabaseReference instance.";

    protected Query query;
    ProviderQueryFiltering filtersProvider;

    AndroidDatabaseQuery(Database databaseDistribution, String databasePath) {
        super(databaseDistribution, databasePath);
        filtersProvider = new ProviderQueryFiltering();
    }

    @Override
    protected void prepare() {
        query = FirebaseDatabase.getInstance().getReference(databasePath);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void applyFilters() {
        filtersProvider.setFilters(filters)
                .setOrderByClause(orderByClause)
                .setQuery(query);
    }

    @Override
    protected void terminate() {
        databaseDistribution.terminateOperation();
        orderByClause = null;
        filtersProvider.clear();
    }
}
