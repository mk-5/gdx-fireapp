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

package mk.gdx.firebase.ios.database;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import bindings.google.firebasedatabase.FIRDatabaseQuery;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.ios.GdxIOSAppTest;


@PrepareForTest({NatJ.class, FIRDatabaseQuery.class})
public class ResolverFIRQueryOrderByTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRDatabaseQuery.class);
    }

    @Test
    public void resolve_orderByChild() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_CHILD);
        orderByClause.setArgument("test_argument");
        FIRDatabaseQuery firDatabaseQuery = PowerMockito.mock(FIRDatabaseQuery.class);
        ResolverFIRQueryOrderBy resolver = new ResolverFIRQueryOrderBy();

        // When
        resolver.resolve(orderByClause, firDatabaseQuery);

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).queryOrderedByChild(Mockito.eq("test_argument"));
    }

    @Test
    public void resolve_orderByKey() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_KEY);
        FIRDatabaseQuery firDatabaseQuery = PowerMockito.mock(FIRDatabaseQuery.class);
        ResolverFIRQueryOrderBy resolver = new ResolverFIRQueryOrderBy();

        // When
        resolver.resolve(orderByClause, firDatabaseQuery);

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).queryOrderedByKey();
    }

    @Test
    public void resolve_orderByValue() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_VALUE);
        FIRDatabaseQuery firDatabaseQuery = PowerMockito.mock(FIRDatabaseQuery.class);
        ResolverFIRQueryOrderBy resolver = new ResolverFIRQueryOrderBy();

        // When
        resolver.resolve(orderByClause, firDatabaseQuery);

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).queryOrderedByValue();
    }
}