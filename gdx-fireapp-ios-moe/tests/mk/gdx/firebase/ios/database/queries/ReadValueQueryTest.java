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
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.database.validators.ArgumentsValidator;
import mk.gdx.firebase.database.validators.ReadValueValidator;
import mk.gdx.firebase.ios.GdxIOSAppTest;
import mk.gdx.firebase.ios.database.Database;

@PrepareForTest({NatJ.class, FIRDatabase.class, FIRDatabaseReference.class, FIRDatabaseQuery.class, Database.class})
public class ReadValueQueryTest extends GdxIOSAppTest {

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

    @Test
    public void createArgumentsValidator() {
        // Given
        ReadValueQuery readValueQuery = new ReadValueQuery(Mockito.mock(Database.class));

        // When
        ArgumentsValidator argumentsValidator = readValueQuery.createArgumentsValidator();

        // Then
        Assert.assertTrue(argumentsValidator instanceof ReadValueValidator);
    }

    @Test
    public void run() throws Exception {
        // Given
        PowerMockito.mockStatic(Database.class);
        Database database = PowerMockito.mock(Database.class);
        PowerMockito.when(database, "dbReference").thenReturn(firDatabaseReference);
        Whitebox.setInternalState(database, "databasePath", "/test");
        ReadValueQuery query = new ReadValueQuery(database);
        Class dataType = String.class;
        DataCallback callback = Mockito.mock(DataCallback.class);

        // When
        query.withArgs(dataType, callback).execute();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1) Mockito.any(), Mockito.any());
    }
}