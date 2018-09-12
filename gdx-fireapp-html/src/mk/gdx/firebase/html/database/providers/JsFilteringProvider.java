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

package mk.gdx.firebase.html.database.providers;

import mk.gdx.firebase.database.SortingFilteringProvider;
import mk.gdx.firebase.html.database.DatabaseReference;
import mk.gdx.firebase.html.database.resolvers.DatabaseReferenceFilterResolver;
import mk.gdx.firebase.html.database.resolvers.DatabaseReferenceOrderByResolver;

/**
 * Applies filtering and sorting to the js database reference.
 */
public class JsFilteringProvider extends SortingFilteringProvider<DatabaseReference, DatabaseReferenceFilterResolver, DatabaseReferenceOrderByResolver> {
    @Override
    public DatabaseReferenceFilterResolver createFilterResolver() {
        return new DatabaseReferenceFilterResolver();
    }

    @Override
    public DatabaseReferenceOrderByResolver createOrderByResolver() {
        return new DatabaseReferenceOrderByResolver();
    }
}
