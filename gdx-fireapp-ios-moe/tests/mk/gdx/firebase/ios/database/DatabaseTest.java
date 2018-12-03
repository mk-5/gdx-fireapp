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

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.LongArray;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
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
import apple.foundation.NSString;
import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseQuery;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.ios.GdxIOSAppTest;
import mk.gdx.firebase.ios.database.observers.DataObserversManager;
import mk.gdx.firebase.ios.database.observers.FIRDatabaseReferenceCompleteObserver;
import mk.gdx.firebase.ios.database.queries.ConnectionStatusQuery;
import mk.gdx.firebase.ios.database.queries.OnDataChangeQuery;
import mk.gdx.firebase.ios.database.queries.ReadValueQuery;
import mk.gdx.firebase.ios.database.queries.RemoveValueQuery;
import mk.gdx.firebase.ios.database.queries.RunTransactionQuery;
import mk.gdx.firebase.ios.database.queries.SetValueQuery;
import mk.gdx.firebase.ios.database.queries.UpdateChildrenQuery;
import mk.gdx.firebase.ios.helpers.NSDictionaryHelper;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * TODO - find a solutions for NoSuchMethod java.lang.System#arraycopy when using Rule
 */
@PrepareForTest({
        NatJ.class, FIRDatabase.class,
        ConnectionStatusQuery.class,
        SetValueQuery.class, ReadValueQuery.class, OnDataChangeQuery.class,
        RemoveValueQuery.class, UpdateChildrenQuery.class, RunTransactionQuery.class,
        NSString.class, DataProcessor.class, NSDictionary.class, NSMutableDictionary.class, NSDictionaryHelper.class
})
public class DatabaseTest extends GdxIOSAppTest {

//    @Rule
//    public final PowerMockRule powerMockRule = new PowerMockRule();

    private FIRDatabase firDatabase;
    private FIRDatabaseReference firDatabaseReference;

    @Before
    public void setup() {
//        super.setup();
        PowerMockito.mockStatic(NatJ.class);
        PowerMockito.mockStatic(NSString.class);
        PowerMockito.mockStatic(FIRDatabase.class);
        PowerMockito.mockStatic(DataProcessor.class);
        PowerMockito.mockStatic(NSDictionary.class);
        PowerMockito.mockStatic(NSMutableDictionary.class);
        PowerMockito.mockStatic(NSDictionaryHelper.class);
        firDatabase = Mockito.mock(FIRDatabase.class);
        firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        Mockito.when(firDatabase.referenceWithPath(Mockito.anyString())).thenReturn(firDatabaseReference);
        Mockito.when(FIRDatabase.database()).thenReturn(firDatabase);
        Mockito.when(DataProcessor.javaDataToIos(Mockito.any())).thenReturn(Mockito.mock(NSString.class));
    }

    @AfterClass
    public static void afterClass() {
        ((LongArray) Whitebox.getInternalState(ConnectionStatusQuery.class, "handles")).clear();
        ((DataObserversManager) Whitebox.getInternalState(OnDataChangeQuery.class, "observersManager")).removeListenersForPath("/test");
    }

    @Test
    public void onConnect() throws Exception {
        // Given
        Database database = new Database();
        ConnectedListener connectedListener = Mockito.mock(ConnectedListener.class);
        PowerMockito.mockStatic(ConnectionStatusQuery.class);
//        ConnectionStatusQuery query = PowerMockito.spy(new ConnectionStatusQuery(database));
        ConnectionStatusQuery query = PowerMockito.mock(ConnectionStatusQuery.class);
        Mockito.when(query.with(Mockito.any(Array.class))).thenReturn(query);
        Mockito.when(query.with(Mockito.any(OrderByClause.class))).thenReturn(query);
        Mockito.when(query.withArgs()).thenReturn(query);
        PowerMockito.whenNew(ConnectionStatusQuery.class).withAnyArguments().thenReturn(query);

        // When
        database.onConnect(connectedListener);

        // Then
        // Can't verify new because of PowerMockitoRunner and JaCoCo coverage. Rule does not work here because of MOE
//        PowerMockito.verifyNew(ConnectionStatusQuery.class).withArguments(Mockito.any());
//        Mockito.verify(connectionStatusQuery, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).observeEventTypeWithBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlock) Mockito.any());
    }

    @Test
    public void inReference() throws Exception {
        // Given
        Database database = new Database();
        String reference = "/test";

        // When
        database.inReference(reference);

        // Then
        Assert.assertEquals(reference, Whitebox.getInternalState(database, "databasePath"));
        Assert.assertNotNull(Whitebox.getInternalState(database, "dbReference"));
    }

    @Test
    public void setValue() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(SetValueQuery.class);
        SetValueQuery query = PowerMockito.spy(new SetValueQuery(database));
        PowerMockito.whenNew(SetValueQuery.class).withAnyArguments().thenReturn(query);

        // When
        database.inReference("/test").setValue("test_value");

        // Then
//      PowerMockito.verifyNew(SetValueQuery.class).withArguments(Mockito.any());
//      Mockito.verify(query, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).setValueWithCompletionBlock(Mockito.any(), Mockito.any(FIRDatabaseReferenceCompleteObserver.class));
    }

    @Test
    public void readValue() throws Exception {
        // Given
        Database database = new Database();
        DataCallback callback = Mockito.mock(DataCallback.class);
        PowerMockito.mockStatic(ReadValueQuery.class);
        ReadValueQuery query = PowerMockito.spy(new ReadValueQuery(database));
        PowerMockito.whenNew(ReadValueQuery.class).withAnyArguments().thenReturn(query);
        Class dataType = String.class;

        // When
        database.inReference("/test").readValue(dataType, callback);

        // Then
//      PowerMockito.verifyNew(SetValueQuery.class).withArguments(Mockito.any());
//      Mockito.verify(query, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1) Mockito.any(), Mockito.any());
    }

    @Test
    public void onDataChange() throws Exception {
        // Given
        Database database = new Database();
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.spy(new OnDataChangeQuery(database));
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        Class dataType = String.class;

        // When
        database.inReference("/test").onDataChange(dataType, listener);

        // Then
//      PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
//      Mockito.verify(query, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).observeEventTypeWithBlockWithCancelBlock(Mockito.anyLong(), (FIRDatabaseQuery.Block_observeEventTypeWithBlockWithCancelBlock_1) Mockito.any(), Mockito.any());
    }

    @Test
    public void onDataChange_nullArgument() throws Exception {
        // Given
        Database database = new Database();
        long handleValue = 10L;
        DataChangeListener listener = null;
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.spy(new OnDataChangeQuery(database));
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        Class dataType = String.class;
        String databasePath = "/test";
        ((DataObserversManager) Whitebox.getInternalState(OnDataChangeQuery.class, "observersManager")).addNewListener(databasePath, handleValue);

        // When
        database.inReference("/test").onDataChange(dataType, listener);

        // Then
//      PowerMockito.verifyNew(OnDataChangeQuery.class).withArguments(Mockito.any());
//      Mockito.verify(query, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).removeObserverWithHandle(Mockito.eq(handleValue));
    }

    @Test
    public void filter() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.filter(FilterType.EQUAL_TO, 2);
        database.filter(FilterType.LIMIT_FIRST, 3);
        Filter filter1 = (Filter) ((Array) Whitebox.getInternalState(database, "filters")).get(0);
        Filter filter2 = (Filter) ((Array) Whitebox.getInternalState(database, "filters")).get(1);


        // Then
        Assert.assertNotNull(filter1);
        Assert.assertNotNull(filter2);
        Assert.assertEquals(FilterType.EQUAL_TO, filter1.getFilterType());
        Assert.assertEquals(2, filter1.getFilterArguments()[0]);
        Assert.assertEquals(FilterType.LIMIT_FIRST, filter2.getFilterType());
        Assert.assertEquals(3, filter2.getFilterArguments()[0]);
    }

    @Test
    public void orderBy() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.orderBy(OrderByMode.ORDER_BY_KEY, "test_key");
        OrderByClause orderByClause = ((OrderByClause) Whitebox.getInternalState(database, "orderByClause"));

        // Then
        Assert.assertEquals(OrderByMode.ORDER_BY_KEY, orderByClause.getOrderByMode());
        Assert.assertEquals("test_key", orderByClause.getArgument());
    }

    @Test
    public void push() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.inReference("/test").push();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).childByAutoId();
        Mockito.verifyNoMoreInteractions(firDatabaseReference);
    }

    @Test
    public void removeValue1() throws Exception {
        // Given
        Database database = new Database();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        PowerMockito.mockStatic(RemoveValueQuery.class);
        RemoveValueQuery query = PowerMockito.spy(new RemoveValueQuery(database));
        PowerMockito.whenNew(RemoveValueQuery.class).withAnyArguments().thenReturn(query);
        Class dataType = String.class;

        // When
        database.inReference("/test").removeValue();

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).removeValueWithCompletionBlock(Mockito.any());
    }

    @Test
    public void updateChildren1() throws Exception {
        // Given
        Database database = new Database();
        PowerMockito.mockStatic(UpdateChildrenQuery.class);
        UpdateChildrenQuery query = PowerMockito.spy(new UpdateChildrenQuery(database));
        PowerMockito.whenNew(UpdateChildrenQuery.class).withAnyArguments().thenReturn(query);
        Map mapData = Mockito.mock(Map.class);

        // When
        database.inReference("/test").updateChildren(mapData);

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).updateChildValuesWithCompletionBlock(Mockito.nullable(NSDictionary.class), Mockito.any());
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
        Class dataType = String.class;

        // When
        database.inReference("/test").transaction(dataType, transactionCallback, callback);

        // Then
//      PowerMockito.verifyNew(RunTransactionQuery.class).withArguments(Mockito.any());
//      Mockito.verify(query, VerificationModeFactory.times(1)).execute();
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).runTransactionBlockAndCompletionBlock(Mockito.any(), Mockito.any());
    }

    @Test
    public void setPersistenceEnabled() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.setPersistenceEnabled(true);

        // Then
        Mockito.verify(firDatabase, VerificationModeFactory.times(1)).setPersistenceEnabled(Mockito.eq(true));
    }

    @Test
    public void keepSynced() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.inReference("/test").keepSynced(true);

        // Then
        Mockito.verify(firDatabaseReference, VerificationModeFactory.times(1)).keepSynced(Mockito.eq(true));
    }

    @Test
    public void dbReference() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.inReference("/test");

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(database, "dbReference"));
        Assert.assertEquals("/test", Whitebox.getInternalState(database, "databasePath"));
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void dbReference_missing() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.readValue(String.class, Mockito.mock(DataCallback.class));

        // Then
        Assert.fail();
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void dbReference_missing2() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.onDataChange(String.class, Mockito.mock(DataChangeListener.class));

        // Then
        Assert.fail();
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void dbReference_missing3() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.removeValue();

        // Then
        Assert.fail();
    }

    @Test
    public void terminateOperation() throws Exception {
        // Given
        Database database = new Database();

        // When
        database.inReference("/test").filter(FilterType.EQUAL_TO, 2).orderBy(OrderByMode.ORDER_BY_VALUE, null);
        database.terminateOperation();

        // Then
        Assert.assertNull(Whitebox.getInternalState(database, "dbReference"));
        Assert.assertNull(Whitebox.getInternalState(database, "databasePath"));
        Assert.assertNull(Whitebox.getInternalState(database, "orderByClause"));
        Assert.assertEquals(0, ((Array) Whitebox.getInternalState(database, "filters")).size);
    }
}