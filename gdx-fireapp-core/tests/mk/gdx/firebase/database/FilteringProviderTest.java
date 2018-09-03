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

package mk.gdx.firebase.database;

import com.badlogic.gdx.utils.Array;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.database.queries.GdxFireappQuery;

@RunWith(MockitoJUnitRunner.class)
public class FilteringProviderTest {

    @Test
    public void setQuery() {
        // Given
        FilteringProvider filteringProvider = new FilteringProvider() {
            @Override
            public Object applyFiltering() {
                return null;
            }

            @Override
            public FilterResolver createFilterResolver() {
                return null;
            }

            @Override
            public OrderByResolver createOrderByResolver() {
                return null;
            }
        };
        GdxFireappQuery query = Mockito.mock(GdxFireappQuery.class);

        // When
        filteringProvider.setQuery(query);

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(filteringProvider, "query"));
    }

    @Test
    public void setFilters() {
        // Given
        FilteringProvider filteringProvider = new FilteringProvider() {
            @Override
            public Object applyFiltering() {
                return null;
            }

            @Override
            public FilterResolver createFilterResolver() {
                return null;
            }

            @Override
            public OrderByResolver createOrderByResolver() {
                return null;
            }
        };
        Array array = new Array();
        Array array2 = new Array();
        array2.add("test");
        Whitebox.setInternalState(filteringProvider, "filters", array);

        // When
        filteringProvider.setFilters(array2);

        // Then
        Assert.assertTrue(array.contains("test", false));
    }

    @Test
    public void setOrderByClause() {
        // Given
        FilteringProvider filteringProvider = new FilteringProvider() {
            @Override
            public Object applyFiltering() {
                return null;
            }

            @Override
            public FilterResolver createFilterResolver() {
                return null;
            }

            @Override
            public OrderByResolver createOrderByResolver() {
                return null;
            }
        };
        OrderByClause orderByClause = Mockito.mock(OrderByClause.class);

        // When
        filteringProvider.setOrderByClause(orderByClause);

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(filteringProvider, "orderByClause"));
    }

    @Test
    public void clear() {
        // Given
        FilteringProvider filteringProvider = new FilteringProvider() {
            @Override
            public Object applyFiltering() {
                return null;
            }

            @Override
            public FilterResolver createFilterResolver() {
                return null;
            }

            @Override
            public OrderByResolver createOrderByResolver() {
                return null;
            }
        };
        Array array = new Array();
        array.add("test");
        filteringProvider.setOrderByClause(Mockito.mock(OrderByClause.class));
        filteringProvider.setFilters(array);
        filteringProvider.setQuery(Mockito.mock(GdxFireappQuery.class));

        // When
        filteringProvider.clear();

        // Then
        Assert.assertNull(Whitebox.getInternalState(filteringProvider, "query"));
        Assert.assertNull(Whitebox.getInternalState(filteringProvider, "orderByClause"));
        Assert.assertTrue(((Array) Whitebox.getInternalState(filteringProvider, "filters")).size == 0);
    }
}