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
import com.google.firebase.database.DataSnapshot;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.List;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.database.pojos.OrderByClause;

@PrepareForTest({GdxNativesLoader.class, ResolverDataSnapshotOrderBy.class})
public class ResolverDataCallbackOnDataTest extends AndroidContextTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(ResolverDataSnapshotOrderBy.class);
    }

    @Test
    public void resolve() {
        // Given
        Mockito.when(ResolverDataSnapshotOrderBy.shouldResolveOrderBy(
                Mockito.any(OrderByClause.class), Mockito.any(Class.class), Mockito.any(DataSnapshot.class)))
                .thenReturn(true);
        List list = Mockito.mock(List.class);
        Mockito.when(ResolverDataSnapshotOrderBy.resolve(Mockito.any(DataSnapshot.class))).thenReturn(list);
        DataCallback dataCallback = Mockito.mock(DataCallback.class);

        // When
        ResolverDataCallbackOnData.resolve(String.class, Mockito.mock(OrderByClause.class), Mockito.mock(DataSnapshot.class), dataCallback);

        // Then
        Mockito.verify(dataCallback, VerificationModeFactory.times(1)).onData(Mockito.refEq(list));
    }

    @Test
    public void resolve2() {
        // Given
        Mockito.when(ResolverDataSnapshotOrderBy.shouldResolveOrderBy(
                Mockito.any(OrderByClause.class), Mockito.any(Class.class), Mockito.any(DataSnapshot.class)))
                .thenReturn(false);
        DataCallback dataCallback = Mockito.mock(DataCallback.class);
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        Mockito.when(dataSnapshot.getValue()).thenReturn("snapshot_value");

        // When
        ResolverDataCallbackOnData.resolve(String.class, Mockito.mock(OrderByClause.class), dataSnapshot, dataCallback);

        // Then
        Mockito.verify(dataCallback, VerificationModeFactory.times(1)).onData(Mockito.eq("snapshot_value"));
    }
}