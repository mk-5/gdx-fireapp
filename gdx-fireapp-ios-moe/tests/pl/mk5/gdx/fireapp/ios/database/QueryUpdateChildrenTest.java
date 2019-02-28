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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import apple.foundation.NSDictionary;
import apple.foundation.NSMutableDictionary;
import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.database.validators.UpdateChildrenValidator;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, FIRDatabase.class, FIRDatabaseReference.class,
        FIRDatabaseQuery.class, Database.class, NSDictionary.class, NSMutableDictionary.class,
        NSDictionaryHelper.class
})
public class QueryUpdateChildrenTest extends GdxIOSAppTest {

    private FIRDatabase firDatabase;
    private FIRDatabaseReference firDatabaseReference;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRDatabase.class);
        PowerMockito.mockStatic(NSDictionary.class);
        PowerMockito.mockStatic(NSMutableDictionary.class);
        PowerMockito.mockStatic(NSDictionaryHelper.class);
        firDatabase = Mockito.mock(FIRDatabase.class);
        firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseReference);
        Mockito.when(FIRDatabase.database()).thenReturn(firDatabase);
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        QueryUpdateChildren query = new QueryUpdateChildren(Mockito.mock(Database.class), "/test");

        // When
        ArgumentsValidator argumentsValidator = query.createArgumentsValidator();

        // Then
        Assert.assertTrue(argumentsValidator instanceof UpdateChildrenValidator);
    }

    @Test
    public void run() throws Exception {
        // Given
        PowerMockito.mockStatic(Database.class);
        Database database = PowerMockito.mock(Database.class);
        PowerMockito.when(database, "dbReference").thenReturn(firDatabaseReference);
        Whitebox.setInternalState(database, "databasePath", "/test");
        QueryUpdateChildren query = new QueryUpdateChildren(database, "/test");
        Map data = Mockito.mock(Map.class);

        // When
        query.withArgs(data).execute();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).updateChildValuesWithCompletionBlock(Mockito.nullable(NSDictionary.class), Mockito.any(FIRDatabaseReference.Block_updateChildValuesWithCompletionBlock.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void run_wrongArguments() throws Exception {
        // Given
        PowerMockito.mockStatic(Database.class);
        Database database = PowerMockito.mock(Database.class);
        PowerMockito.when(database, "dbReference").thenReturn(firDatabaseReference);
        Whitebox.setInternalState(database, "databasePath", "/test");
        QueryUpdateChildren query = new QueryUpdateChildren(database, "/test");
        Map data = Mockito.mock(Map.class);

        // When
        query.withArgs(data, data, data).execute();

        // Then
        Assert.fail();
    }
}