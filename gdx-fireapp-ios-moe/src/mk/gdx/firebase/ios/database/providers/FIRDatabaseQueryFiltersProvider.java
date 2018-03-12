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

package mk.gdx.firebase.ios.database.providers;

import com.badlogic.gdx.utils.Array;
import com.google.firebasedatabase.FIRDatabaseQuery;

import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.ios.database.proxies.FiltersAffectedQuery;
import mk.gdx.firebase.ios.database.resolvers.IosOrderByResolver;

/**
 * Provides decision between call {@code FIRDatabaseReference} or {@code FIRDatabaseQuery} based at current query context.
 */
public class FIRDatabaseQueryFiltersProvider implements FiltersAffectedQuery
{

    private OrderByClause orderByClause;
    private Array<Filter> filters;
    private FIRDatabaseQuery databaseQuery;
    private IosOrderByResolver orderByResolver;
    private FIRQueryFilterProvider firQueryFilterProvider;

    public FIRDatabaseQueryFiltersProvider(Array<Filter> filters, OrderByClause orderByClause, FIRDatabaseQuery databaseQuery)
    {
        this.orderByClause = orderByClause;
        // The first go to the end, now i can use .pop() later.
        filters.reverse();
        this.filters = filters;
        this.databaseQuery = databaseQuery;
        orderByResolver = new IosOrderByResolver();
        firQueryFilterProvider = new FIRQueryFilterProvider();
    }

    @Override
    public long observeEventTypeWithBlockWithCancelBlock(long eventType, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1 block, FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_2 cancelBlock)
    {
        databaseQuery = processQuery();
        return databaseQuery.observeEventTypeWithBlockWithCancelBlock(eventType, block, cancelBlock);
    }

    @Override
    public void observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(long eventType, FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1 block, FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2 cancelBlock)
    {
        databaseQuery = processQuery();
        databaseQuery.observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(eventType, block, cancelBlock);
    }

    private FIRDatabaseQuery processQuery()
    {
        if (orderByClause != null) {
            databaseQuery = orderByResolver.resolve(orderByClause, databaseQuery);
        }
        Filter filter = null;
        while (filters.size > 0) {
            filter = filters.pop();
            databaseQuery = firQueryFilterProvider.apply(filter.getFilterType(), databaseQuery, filter.getFilterArguments());
        }
        return databaseQuery;
    }
}
