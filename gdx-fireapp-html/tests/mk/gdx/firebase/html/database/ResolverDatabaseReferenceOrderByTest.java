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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.OrderByClause;

@PrepareForTest({DatabaseReference.class})
public class ResolverDatabaseReferenceOrderByTest {

    private DatabaseReference databaseReference;

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DatabaseReference.class);
        databaseReference = PowerMockito.mock(DatabaseReference.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void resolve_orderByChild_wrongArgument() {
        // Given
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_CHILD, null);
        ResolverDatabaseReferenceOrderBy orderByResolver = new ResolverDatabaseReferenceOrderBy();

        // When
        orderByResolver.resolve(orderByClause, databaseReference);

        // Then
        Assert.fail();
    }

    @Test
    public void resolve_orderByChild() {
        // Given
        String arg = "test";
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_CHILD, arg);
        ResolverDatabaseReferenceOrderBy orderByResolver = new ResolverDatabaseReferenceOrderBy();

        // When
        orderByResolver.resolve(orderByClause, databaseReference);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).orderByChild(Mockito.eq(arg));
    }

    @Test
    public void resolve_orderByKey() {
        // Given
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, null);
        ResolverDatabaseReferenceOrderBy orderByResolver = new ResolverDatabaseReferenceOrderBy();

        // When
        orderByResolver.resolve(orderByClause, databaseReference);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).orderByKey();
    }

    @Test
    public void resolve_orderByValue() {
        // Given
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_VALUE, null);
        ResolverDatabaseReferenceOrderBy orderByResolver = new ResolverDatabaseReferenceOrderBy();

        // When
        orderByResolver.resolve(orderByClause, databaseReference);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).orderByValue();
    }
}