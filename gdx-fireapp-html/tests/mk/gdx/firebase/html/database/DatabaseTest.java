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
import com.badlogic.gdx.utils.reflect.ClassReflection;

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
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.html.firebase.ScriptRunner;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.promises.ConverterPromise;
import mk.gdx.firebase.promises.FutureListenerPromise;
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({ScriptRunner.class, QueryConnectionStatus.class,
        QuerySetValue.class, QueryReadValue.class, QueryOnDataChange.class, QueryPush.class,
        QueryRemoveValue.class, QueryUpdateChildren.class, QueryRunTransaction.class, DatabaseReference.class,
        ClassReflection.class, GwtDataPromisesManager.class
//        , Database.class
})
public class DatabaseTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private DatabaseReference databaseReference;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(ClassReflection.class);
        PowerMockito.mockStatic(DatabaseReference.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.mockStatic(GwtDataPromisesManager.class);
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
        PowerMockito.mockStatic(QueryConnectionStatus.class);
        QueryConnectionStatus queryConnectionStatus = PowerMockito.spy(new QueryConnectionStatus(database));
        PowerMockito.whenNew(QueryConnectionStatus.class).withAnyArguments().thenReturn(queryConnectionStatus);

        // When
        database.onConnect();

        // Then
//        PowerMockito.verifyNew(ConnectionStatusQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryConnectionStatus.class);
        QueryConnectionStatus.onConnect(Mockito.any(FuturePromise.class));
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
        PowerMockito.mockStatic(QuerySetValue.class);
        QuerySetValue query = PowerMockito.spy(new QuerySetValue(database));
        PowerMockito.whenNew(QuerySetValue.class).withAnyArguments().thenReturn(query);
        String testValue = "test_value";
        String testReference = "test_reference";

        // When
        database.inReference(testReference)
                .setValue(testValue);

        // Then
        PowerMockito.verifyStatic(QuerySetValue.class);
        QuerySetValue.setWithPromise(Mockito.eq(testReference), Mockito.eq(testValue), Mockito.any(FuturePromise.class));
    }

    @Test
    public void readValue() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(QueryReadValue.class);
        QueryReadValue query = PowerMockito.spy(new QueryReadValue(database));
        PowerMockito.whenNew(QueryReadValue.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Gdx.app = Mockito.mock(Application.class);
        Class dataType = String.class;


        // When
        ((FuturePromise) database.inReference(testReference).readValue(dataType)).doComplete(testReference);

        // Then
//        PowerMockito.verifyNew(ReadValueQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryReadValue.class);
        QueryReadValue.once(Mockito.any(DatabaseReference.class), Mockito.any(ConverterPromise.class));
    }

    @Test
    public void onDataChange() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(QueryOnDataChange.class);
        QueryOnDataChange query = PowerMockito.spy(new QueryOnDataChange(database));
        PowerMockito.whenNew(QueryOnDataChange.class).withAnyArguments().thenReturn(query);
        Gdx.app = Mockito.mock(Application.class);
        String testReference = "test_reference";
        PowerMockito.doReturn(false).when(GwtDataPromisesManager.class, "hasPromise", testReference);
        Class dataType = String.class;

        // When
        ((FuturePromise) database.inReference(testReference).onDataChange(dataType)).doComplete(testReference);

        // Then
//        PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryOnDataChange.class);
        QueryOnDataChange.onValue(Mockito.eq(testReference), Mockito.any(DatabaseReference.class));
    }

    @Test
    public void onDataChange_detach() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(QueryOnDataChange.class);
        QueryOnDataChange query = PowerMockito.spy(new QueryOnDataChange(database));
        PowerMockito.whenNew(QueryOnDataChange.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        PowerMockito.doReturn(false).when(GwtDataPromisesManager.class, "hasPromise", testReference);
        GwtDataPromisesManager.removeDataPromise(testReference);
        Gdx.app = Mockito.mock(Application.class);

        // When
        FutureListenerPromise promise = ((FutureListenerPromise) database.inReference(testReference).onDataChange(String.class));
        promise.doComplete(testReference);
        PowerMockito.doReturn(true).when(GwtDataPromisesManager.class, "hasPromise", testReference);
        promise.cancel();

        // Then
//        PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryOnDataChange.class);
        QueryOnDataChange.offValue(Mockito.eq(testReference));
    }

    @Test
    public void push() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(QueryPush.class);
        QueryPush query = PowerMockito.spy(new QueryPush(database));
        PowerMockito.whenNew(QueryPush.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";

        // When
        database.inReference(testReference).push();

        // Then
//        PowerMockito.verifyNew(PushQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryPush.class);
        QueryPush.push(Mockito.eq(testReference));
    }

    @Test
    public void removeValue() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(QueryRemoveValue.class);
        QueryRemoveValue query = PowerMockito.spy(new QueryRemoveValue(database));
        PowerMockito.whenNew(QueryRemoveValue.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";

        // When
        database.inReference(testReference).removeValue();

        // Then
        PowerMockito.verifyStatic(QueryRemoveValue.class);
        QueryRemoveValue.removeWithPromise(Mockito.eq(testReference), Mockito.any(FuturePromise.class));
    }

    @Test
    public void updateChildren() throws Exception {
        // Given
        Database database = new Database();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        PowerMockito.mockStatic(QueryUpdateChildren.class);
        QueryUpdateChildren query = PowerMockito.spy(new QueryUpdateChildren(database));
        PowerMockito.whenNew(QueryUpdateChildren.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Map data = Mockito.mock(Map.class);

        // When
        database.inReference(testReference).updateChildren(data);

        // Then
        PowerMockito.verifyStatic(QueryUpdateChildren.class);
        QueryUpdateChildren.updateWithPromise(Mockito.eq(testReference), Mockito.anyString(), Mockito.any(FuturePromise.class));
    }

    @Test
    public void transaction() throws Exception {
        // Given
        Database database = new Database();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        PowerMockito.mockStatic(QueryRunTransaction.class);
        QueryRunTransaction query = PowerMockito.spy(new QueryRunTransaction(database));
        PowerMockito.whenNew(QueryRunTransaction.class).withAnyArguments().thenReturn(query);
        String testReference = "test_reference";
        Class dataType = Long.class;

        // When
        database.inReference(testReference).transaction(dataType, transactionCallback, callback);

        // Then
//        PowerMockito.verifyNew(RunTransactionQuery.class).withArguments(Mockito.any());
        PowerMockito.verifyStatic(QueryRunTransaction.class);
        QueryRunTransaction.transaction(Mockito.eq(testReference), Mockito.any(JsonDataModifier.class), Mockito.refEq(callback));
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
        database.onDataChange(String.class);
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference_notSet4() {
        // Given
        Database database = new Database();
        // When
        database.readValue(String.class);
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