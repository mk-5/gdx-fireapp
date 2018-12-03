/*
 * Copyright 2017 mk
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

import java.util.Map;

import bindings.google.firebasedatabase.FIRDatabase;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.FilteringStateEnsurer;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.ios.database.queries.ConnectionStatusQuery;
import mk.gdx.firebase.ios.database.queries.OnDataChangeQuery;
import mk.gdx.firebase.ios.database.queries.ReadValueQuery;
import mk.gdx.firebase.ios.database.queries.RemoveValueQuery;
import mk.gdx.firebase.ios.database.queries.RunTransactionQuery;
import mk.gdx.firebase.ios.database.queries.SetValueQuery;
import mk.gdx.firebase.ios.database.queries.UpdateChildrenQuery;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

/**
 * iOS Firebase database API implementation.
 * <p>
 *
 * @see DatabaseDistribution
 */
public class Database implements DatabaseDistribution {

    private static final String MISSING_REFERENCE = "Please call GdxFIRDatabase#inReference() first";

    FIRDatabaseReference dbReference;
    String databasePath;
    private final Array<Filter> filters;
    private OrderByClause orderByClause;

    public Database() {
        filters = new Array<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnect(ConnectedListener connectedListener) {
        new ConnectionStatusQuery(this).withArgs(connectedListener).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath) {
        dbReference = FIRDatabase.database().referenceWithPath(databasePath);
        this.databasePath = databasePath;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> setValue(Object value) {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new SetValueQuery(Database.this)
                        .withArgs(value)
                        .with(voidFuturePromise)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> void readValue(Class<T> dataType, DataCallback<R> callback) {
        FilteringStateEnsurer.checkFilteringState(filters, orderByClause, dataType);
        new ReadValueQuery(this).with(filters).with(orderByClause).withArgs(dataType, callback).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> void onDataChange(Class<T> dataType, DataChangeListener<R> listener) {
        FilteringStateEnsurer.checkFilteringState(filters, orderByClause, dataType);
        new OnDataChangeQuery(this).with(filters).with(orderByClause).withArgs(dataType, listener).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> DatabaseDistribution filter(FilterType filterType, V... filterArguments) {
        filters.add(new Filter(filterType, filterArguments));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode, String argument) {
        orderByClause = new OrderByClause(orderByMode, argument);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push() {
        dbReference = dbReference().childByAutoId();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> removeValue() {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new RemoveValueQuery(Database.this)
                        .with(voidFuturePromise)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> updateChildren(Map<String, Object> data) {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new UpdateChildrenQuery(Database.this)
                        .withArgs(data)
                        .with(voidFuturePromise)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R extends T> void transaction(Class<T> dataType, TransactionCallback<R> transactionCallback, CompleteCallback completeCallback) {
        new RunTransactionQuery(this).withArgs(dataType, transactionCallback, completeCallback).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistenceEnabled(boolean enabled) {
        FIRDatabase.database().setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced) {
        dbReference().keepSynced(synced);
        terminateOperation();
    }

    /**
     * Simple getter of {@link FIRDatabaseReference} which this {@link Database} instance will be deal with.
     *
     * @return FirebaseSDK Database reference. Every action will be deal with it.
     * @throws DatabaseReferenceNotSetException It is thrown when user forgot to call {@link #inReference(String)}
     */
    FIRDatabaseReference dbReference() {
        if (dbReference == null)
            throw new DatabaseReferenceNotSetException(MISSING_REFERENCE);
        return dbReference;
    }

    /**
     * Reset {@link #dbReference} and {@link #databasePath} to initial state.
     * After each flow-terminate operation{@link #dbReference} and {@link #databasePath} should be reset the initial value,
     * it forces the users to call {@link #inReference(String)} before each flow-terminate operation.
     * <p>
     * Flow-terminate operations are: <uL>
     * <li>{@link #setValue(Object)}</li>
     * <li>{@link #readValue(Class, DataCallback)}</li>
     * <li>{@link #onDataChange(Class, DataChangeListener)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #transaction(Class, TransactionCallback, CompleteCallback)}</li>
     * </uL>
     */
    void terminateOperation() {
        dbReference = null;
        databasePath = null;
        filters.clear();
        orderByClause = null;
    }

}
