/*
 * Copyright 2018 mk
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

package pl.mk5.gdx.fireapp.ios.database;

import bindings.google.firebasedatabase.FIRDatabaseQuery;
import pl.mk5.gdx.fireapp.database.SortingFilteringProvider;

/**
 * Provides decision between call {@code FIRDatabaseReference} or {@code FIRDatabaseQuery} based at current query context.
 */
class ProviderFIRDatabaseQueryFiltering extends SortingFilteringProvider<FIRDatabaseQuery, ResolverFIRQueryFilter, ResolverFIRQueryOrderBy> {

    @Override
    public ResolverFIRQueryFilter createFilterResolver() {
        return new ResolverFIRQueryFilter();
    }

    @Override
    public ResolverFIRQueryOrderBy createOrderByResolver() {
        return new ResolverFIRQueryOrderBy();
    }
}
