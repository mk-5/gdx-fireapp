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

package pl.mk5.gdx.fireapp.android.database;

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.database.DataSnapshot;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.ArrayList;
import java.util.List;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;
import pl.mk5.gdx.fireapp.database.OrderByClause;

@PrepareForTest({GdxNativesLoader.class, DataSnapshot.class})
public class ResolverDataSnapshotOrderByTest extends AndroidContextTest {

    @Test
    public void resolve() {
        // Given
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        DataSnapshot dataSnapshot2 = Mockito.mock(DataSnapshot.class);
        DataSnapshot dataSnapshot3 = Mockito.mock(DataSnapshot.class);
        ArrayList list = new ArrayList();
        list.add(dataSnapshot2);
        list.add(dataSnapshot3);
        Mockito.when(dataSnapshot.getChildren()).thenReturn(list);

        // When
        List result = ResolverDataSnapshotOrderBy.resolve(dataSnapshot);

        // Then
        Assert.assertEquals(2, result.size());
        Mockito.verify(dataSnapshot2, VerificationModeFactory.times(1)).getValue();
        Mockito.verify(dataSnapshot3, VerificationModeFactory.times(1)).getValue();
    }

    @Test
    public void shouldResolveOrderBy() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = List.class;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Mockito.when(dataSnapshot.getChildrenCount()).thenReturn(1L);

        // When
        boolean should = ResolverDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, dataSnapshot);

        // Then
        Assert.assertTrue(should);
    }

    @Test
    public void shouldResolveOrderBy2() {
        // Given
        OrderByClause orderByClause = null;
        Class dataType = List.class;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Mockito.when(dataSnapshot.getChildrenCount()).thenReturn(1L);

        // When
        boolean should = ResolverDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, dataSnapshot);

        // Then
        Assert.assertFalse(should);
    }

    @Test
    public void shouldResolveOrderBy3() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = String.class;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Mockito.when(dataSnapshot.getChildrenCount()).thenReturn(1L);

        // When
        boolean should = ResolverDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, dataSnapshot);

        // Then
        Assert.assertFalse(should);
    }

    @Test
    public void shouldResolveOrderBy4() {
        // Given
        OrderByClause orderByClause = new OrderByClause();
        Class dataType = String.class;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Mockito.when(dataSnapshot.getChildrenCount()).thenReturn(0L);

        // When
        boolean should = ResolverDataSnapshotOrderBy.shouldResolveOrderBy(orderByClause, dataType, dataSnapshot);

        // Then
        Assert.assertFalse(should);
    }


}