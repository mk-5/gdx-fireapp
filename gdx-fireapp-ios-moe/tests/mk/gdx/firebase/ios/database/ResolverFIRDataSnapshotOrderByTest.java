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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;
import java.util.Map;

import bindings.google.firebasedatabase.FIRDataSnapshot;
import mk.gdx.firebase.database.OrderByClause;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, FIRDataSnapshot.class})
public class ResolverFIRDataSnapshotOrderByTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRDataSnapshot.class);
    }

    @Test
    public void resolve() {
    }

    @Test
    public void shouldResolveOrderBy() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = List.class;
        FIRDataSnapshot firDataSnapshot = Mockito.mock(FIRDataSnapshot.class);
        Mockito.when(firDataSnapshot.childrenCount()).thenReturn(2L);

        // When
        boolean shouldResolve = ResolverFIRDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, firDataSnapshot);

        // Then
        Assert.assertTrue(shouldResolve);
    }

    @Test
    public void shouldResolveOrderBy_fail1() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = Map.class;
        FIRDataSnapshot firDataSnapshot = Mockito.mock(FIRDataSnapshot.class);
        Mockito.when(firDataSnapshot.childrenCount()).thenReturn(2L);

        // When
        boolean shouldResolve = ResolverFIRDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, firDataSnapshot);

        // Then
        Assert.assertFalse(shouldResolve);
    }

    @Test
    public void shouldResolveOrderBy_fail2() {
        // Given
        OrderByClause orderByClause = null;
        Class dataType = List.class;
        FIRDataSnapshot firDataSnapshot = Mockito.mock(FIRDataSnapshot.class);
        Mockito.when(firDataSnapshot.childrenCount()).thenReturn(2L);

        // When
        boolean shouldResolve = ResolverFIRDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, firDataSnapshot);

        // Then
        Assert.assertFalse(shouldResolve);
    }

    @Test
    public void shouldResolveOrderBy_fail3() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = List.class;
        FIRDataSnapshot firDataSnapshot = Mockito.mock(FIRDataSnapshot.class);
        Mockito.when(firDataSnapshot.childrenCount()).thenReturn(0L);

        // When
        boolean shouldResolve = ResolverFIRDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, firDataSnapshot);

        // Then
        Assert.assertFalse(shouldResolve);
    }
}