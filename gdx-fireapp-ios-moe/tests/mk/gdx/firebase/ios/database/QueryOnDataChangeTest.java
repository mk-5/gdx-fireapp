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
import mk.gdx.firebase.database.validators.OnDataValidator;
import mk.gdx.firebase.ios.GdxIOSAppTest;
import mk.gdx.firebase.listeners.DataChangeListener;

@PrepareForTest({NatJ.class, FIRDatabase.class, FIRDatabaseReference.class, FIRDatabaseQuery.class, Database.class})
public class QueryOnDataChangeTest extends GdxIOSAppTest {

    private FIRDatabase firDatabase;
    private FIRDatabaseReference firDatabaseReference;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRDatabase.class);
        firDatabase = Mockito.mock(FIRDatabase.class);
        firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseReference);
        Mockito.when(FIRDatabase.database()).thenReturn(firDatabase);
    }

    @AfterClass
    public static void afterClass() {
        Whitebox.setInternalState(QueryOnDataChange.class, "observersManager", new DataObserversManager());
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        QueryOnDataChange query = new QueryOnDataChange(Mockito.mock(Database.class));

        // When
        ArgumentsValidator argumentsValidator = query.createArgumentsValidator();

        // Then
        Assert.assertTrue(argumentsValidator instanceof OnDataValidator);
    }

    @Test
    public void run() throws Exception {
        // Given
        DataObserversManager dataObserversManager = Mockito.spy(new DataObserversManager());
        Whitebox.setInternalState(QueryOnDataChange.class, "observersManager", dataObserversManager);
        PowerMockito.mockStatic(Database.class);
        Database database = PowerMockito.mock(Database.class);
        PowerMockito.when(database, "dbReference").thenReturn(firDatabaseReference);
        Whitebox.setInternalState(database, "dbReference", firDatabaseReference);
        Whitebox.setInternalState(database, "databasePath", "/test");
        QueryOnDataChange query = new QueryOnDataChange(database);
        Class dataType = String.class;
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);


        // When
        query.withArgs(dataType, listener).execute();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).observeEventTypeWithBlockWithCancelBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1) Mockito.any(), Mockito.any());
        Mockito.verify(dataObserversManager, VerificationModeFactory.times(1)).addNewListener(Mockito.anyString(), Mockito.any());
    }

    @Test
    public void run_detach() throws Exception {
        // Given
        long handleValue = 10L;
        DataObserversManager dataObserversManager = Mockito.spy(new DataObserversManager());
        dataObserversManager.addNewListener("/test", handleValue);
        Whitebox.setInternalState(QueryOnDataChange.class, "observersManager", dataObserversManager);
        PowerMockito.mockStatic(Database.class);
        Database database = PowerMockito.mock(Database.class);
        PowerMockito.when(database, "dbReference").thenReturn(firDatabaseReference);
        Whitebox.setInternalState(database, "dbReference", firDatabaseReference);
        Whitebox.setInternalState(database, "databasePath", "/test");
        QueryOnDataChange query = new QueryOnDataChange(database);
        Class dataType = String.class;
        DataChangeListener listener = null;


        // When
        query.withArgs(dataType, listener).execute();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).removeObserverWithHandle(Mockito.eq(handleValue));
        Mockito.verify(dataObserversManager, VerificationModeFactory.times(1)).removeListenersForPath(Mockito.anyString());
    }
}