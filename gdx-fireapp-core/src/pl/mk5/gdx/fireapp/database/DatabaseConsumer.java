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

package pl.mk5.gdx.fireapp.database;

import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Consumer to use with async database calls
 *
 * @param <T> Consumer type
 */
public abstract class DatabaseConsumer<T> implements Consumer<T> {
    private final String databasePath;
    private final OrderByClause orderByClause;
    private final Array<Filter> filters;

    public DatabaseConsumer(String databasePath, OrderByClause orderByClause, Array<Filter> filtersArr) {
        this.databasePath = databasePath;
        this.orderByClause = orderByClause != null ? new OrderByClause(orderByClause.getOrderByMode(), orderByClause.getArgument()) : null;
        this.filters = new Array<>();
        for (Filter filter : filtersArr) {
            filters.add(new Filter(filter.getFilterType(), filter.getFilterArguments()));
        }
    }

    public String getDatabasePath() {
        return databasePath;
    }

    public OrderByClause getOrderByClause() {
        return orderByClause;
    }

    public Array<Filter> getFilters() {
        return filters;
    }
}
