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
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

@PrepareForTest({
        GdxNativesLoader.class, FirebaseDatabase.class,
        QueryOnDataChange.class, Database.class, QueryConnectionStatus.class,
        QueryUpdateChildren.class, QueryReadValue.class, QueryRemoveValue.class,
        QuerySetValue.class, QueryRunTransaction.class

})
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
        PowerMockito.mockStatic(QueryOnDataChange.class);
        QueryOnDataChange query = PowerMockito.mock(QueryOnDataChange.class);
        PowerMockito.whenNew(QueryOnDataChange.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(Array.class))).thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(OrderByClause.class))).thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any(), Mockito.any())).thenReturn(query);
        Database database = new Database();

        // When
        database.inReference("/test").onDataChange(Map.class, null);

        // Then
        PowerMockito.verifyNew(QueryOnDataChange.class);
    }

    @Test
    public void onConnect() throws Exception {
        // Given
        PowerMockito.mockStatic(QueryConnectionStatus.class);
        Database database = new Database();
        QueryConnectionStatus query = Mockito.spy(new QueryConnectionStatus(database));
        PowerMockito.whenNew(QueryConnectionStatus.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        ConnectedListener listener = Mockito.mock(ConnectedListener.class);

        // When
        database.onConnect(listener);

        // Then
        PowerMockito.verifyNew(QueryConnectionStatus.class);
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
        PowerMockito.mockStatic(QuerySetValue.class);
        Database database = new Database();
        QuerySetValue query = PowerMockito.spy(new QuerySetValue(database));
        PowerMockito.whenNew(QuerySetValue.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);

        // When
        Promise promise = Mockito.spy(database.inReference("/test").setValue(""));

        // Then
        PowerMockito.verifyNew(QuerySetValue.class);
    }

    @Test
    public void readValue() throws Exception {
        // Given
        PowerMockito.mockStatic(QueryReadValue.class);
        Database database = new Database();
        QueryReadValue query = Mockito.spy(new QueryReadValue(database));
        PowerMockito.whenNew(QueryReadValue.class).withAnyArguments().thenReturn(query);

        // When
        ((FuturePromise) database.inReference("/test").readValue(String.class)).doComplete(null);

        // Then
        PowerMockito.verifyNew(QueryReadValue.class);
        // TODO - verify callback
    }

    @Test
    public void onDataChange() throws Exception {
        // Given
        PowerMockito.mockStatic(QueryOnDataChange.class);
        QueryOnDataChange query = PowerMockito.mock(QueryOnDataChange.class);
        DataChangeListener dataChangeListener = Mockito.mock(DataChangeListener.class);
        PowerMockito.whenNew(QueryOnDataChange.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(Array.class))).thenReturn(query);
        Mockito.when(query.with(Mockito.nullable(OrderByClause.class))).thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any(), Mockito.any())).thenReturn(query);
        Database database = new Database();

        // When
        database.inReference("/test").onDataChange(Map.class, dataChangeListener);

        // Then
        PowerMockito.verifyNew(QueryOnDataChange.class);
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
        PowerMockito.mockStatic(QueryRemoveValue.class);
        Database database = new Database();
        QueryRemoveValue query = PowerMockito.mock(QueryRemoveValue.class);
        PowerMockito.whenNew(QueryRemoveValue.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);

        // When
        Promise promise = Mockito.spy(database.inReference("/test").removeValue());

        // Then
        PowerMockito.verifyNew(QueryRemoveValue.class);
    }

    @Test
    public void updateChildren() throws Exception {
        // Given
        PowerMockito.mockStatic(QueryUpdateChildren.class);
        Database database = new Database();
        QueryUpdateChildren query = PowerMockito.spy(new QueryUpdateChildren(database));
        PowerMockito.whenNew(QueryUpdateChildren.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        Map data = Mockito.mock(Map.class);

        // When
        database.inReference("/test").updateChildren(data);

        // Then
        PowerMockito.verifyNew(QueryUpdateChildren.class);
    }

    @Test
    public void transaction() throws Exception {
        // Given
        PowerMockito.mockStatic(QueryRunTransaction.class);
        Database database = new Database();
        QueryRunTransaction query = PowerMockito.mock(QueryRunTransaction.class);
        PowerMockito.whenNew(QueryRunTransaction.class).withAnyArguments().thenReturn(query);
        Mockito.when(query.withArgs(Mockito.any())).thenReturn(query);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        Class dataType = String.class;

        // When
        database.inReference("/test").transaction(dataType, transactionCallback, callback);

        // Then
        PowerMockito.verifyNew(QueryRunTransaction.class);
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