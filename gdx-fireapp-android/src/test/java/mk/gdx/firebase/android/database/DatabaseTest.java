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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import java.util.Map;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.android.database.queries.ConnectionStatusQuery;
import mk.gdx.firebase.android.database.queries.OnDataChangeQuery;
import mk.gdx.firebase.android.database.queries.ReadValueQuery;
import mk.gdx.firebase.android.database.queries.RemoveValueQuery;
import mk.gdx.firebase.android.database.queries.RunTransactionQuery;
import mk.gdx.firebase.android.database.queries.SetValueQuery;
import mk.gdx.firebase.android.database.queries.UpdateChildrenQuery;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.promises.Promise;

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

    @Test
    public void onConnect() throws Exception {
        // Given
        PowerMockito.mockStatic(ConnectionStatusQuery.class);
        Database database = new Database();
        ConnectionStatusQuery query = Mockito.spy(new ConnectionStatusQuery(database));
        PowerMockito.whenNew(ConnectionStatusQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        ConnectedListener listener = Mockito.mock(ConnectedListener.class);

        // When
        database.onConnect(listener);

        // Then
        PowerMockito.verifyNew(ConnectionStatusQuery.class);
    }

    @Test
    public void inReference() {
        // Given
        Database database = Mockito.spy(new Database());

        // When
        database.inReference("test");
        DatabaseReference reference = Whitebox.getInternalState(database, "databaseReference");
        String path = Whitebox.getInternalState(database, "databasePath");

        // Then
        Assert.assertEquals("test", path);
        Assert.assertNotNull(reference);
    }

    @Test
    public void setValue() throws Exception {
        // Given
        PowerMockito.mockStatic(SetValueQuery.class);
        Database database = new Database();
        SetValueQuery query = PowerMockito.spy(new SetValueQuery(database));
        PowerMockito.whenNew(SetValueQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);

        // When
        Promise promise = Mockito.spy(database.inReference("/test").setValue(""));

        // Then
        PowerMockito.verifyNew(SetValueQuery.class);
    }

    @Test
    public void readValue() throws Exception {
        // Given
        PowerMockito.mockStatic(SetValueQuery.class);
        Database database = new Database();
        ReadValueQuery query = Mockito.spy(new ReadValueQuery(database));
        DataCallback dataCallback = Mockito.mock(DataCallback.class);
        PowerMockito.whenNew(ReadValueQuery.class).withAnyArguments().thenReturn(query);

        // When
        database.inReference("/test").readValue(String.class, dataCallback);

        // Then
        PowerMockito.verifyNew(ReadValueQuery.class);
        // TODO - verify callback
    }

    @Test
    public void onDataChange() throws Exception {
        // Given
        PowerMockito.mockStatic(OnDataChangeQuery.class);
        OnDataChangeQuery query = PowerMockito.mock(OnDataChangeQuery.class);
        DataChangeListener dataChangeListener = Mockito.mock(DataChangeListener.class);
        PowerMockito.whenNew(OnDataChangeQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(Array.class))).thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(OrderByClause.class))).thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any(), Mockito.any())).thenReturn(query);
        Database database = new Database();

        // When
        database.inReference("/test").onDataChange(Map.class, dataChangeListener);

        // Then
        PowerMockito.verifyNew(OnDataChangeQuery.class);
    }

    @Test
    public void filter() {
        // Given
        Database database = new Database();

        // When
        database.filter(FilterType.LIMIT_FIRST, 2)
                .filter(FilterType.EQUAL_TO, 3);

        // Then
        Assert.assertEquals(FilterType.LIMIT_FIRST, ((Array<Filter>) Whitebox.getInternalState(database, "filters")).get(0).getFilterType());
        Assert.assertEquals(FilterType.EQUAL_TO, ((Array<Filter>) Whitebox.getInternalState(database, "filters")).get(1).getFilterType());
    }

    @Test
    public void orderBy() {
        // Given
        Database database = new Database();

        // When
        database.orderBy(OrderByMode.ORDER_BY_KEY, "test");

        // Then
        Assert.assertEquals(OrderByMode.ORDER_BY_KEY, ((OrderByClause) Whitebox.getInternalState(database, "orderByClause")).getOrderByMode());
        Assert.assertEquals("test", ((OrderByClause) Whitebox.getInternalState(database, "orderByClause")).getArgument());
    }

    @Test
    public void push() {
        // Given
        Database database = new Database();
        Mockito.when(databaseReference.push()).thenReturn(databaseReference);

        // When
        database.inReference("/test").push();

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).push();
    }

    @Test
    public void removeValue() throws Exception {
        // Given
        PowerMockito.mockStatic(RemoveValueQuery.class);
        Database database = new Database();
        RemoveValueQuery query = PowerMockito.mock(RemoveValueQuery.class);
        PowerMockito.whenNew(RemoveValueQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);

        // When
        Promise promise = Mockito.spy(database.inReference("/test").removeValue());

        // Then
        PowerMockito.verifyNew(RemoveValueQuery.class);
    }

    @Test
    public void updateChildren() throws Exception {
        // Given
        PowerMockito.mockStatic(UpdateChildrenQuery.class);
        Database database = new Database();
        UpdateChildrenQuery query = PowerMockito.spy(new UpdateChildrenQuery(database));
        PowerMockito.whenNew(UpdateChildrenQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        Map data = Mockito.mock(Map.class);

        // When
        database.inReference("/test").updateChildren(data);

        // Then
        PowerMockito.verifyNew(UpdateChildrenQuery.class);
    }

    @Test
    public void transaction() throws Exception {
        // Given
        PowerMockito.mockStatic(RunTransactionQuery.class);
        Database database = new Database();
        RunTransactionQuery query = PowerMockito.mock(RunTransactionQuery.class);
        PowerMockito.whenNew(RunTransactionQuery.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        Class dataType = String.class;

        // When
        database.inReference("/test").transaction(dataType, transactionCallback, callback);

        // Then
        PowerMockito.verifyNew(RunTransactionQuery.class);
    }

    @Test
    public void setPersistenceEnabled() {
        // Given
        Database database = new Database();

        // When
        database.setPersistenceEnabled(true);

        // Then
        Mockito.verify(firebaseDatabase, VerificationModeFactory.times(1)).setPersistenceEnabled(Mockito.eq(true));
    }

    @Test
    public void keepSynced() {
        // Given
        Database database = new Database();

        // When
        database.inReference("/test").keepSynced(true);

        // Then
        Mockito.verify(databaseReference, VerificationModeFactory.times(1)).keepSynced(Mockito.eq(true));
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference() {
        // Given
        Database database = new Database();

        // When
        database.keepSynced(true);

        // Then
        Assert.fail();
    }

    @Test(expected = DatabaseReferenceNotSetException.class)
    public void databaseReference2() {
        // Given
        Database database = new Database();

        // When
        database.setValue("test");

        // Then
        Assert.fail();
    }

    @Test
    public void terminateOperation() {
        // Given
        Database database = new Database();
        database.inReference("test").filter(FilterType.LIMIT_FIRST, 2).orderBy(OrderByMode.ORDER_BY_KEY, "test");

        // When
        database.terminateOperation();

        // Then
        Assert.assertNull(Whitebox.getInternalState(database, "databaseReference"));
        Assert.assertNull(Whitebox.getInternalState(database, "databasePath"));
        Assert.assertNull(Whitebox.getInternalState(database, "orderByClause"));
        Assert.assertEquals(0, ((Array) Whitebox.getInternalState(database, "filters")).size);
    }

}