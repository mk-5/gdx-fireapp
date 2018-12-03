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

package mk.gdx.firebase.html.database;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.html.database.json.JsonDataCallback;
import mk.gdx.firebase.html.database.json.JsonDataModifier;
import mk.gdx.firebase.html.database.queries.ConnectionStatusQuery;
import mk.gdx.firebase.html.database.queries.OnDataChangeQuery;
import mk.gdx.firebase.html.database.queries.PushQuery;
import mk.gdx.firebase.html.database.queries.ReadValueQuery;
import mk.gdx.firebase.html.database.queries.RemoveValueQuery;
import mk.gdx.firebase.html.database.queries.RunTransactionQuery;
import mk.gdx.firebase.html.database.queries.SetValueQuery;
import mk.gdx.firebase.html.database.queries.UpdateChildrenQuery;
import mk.gdx.firebase.html.firebase.ScriptRunner;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({ScriptRunner.class, ConnectionStatusQuery.class,
        SetValueQuery.class, ReadValueQuery.class, OnDataChangeQuery.class, PushQuery.class,
        RemoveValueQuery.class, UpdateChildrenQuery.class, RunTransactionQuery.class, DatabaseReference.class
//        , Database.class
})
public class DatabaseTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private DatabaseReference databaseReference;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(DatabaseReference.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.when(ScriptRunner.class, "firebaseScript", Mockito.any(Runnable.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        });
        databaseReference = PowerMockito.mock(DatabaseReference.class);
        PowerMockito.when(DatabaseReference.of(Mockito.anyString())).thenReturn(databaseReference);
    }

    @Test
    public void onConnect() throws Exception {
        // Given
        Database database = new Database();
        ConnectedListener connectedListener = Mockito.mock(ConnectedListener.class);
        PowerMockito.mockStatic(ConnectionStatusQuery.class);
        ConnectionStatusQuery connectionStatusQuery = PowerMockito.spy(new ConnectionStatusQuery(database));
        PowerMockito.whenNew(ConnectionStatusQuery.class).withAnyArguments().thenReturn(connectionStatusQuery);

        // When
        database.onConnect(connectedListener);

        // Then
//        PowerMockito.verifyNew(ConnectionStatusQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(ConnectionStatusQuery.class);
        ConnectionStatusQuery.onConnect(Mockito.refEq(connectedListener));
    }

    @Test
    public void inReference() {
        // Given
        Database database = new Database();
        String testValue = "test_value";

        // When
        database.inReference(testValue);

        // Then
        Assert.assertEquals(testValue, Whitebox.getInternalState(database, "refPath"));
    }

    @Test
    public void setValue() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(SetValueQuery.class);
        SetValueQuery query = PowerMockito.spy(new SetValueQuery(database));
        PowerMockito.whenNew(SetValueQuery.class).withAnyArguments().thenReturn(query);
        String testValue = "test_value";
        String testReference = "test_reference";

        // When
        database.inReference(testReference)
                .setValue(testValue);

        // Then
        PowerMockito.verifyStatic(SetValueQuery.class);
        SetValueQuery.setWithPromise(Mockito.eq(testReference), Mockito.eq(testValue), Mockito.any(FuturePromise.class));
    }

    @Test
    public void readValue() throws Exception {
        // Given
        Database database = new Database();
        DataCallback callback = Mockito.mock(DataCallback.class);
        PowerMockito.mockStatic(ReadValueQuery.class);
        ReadValueQuery query = PowerMockito.spy(new ReadValueQuery(database));
        PowerMockito.whenNew(ReadValueQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Class dataType = String.class;

        // When
        database.inReference(testReference).readValue(dataType, callback);

        // Then
//        PowerMockito.verifyNew(ReadValueQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(ReadValueQuery.class);
        ReadValueQuery.once(Mockito.any(DatabaseReference.class), Mockito.any(JsonDataCallback.class));
    }

    @Test
    public void onDataChange() throws Exception {
        // Given
        Database database = new Database();
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.spy(new OnDataChangeQuery(database));
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Class dataType = String.class;

        // When
        database.inReference(testReference).onDataChange(dataType, listener);

        // Then
//        PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(OnDataChangeQuery.class);
        OnDataChangeQuery.onValue(Mockito.eq(testReference), Mockito.any(DatabaseReference.class));
    }

    @Test
    public void onDataChange_detach() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.spy(new OnDataChangeQuery(database));
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Class dataType = String.class;

        // When
        database.inReference(testReference).onDataChange(dataType, null);

        // Then
//        PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(OnDataChangeQuery.class);
        OnDataChangeQuery.offValue(Mockito.eq(testReference));
    }

    @Test
    public void push() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(PushQuery.class);
        PushQuery query = PowerMockito.spy(new PushQuery(database));
        PowerMockito.whenNew(PushQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";

        // When
        database.inReference(testReference).push();

        // Then
//        PowerMockito.verifyNew(PushQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(PushQuery.class);
        PushQuery.push(Mockito.eq(testReference));
    }

    @Test
    public void removeValue() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(RemoveValueQuery.class);
        RemoveValueQuery query = PowerMockito.spy(new RemoveValueQuery(database));
        PowerMockito.whenNew(RemoveValueQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";

        // When
        database.inReference(testReference).removeValue();

        // Then
        PowerMockito.verifyStatic(RemoveValueQuery.class);
        RemoveValueQuery.removeWithPromise(Mockito.eq(testReference), Mockito.any(FuturePromise.class));
    }

    @Test
    public void updateChildren() throws Exception {
        // Given
        Database database = new Database();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        PowerMockito.mockStatic(UpdateChildrenQuery.class);
        UpdateChildrenQuery query = PowerMockito.spy(new UpdateChildrenQuery(database));
        PowerMockito.whenNew(UpdateChildrenQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Map data = Mockito.mock(Map.class);

        // When
        database.inReference(testReference).updateChildren(data);

        // Then
        PowerMockito.verifyStatic(UpdateChildrenQuery.class);
        UpdateChildrenQuery.updateWithPromise(Mockito.eq(testReference), Mockito.anyString(), Mockito.any(FuturePromise.class));
    }

    @Test
    public void transaction() throws Exception {
        // Given
        Database database = new Database();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        PowerMockito.mockStatic(RunTransactionQuery.class);
        RunTransactionQuery query = PowerMockito.spy(new RunTransactionQuery(database));
        PowerMockito.whenNew(RunTransactionQuery.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Class dataType = Long.class;

        // When
        database.inReference(testReference).transaction(dataType, transactionCallback, callback);

        // Then
//        PowerMockito.verifyNew(RunTransactionQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(RunTransactionQuery.class);
        RunTransactionQuery.transaction(Mockito.eq(testReference), Mockito.any(JsonDataModifier.class), Mockito.refEq(callback));
    }

    @Test
    public void setPersistenceEnabled() {
        // Given
        Database database = new Database();
        Application application = Mockito.mock(Application.class);
        Gdx.app = application;

        // When
        database.setPersistenceEnabled(true);

        // Then
        Mockito.verify(application).log(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void keepSynced() {
        // Given
        Database database = new Database();
        Application application = Mockito.mock(Application.class);
        Gdx.app = application;

        // When
        database.keepSynced(true);

        // Then
        Mockito.verify(application).log(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void databaseReference() {
        // Given
        Database database = new Database();
        String testReference = "/test";

        // When
        database.inReference(testReference);

        // Then
        Assert.assertEquals(testReference, Whitebox.getInternalState(database, "refPath"));
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet1() {
        // Given
        Database database = new Database();
        // When
        database.setValue("test");
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet2() {
        // Given
        Database database = new Database();
        // When
        database.updateChildren(null);
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet3() {
        // Given
        Database database = new Database();
        // When
        database.onDataChange(String.class, Mockito.mock(DataChangeListener.class));
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet4() {
        // Given
        Database database = new Database();
        // When
        database.readValue(String.class, Mockito.mock(DataCallback.class));
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet5() {
        // Given
        Database database = new Database();
        // When
        database.transaction(String.class, Mockito.mock(TransactionCallback.class), Mockito.mock(CompleteCallback.class));
    }

    @Test
    public void terminateOperation() {
        // Given
        Database database = new Database();
        database.inReference("test");

        // When
        database.terminateOperation();

        // Then
        Assert.assertNull(Whitebox.getInternalState(database, "refPath"));
    }
}