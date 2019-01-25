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

package mk.gdx.firebase.html.database;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ArrayReflection;
import com.badlogic.gwtref.client.ReflectionCache;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;

import mk.gdx.firebase.database.Filter;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByClause;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.html.firebase.ScriptRunner;

@PrepareForTest({ScriptRunner.class, DatabaseReference.class, ArrayReflection.class, ReflectionCache.class})
public class GwtDatabaseQueryTest {

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DatabaseReference.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.when(ScriptRunner.class, "firebaseScript", Mockito.any(Runnable.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        });
    }

    @Test
    public void run() {
        // Given
        ProviderJsFiltering providerJsFiltering = Mockito.mock(ProviderJsFiltering.class);
        ResolverDatabaseReferenceFilter filterResolver = Mockito.mock(ResolverDatabaseReferenceFilter.class);
        ResolverDatabaseReferenceOrderBy orderByResolver = Mockito.mock(ResolverDatabaseReferenceOrderBy.class);
        Mockito.when(providerJsFiltering.createFilterResolver()).thenReturn(filterResolver);
        Mockito.when(providerJsFiltering.createOrderByResolver()).thenReturn(orderByResolver);
        Mockito.when(providerJsFiltering.setFilters(Mockito.any(Array.class))).thenReturn(providerJsFiltering);
        Mockito.when(providerJsFiltering.setOrderByClause(Mockito.any(OrderByClause.class))).thenReturn(providerJsFiltering);
        Mockito.when(providerJsFiltering.setQuery(Mockito.any(DatabaseReference.class))).thenReturn(providerJsFiltering);
        GwtDatabaseQuery gwtDatabaseQuery = new TestQuery(Mockito.mock(Database.class));
        gwtDatabaseQuery.providerJsFiltering = providerJsFiltering;
        Array<Filter> filters = new Array<>();
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, null);
        filters.add(new Filter(FilterType.LIMIT_FIRST, 2));
        gwtDatabaseQuery.with(filters);
        gwtDatabaseQuery.with(orderByClause);

        // When
        gwtDatabaseQuery.applyFilters();
        gwtDatabaseQuery.run();

        // Then
        Mockito.verify(providerJsFiltering, VerificationModeFactory.times(1)).setFilters(Mockito.any(Array.class));
        Mockito.verify(providerJsFiltering, VerificationModeFactory.times(1)).setOrderByClause(Mockito.any(OrderByClause.class));
        Mockito.verify(providerJsFiltering, VerificationModeFactory.times(1)).setQuery(Mockito.nullable(DatabaseReference.class));
        Mockito.verify(providerJsFiltering, VerificationModeFactory.times(1)).applyFiltering();
    }

    @Test
    public void applyFilters() {
        // Given
        GwtDatabaseQuery gwtDatabaseQuery = new TestQuery(Mockito.mock(Database.class));
        Array<Filter> filters = new Array<>();
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, null);
        filters.add(new Filter(FilterType.LIMIT_FIRST, 2));
        gwtDatabaseQuery.with(filters);
        gwtDatabaseQuery.with(orderByClause);

        // When
        gwtDatabaseQuery.applyFilters();

        // Then
        Assert.assertEquals(1, ((Array) Whitebox.getInternalState(gwtDatabaseQuery, "filtersToApply")).size);
        Assert.assertNotNull(Whitebox.getInternalState(gwtDatabaseQuery, "orderByToApply"));
    }

    @Test
    public void terminate() {
        // Given
        Database database = Mockito.mock(Database.class);
        GwtDatabaseQuery gwtDatabaseQuery = new TestQuery(database);
        Array<Filter> filters = new Array<>();
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, null);
        filters.add(new Filter(FilterType.LIMIT_FIRST, 2));
        gwtDatabaseQuery.with(filters);
        gwtDatabaseQuery.with(orderByClause);

        // When
        gwtDatabaseQuery.prepare();
        gwtDatabaseQuery.run();
        gwtDatabaseQuery.terminate();

        // Then
        Mockito.verify(database, VerificationModeFactory.times(1)).terminateOperation();
        Assert.assertNull(Whitebox.getInternalState(gwtDatabaseQuery, "filtersToApply"));
        Assert.assertNull(Whitebox.getInternalState(gwtDatabaseQuery, "orderByToApply"));
        Assert.assertNull(Whitebox.getInternalState(gwtDatabaseQuery, "databaseReferencePath"));
        Assert.assertNull(Whitebox.getInternalState(gwtDatabaseQuery, "databaseReference"));
    }

    private class TestQuery extends GwtDatabaseQuery {

        public TestQuery(Database databaseDistribution) {
            super(databaseDistribution);
        }

        @Override
        protected void runJS() {

        }

        @Override
        protected ArgumentsValidator createArgumentsValidator() {
            return null;
        }
    }
}