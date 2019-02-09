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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Map;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnDataValidator;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;

@PrepareForTest({
        GdxNativesLoader.class, FirebaseDatabase.class,
        ProviderQueryFiltering.class
//        OnDataChangeQuery.class,
})
public class QueryOnDataChangeTest extends AndroidContextTest {

    private FirebaseDatabase firebaseDatabase;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseDatabase.class);
        firebaseDatabase = PowerMockito.mock(FirebaseDatabase.class);
        Mockito.when(FirebaseDatabase.getInstance()).thenReturn(firebaseDatabase);
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        Database databaseDistribution = Mockito.mock(Database.class);
        QueryOnDataChange queryOnDataChange = new QueryOnDataChange(databaseDistribution);

        // When
        ArgumentsValidator argumentsValidator = queryOnDataChange.createArgumentsValidator();

        // Then
        Assert.assertNotNull(argumentsValidator);
        Assert.assertTrue(argumentsValidator instanceof OnDataValidator);
    }

    @Test
    public void run() {
        // Given
        final Database databaseDistribution = Mockito.spy(Database.class);
        final DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(firebaseDatabase.getReference(Mockito.anyString())).thenReturn(databaseReference);
        Mockito.when(databaseDistribution.inReference(Mockito.anyString())).thenCallRealMethod();
        final QueryOnDataChange queryOnDataChange = new QueryOnDataChange(databaseDistribution);
        final ConverterPromise promise = Mockito.mock(ConverterPromise.class);

        // When
        databaseDistribution.inReference("/test");
        queryOnDataChange.with(promise).withArgs(Map.class).execute();

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).addValueEventListener(Mockito.any(ValueEventListener.class));
    }

    @Test
    public void run_detach() {
        // Given
        final Database databaseDistribution = Mockito.spy(Database.class);
        final DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);
        Mockito.when(firebaseDatabase.getReference(Mockito.anyString())).thenReturn(databaseReference);
        Mockito.when(databaseDistribution.inReference(Mockito.anyString())).thenCallRealMethod();
        QueryOnDataChange queryOnDataChange = new QueryOnDataChange(databaseDistribution);
        final ConverterPromise promise = Mockito.spy(ConverterPromise.class);

        // When
        databaseDistribution.inReference("/test");
        queryOnDataChange.with(promise).withArgs(String.class).execute();
        promise.cancel();

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).removeEventListener(Mockito.any(ValueEventListener.class));
    }
}