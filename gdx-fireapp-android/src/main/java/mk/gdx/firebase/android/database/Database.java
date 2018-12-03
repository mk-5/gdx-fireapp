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

package mk.gdx.firebase.android.database;

import com.badlogic.gdx.utils.Array;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

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
import mk.gdx.firebase.database.FilteringStateEnsurer;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.database.pojos.Filter;
import mk.gdx.firebase.database.pojos.OrderByClause;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

/**
 * Android Firebase database API implementation.
 * <p>
 *
 * @see DatabaseDistribution
 */
public class Database implements DatabaseDistribution {

    private static final String MISSING_REFERENCE = "Please call GdxFIRDatabase#inReference() first";

    private DatabaseReference databaseReference;
    private String databasePath;
    private final Array<Filter> filters;
    private OrderByClause orderByClause;


    /**
     * Constructor of android database distribution
     */
    public Database() {
        filters = new Array<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnect(final ConnectedListener listener) {
        new ConnectionStatusQuery(this).withArgs(listener).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath) {
        databaseReference = FirebaseDatabase.getInstance().getReference(databasePath);
        this.databasePath = databasePath;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> setValue(final Object value) {
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
    public <T, E extends T> void readValue(final Class<T> dataType, final DataCallback<E> callback) {
        FilteringStateEnsurer.checkFilteringState(filters, orderByClause, dataType);
        new ReadValueQuery(this).with(filters).with(orderByClause).withArgs(dataType, callback).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
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
        databaseReference = databaseReference().push();
        databasePath = databasePath + "/" + databaseReference.getKey();
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
    public Promise<Void> updateChildren(final Map<String, Object> data) {
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
    @SuppressWarnings("unchecked")
    public <T, R extends T> void transaction(final Class<T> dataType, final TransactionCallback<R> transactionCallback, final CompleteCallback completeCallback) {
        new RunTransactionQuery(this).withArgs(dataType, transactionCallback, completeCallback).execute();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistenceEnabled(boolean enabled) {
        FirebaseDatabase.getInstance().setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced) {
        databaseReference().keepSynced(synced);
    }

    /**
     * Simple getter of {@link DatabaseReference} which this {@link Database} instance will be deal with.
     *
     * @return FirebaseSDK Database reference. Every action will be deal with it.
     * @throws DatabaseReferenceNotSetException It is thrown when user forgot to call {@link #inReference(String)}
     */
    DatabaseReference databaseReference() {
        if (databaseReference == null)
            throw new DatabaseReferenceNotSetException(MISSING_REFERENCE);

        return databaseReference;
    }

    /**
     * Reset {@link #databaseReference} and {@link #databasePath} to initial state.
     * After each flow-terminate operation{@link #databaseReference} and {@link #databasePath} should be reset the initial value,
     * it forces the users to call {@link #inReference(String)} before each flow-terminate operation.
     * <p>
     * Flow-terminate operations are: <uL>
     * <li>{@link #setValue(Object)}</li>
     * <li>{@link #setValue(Object)}</li>
     * <li>{@link #readValue(Class, DataCallback)}</li>
     * <li>{@link #onDataChange(Class, DataChangeListener)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #transaction(Class, TransactionCallback, CompleteCallback)}</li>
     * </uL>
     */
    void terminateOperation() {
        databaseReference = null;
        databasePath = null;
        orderByClause = null;
        filters.clear();
    }

    /**
     * Getter for databasePath.
     *
     * @return Database path, may be null
     */
    String getDatabasePath() {
        return databasePath;
    }
}
