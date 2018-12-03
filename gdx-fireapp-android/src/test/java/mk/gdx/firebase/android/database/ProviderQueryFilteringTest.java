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

package mk.gdx.firebase.android.database;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.database.Query;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;


@PrepareForTest({GdxNativesLoader.class})
public class ProviderQueryFilteringTest extends AndroidContextTest {

    @Test
    public void applyFiltering1() {
        // Given
        ProviderQueryFiltering provider = new ProviderQueryFiltering();
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, "test");
        Array<Filter> filters = new Array<>(new Filter[]{new Filter(FilterType.LIMIT_FIRST, 2)});
        Query query = Mockito.mock(Query.class);
        Mockito.when(query.limitToFirst(Mockito.anyInt())).thenReturn(query);
        Mockito.when(query.orderByKey()).thenReturn(query);
        provider.setQuery(query);
        provider.setFilters(filters);
        provider.setOrderByClause(orderByClause);

        // When
        Object result = provider.applyFiltering();

        // Then
        Assert.assertNotNull(result);
    }

    @Test
    public void createFilterResolver() {
        // Given
        ProviderQueryFiltering provider = new ProviderQueryFiltering();

        // When
        Object result = provider.createFilterResolver();

        // Then
        Assert.assertNotNull(result);
    }

    @Test
    public void createOrderByResolver() {
        // Given
        ProviderQueryFiltering provider = new ProviderQueryFiltering();

        // When
        Object result = provider.createOrderByResolver();

        // Then
        Assert.assertNotNull(result);
    }
}