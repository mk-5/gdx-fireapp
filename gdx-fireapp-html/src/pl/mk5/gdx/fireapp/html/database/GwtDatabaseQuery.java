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

package pl.mk5.gdx.fireapp.html.database;

import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.database.Filter;
import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.queries.GdxFireappQuery;
import pl.mk5.gdx.fireapp.html.firebase.ScriptRunner;

/**
 * Provides flow for html firebase call.
 */
abstract class GwtDatabaseQuery<R> extends GdxFireappQuery<Database, R> {

    protected String databaseReferencePath;
    protected ProviderJsFiltering providerJsFiltering;

    protected DatabaseReference databaseReference;
    private Array<Filter> filtersToApply;
    private OrderByClause orderByToApply;

    GwtDatabaseQuery(Database databaseDistribution) {
        super(databaseDistribution);
        providerJsFiltering = new ProviderJsFiltering();
    }

    @Override
    protected void prepare() {
        databaseReferencePath = databaseDistribution.databaseReference();
    }

    @Override
    @SuppressWarnings("unchecked")
    protected R run() {
        ScriptRunner.firebaseScript(new ScriptRunner.ScriptDBAction(databaseReferencePath) {
            @Override
            public void run() {
                databaseReference = DatabaseReference.of(databaseReferencePath);
                if (filtersToApply != null || orderByToApply != null) {
                    GdxFIRLogger.log("Applies filters and sorting to database reference...");
                    providerJsFiltering
                            .setFilters(filtersToApply)
                            .setOrderByClause(orderByToApply)
                            .setQuery(databaseReference);
                    databaseReference = providerJsFiltering.applyFiltering();
                    filtersToApply = null;
                    orderByToApply = null;
                }
                runJS();
            }
        });
        return null;
    }

    /**
     * Filters and order by will be clear in the GdxFireappQuery flow,
     * so we save it here for a moment. All database flow need to be run
     * in {@link #runJS()}.
     */
    @Override
    protected void applyFilters() {
        if (filters.size > 0) {
            filtersToApply = new Array<>();
            filtersToApply.addAll(filters);
        }
        if (orderByClause != null) {
            orderByToApply = orderByClause;
        }
    }

    @Override
    protected void terminate() {
        databaseDistribution.terminateOperation();
        databaseReferencePath = null;
        databaseReference = null;
        filtersToApply = null;
        orderByToApply = null;
    }

    /**
     * Execute database operation after firebase.js was loaded.
     */
    protected abstract void runJS();
}
