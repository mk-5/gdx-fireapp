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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Map;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.android.database.queries.OnDataChangeQuery;
import mk.gdx.firebase.database.pojos.OrderByClause;

@PrepareForTest({GdxNativesLoader.class, FirebaseDatabase.class, OnDataChangeQuery.class, Database.class})
public class DatabaseTest extends AndroidContextTest {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseDatabase.class);
        firebaseDatabase = PowerMockito.mock(FirebaseDatabase.class);
        Mockito.when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);
        databaseReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(firebaseDatabase.getReference(Mockito.anyString())).thenReturn(databaseReference);
    }

    @Test
    public void onDataChange_withNullListener() throws Exception {
        // Given
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.mock(OnDataChangeQuery.class);
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(Array.class))).thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(OrderByClause.class))).thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any(), Mockito.any())).thenReturn(query);
        Database database = new Database();

        // When
        database.inReference("/test").onDataChange(Map.class, null);

        // Then
        PowerMockito.verifyNew(OnDataChangeQuery.class);
    }
}