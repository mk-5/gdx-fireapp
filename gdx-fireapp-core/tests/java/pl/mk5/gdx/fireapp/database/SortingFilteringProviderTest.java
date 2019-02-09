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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import pl.mk5.gdx.fireapp.database.queries.GdxFireappQuery;

@RunWith(MockitoJUnitRunner.class)
public class SortingFilteringProviderTest {

    @Test
    public void setQuery() {
        // Given
        SortingFilteringProvider sortingFilteringProvider = new SortingFilteringProvider() {
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
        sortingFilteringProvider.setQuery(query);

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(sortingFilteringProvider, "query"));
    }

    @Test
    public void setFilters() {
        // Given
        SortingFilteringProvider sortingFilteringProvider = new SortingFilteringProvider() {
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
        Whitebox.setInternalState(sortingFilteringProvider, "filters", array);

        // When
        sortingFilteringProvider.setFilters(array2);

        // Then
        Assert.assertTrue(array.contains("test", false));
    }

    @Test
    public void setOrderByClause() {
        // Given
        SortingFilteringProvider sortingFilteringProvider = new SortingFilteringProvider() {
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
        sortingFilteringProvider.setOrderByClause(orderByClause);

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(sortingFilteringProvider, "orderByClause"));
    }

    @Test
    public void clear() {
        // Given
        SortingFilteringProvider sortingFilteringProvider = new SortingFilteringProvider() {
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
        sortingFilteringProvider.setOrderByClause(Mockito.mock(OrderByClause.class));
        sortingFilteringProvider.setFilters(array);
        sortingFilteringProvider.setQuery(Mockito.mock(GdxFireappQuery.class));

        // When
        sortingFilteringProvider.clear();

        // Then
        Assert.assertNull(Whitebox.getInternalState(sortingFilteringProvider, "query"));
        Assert.assertNull(Whitebox.getInternalState(sortingFilteringProvider, "orderByClause"));
        Assert.assertTrue(((Array) Whitebox.getInternalState(sortingFilteringProvider, "filters")).size == 0);
    }

    @Test
    public void applyFiltering() {
        // Given
        SortingFilteringProvider provider = new SortingFilteringProvider() {

            @Override
            public FilterResolver createFilterResolver() {
                return Mockito.mock(FilterResolver.class);
            }

            @Override
            public OrderByResolver createOrderByResolver() {
                return Mockito.mock(OrderByResolver.class);
            }
        };
        Array array = new Array();
        array.add(new Filter());
        provider.setOrderByClause(Mockito.mock(OrderByClause.class));
        provider.setFilters(array);
        provider.setQuery(Mockito.mock(GdxFireappQuery.class));

        // When
        provider.applyFiltering();

        // Then
        Mockito.verify(provider.filterResolver, VerificationModeFactory.times(1)).resolve(Mockito.nullable(FilterType.class), Mockito.nullable(Object.class), Mockito.nullable(Object[].class));
        Mockito.verify(provider.orderByResolver, VerificationModeFactory.times(1)).resolve(Mockito.nullable(OrderByClause.class), Mockito.any());
    }
}