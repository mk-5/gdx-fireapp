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

package mk.gdx.firebase.ios.database.queries;

import com.badlogic.gdx.utils.LongArray;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.OnConnectionValidator;
import mk.gdx.firebase.ios.GdxIOSAppTest;
import mk.gdx.firebase.ios.database.Database;
import mk.gdx.firebase.listeners.ConnectedListener;


@PrepareForTest({FIRDatabase.class, NatJ.class})
public class ConnectionStatusQueryTest extends GdxIOSAppTest {

    @After
    public void afterClass() {
        ((LongArray) Whitebox.getInternalState(ConnectionStatusQuery.class, "handles")).clear();
    }

    @Test
    public void prepare() {
        // Given
        PowerMockito.mockStatic(FIRDatabase.class);
        FIRDatabase firDatabase = PowerMockito.mock(FIRDatabase.class);
        PowerMockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        ConnectionStatusQuery query = new ConnectionStatusQuery(Mockito.mock(Database.class));

        // When
        query.prepare();

        // Then
        Mockito.verify(firDatabase, VerificationModeFactory.times(1)).referenceWithPath(Mockito.eq(".info/connected"));
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        ConnectionStatusQuery query = new ConnectionStatusQuery(Mockito.mock(Database.class));

        // When
        ArgumentsValidator argumentsValidator = query.createArgumentsValidator();

        // Then
        Assert.assertTrue(argumentsValidator instanceof OnConnectionValidator);
    }

    @Test
    public void run_withValidArgument() {
        // Given
        long handleValue = 10L;
        PowerMockito.mockStatic(FIRDatabase.class);
        FIRDatabase firDatabase = PowerMockito.mock(FIRDatabase.class);
        PowerMockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        FIRDatabaseReference firDatabaseQuery = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseQuery);
        Mockito.when(firDatabaseQuery.observeEventTypeWithBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlock) Mockito.any())).thenReturn(handleValue);
        Database database = Mockito.mock(Database.class);
        ConnectionStatusQuery query = new ConnectionStatusQuery(database);
        ConnectedListener listener = Mockito.mock(ConnectedListener.class);

        // When
        query.withArgs(listener).execute();

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).observeEventTypeWithBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlock) Mockito.any());
        Assert.assertTrue(((LongArray) Whitebox.getInternalState(ConnectionStatusQuery.class, "handles")).contains(handleValue));
    }

    @Test
    public void run_withValidNullArgument() {
        // Given
        long handleValue = 10L;
        PowerMockito.mockStatic(FIRDatabase.class);
        FIRDatabase firDatabase = PowerMockito.mock(FIRDatabase.class);
        PowerMockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        FIRDatabaseReference firDatabaseQuery = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseQuery);
        Mockito.when(firDatabaseQuery.observeEventTypeWithBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlock) Mockito.any())).thenReturn(handleValue);
        Database database = Mockito.mock(Database.class);
        ConnectionStatusQuery query = new ConnectionStatusQuery(database);
        ConnectedListener listener = null;
        ((LongArray) Whitebox.getInternalState(ConnectionStatusQuery.class, "handles")).add(handleValue);

        // When
        query.withArgs(listener).execute();

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).removeObserverWithHandle(Mockito.eq(handleValue));
        Assert.assertTrue(((LongArray) Whitebox.getInternalState(ConnectionStatusQuery.class, "handles")).size == 0);
    }
}