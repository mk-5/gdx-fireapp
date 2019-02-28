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

package pl.mk5.gdx.fireapp.ios.database;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.OnConnectionValidator;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;


@PrepareForTest({FIRDatabase.class, NatJ.class})
public class QueryConnectionStatusTest extends GdxIOSAppTest {

    @After
    public void afterClass() {
    }

    @Test
    public void prepare() {
        // Given
        PowerMockito.mockStatic(FIRDatabase.class);
        FIRDatabase firDatabase = PowerMockito.mock(FIRDatabase.class);
        PowerMockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        QueryConnectionStatus query = new QueryConnectionStatus(Mockito.mock(Database.class));

        // When
        query.prepare();

        // Then
        Mockito.verify(firDatabase, VerificationModeFactory.times(1)).referenceWithPath(Mockito.eq(".info/connected"));
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        QueryConnectionStatus query = new QueryConnectionStatus(Mockito.mock(Database.class));

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
        QueryConnectionStatus query = new QueryConnectionStatus(database);
        FutureListenerPromise promise = Mockito.spy(FutureListenerPromise.class);

        // When
        query.with(promise).execute();

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).observeEventTypeWithBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlock) Mockito.any());
    }

    @Test
    public void run_withCancel() {
        // Given
        long handleValue = 10L;
        PowerMockito.mockStatic(FIRDatabase.class);
        FIRDatabase firDatabase = PowerMockito.mock(FIRDatabase.class);
        PowerMockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        FIRDatabaseReference firDatabaseQuery = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseQuery);
        Mockito.when(firDatabaseQuery.observeEventTypeWithBlock(Mockito.anyLong(), (QueryConnectionStatus.ConnectionBlock) Mockito.any())).thenReturn(handleValue);
        Database database = Mockito.mock(Database.class);
        QueryConnectionStatus query = new QueryConnectionStatus(database);
        FutureListenerPromise promise = Mockito.spy(FutureListenerPromise.class);

        // When
        query.with(promise).execute();
        promise.cancel();

        // Then
        Mockito.verify(firDatabaseQuery, VerificationModeFactory.times(1)).removeObserverWithHandle(Mockito.eq(handleValue));
    }
}