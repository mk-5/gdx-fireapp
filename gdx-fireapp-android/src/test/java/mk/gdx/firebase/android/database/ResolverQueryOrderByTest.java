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

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.database.Query;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.database.OrderByClause;
import mk.gdx.firebase.database.OrderByMode;

@PrepareForTest({GdxNativesLoader.class, Query.class})
public class ResolverQueryOrderByTest extends AndroidContextTest {

    @Test
    public void resolve_orderByChild() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_CHILD);
        orderByClause.setArgument("test_argument");
        Query query = PowerMockito.mock(Query.class);
        ResolverQueryOrderBy resolver = new ResolverQueryOrderBy();

        // When
        resolver.resolve(orderByClause, query);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).orderByChild(Mockito.eq("test_argument"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_orderByChild_withoutArgument() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_CHILD);
        Query query = PowerMockito.mock(Query.class);
        ResolverQueryOrderBy resolver = new ResolverQueryOrderBy();

        // When
        resolver.resolve(orderByClause, query);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).orderByChild(Mockito.eq("test_argument"));
    }

    @Test
    public void resolve_orderByKey() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_KEY);
        Query query = PowerMockito.mock(Query.class);
        ResolverQueryOrderBy resolver = new ResolverQueryOrderBy();

        // When
        resolver.resolve(orderByClause, query);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).orderByKey();
    }

    @Test
    public void resolve_orderByValue() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_VALUE);
        Query query = PowerMockito.mock(Query.class);
        ResolverQueryOrderBy resolver = new ResolverQueryOrderBy();

        // When
        resolver.resolve(orderByClause, query);

        // Then
        Mockito.verify(query, VerificationModeFactory.times(1)).orderByValue();
    }
}