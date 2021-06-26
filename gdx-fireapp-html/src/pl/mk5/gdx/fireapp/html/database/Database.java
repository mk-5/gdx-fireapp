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

package pl.mk5.gdx.fireapp.html.database;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.database.DatabaseConsumer;
import pl.mk5.gdx.fireapp.database.Filter;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.database.MapConverter;
import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.database.QueryProvider;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.exceptions.DatabaseReferenceNotSetException;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.promises.ConverterPromise;
import pl.mk5.gdx.fireapp.promises.FutureListenerPromise;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * GWT Firebase API implementation.
 *
 * @see DatabaseDistribution
 */
public class Database implements DatabaseDistribution, QueryProvider {

    private String refPath;
    private final Array<Filter> filters;
    private OrderByClause orderByClause;

    public Database(String dbUrl) {
        throw new UnsupportedOperationException("Multiple databases are not support in GWT version yet.");
    }

    public Database() {
        filters = new Array<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListenerPromise<ConnectionStatus> onConnect() {
        return FutureListenerPromise.whenListener(new Consumer<FutureListenerPromise<ConnectionStatus>>() {
            @Override
            @SuppressWarnings("unchecked")
            public void accept(FutureListenerPromise<ConnectionStatus> futurePromise) {
                new QueryConnectionStatus(Database.this)
                        .with(futurePromise)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath) {
        refPath = databasePath;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> setValue(final Object value) {
        checkDatabaseReference();
        return FuturePromise.when(new DatabaseConsumer<FuturePromise<Void>>(this) {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new QuerySetValue(Database.this, getDatabasePath())
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
    public <T, R extends T> Promise<R> readValue(final Class<T> dataType) {
        checkDatabaseReference();
        return ConverterPromise.whenWithConvert(new DatabaseConsumer<ConverterPromise<T, R>>(this) {
            @Override
            @SuppressWarnings("unchecked")
            public void accept(ConverterPromise<T, R> rConverterPromise) {
                // TODO - check
                rConverterPromise.with(GdxFIRDatabase.instance().getMapConverter(), dataType);
                new QueryReadValue(Database.this, getDatabasePath())
                        .with(getFilters())
                        .with(getOrderByClause())
                        .with(rConverterPromise)
                        .withArgs(dataType)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> ListenerPromise<R> onDataChange(final Class<T> dataType) {
        checkDatabaseReference();
        return ConverterPromise.whenWithConvert(new DatabaseConsumer<ConverterPromise<T, R>>(this) {
            @Override
            public void accept(ConverterPromise<T, R> rConverterPromise) {
                rConverterPromise.with(GdxFIRDatabase.instance().getMapConverter(), dataType);
                new QueryOnDataChange(Database.this, getDatabasePath())
                        .with(getFilters())
                        .with(getOrderByClause())
                        .with(rConverterPromise)
                        .withArgs(dataType)
                        .execute();
            }
        });
    }

    @Override
    public <T, R extends T> ListenerPromise<R> onChildChange(final Class<T> dataType, final ChildEventType... eventsType) {
        checkDatabaseReference();
        return ConverterPromise.whenWithConvert(new DatabaseConsumer<ConverterPromise<T, R>>(this) {
            @Override
            public void accept(ConverterPromise<T, R> rConverterPromise) {
                rConverterPromise.with(GdxFIRDatabase.instance().getMapConverter(), dataType);
                new QueryOnChildChange(Database.this, getDatabasePath())
                        .with(getFilters())
                        .with(getOrderByClause())
                        .with(rConverterPromise)
                        .withArgs(dataType, eventsType)
                        .execute();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> DatabaseDistribution filter(FilterType filterType, V... filterArguments) {
        checkDatabaseReference();
        filters.add(new Filter(filterType, filterArguments));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode, String argument) {
        checkDatabaseReference();
        orderByClause = new OrderByClause(orderByMode, argument);
        return this;
    }

    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode) {
        return orderBy(orderByMode, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push() {
        new QueryPush(this, databaseReference()).execute();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> removeValue() {
        return FuturePromise.when(new DatabaseConsumer<FuturePromise<Void>>(this) {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new QueryRemoveValue(Database.this, getDatabasePath())
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
        checkDatabaseReference();
        return FuturePromise.when(new DatabaseConsumer<FuturePromise<Void>>(this) {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new QueryUpdateChildren(Database.this, getDatabasePath())
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
    public <T, R extends T> Promise<Void> transaction(final Class<T> dataType, final Function<R, R> transactionFunction) {
        checkDatabaseReference();
        return FuturePromise.when(new DatabaseConsumer<FuturePromise<Void>>(this) {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                new QueryRunTransaction(Database.this, getDatabasePath())
                        .withArgs(dataType, transactionFunction)
                        .with(voidFuturePromise)
                        .execute();
            }
        });
    }

    /**
     * Firebase web api does not support this feature.
     * <p>
     *
     * @param enabled If true persistence will be enabled.
     */
    @Override
    public void setPersistenceEnabled(boolean enabled) {
        Gdx.app.log("GdxFireapp", "No such feature on firebase web platform.");
    }

    /**
     * Firebase web api does not support this feature.
     *
     * @param synced If true sync for specified database path will be enabled
     */
    @Override
    public void keepSynced(boolean synced) {
        Gdx.app.log("GdxFireapp", "No such feature on firebase web platform.");
    }

    /**
     * Gets firebase database path from local var or throw exception when it is null.
     *
     * @return Database reference path. Every action will be deal with it.
     * @throws DatabaseReferenceNotSetException It is thrown when user forgot to call {@link #inReference(String)}
     */
    String databaseReference() {
        checkDatabaseReference();
        return refPath;
    }

    @Override
    public String getReferencePath() {
        return refPath;
    }

    @Override
    public Array<Filter> getFilters() {
        return filters;
    }

    @Override
    public OrderByClause getOrderByClause() {
        return orderByClause;
    }

    /**
     * Reset {@link #refPath} to initial state.
     * <p>
     * After each flow-terminate operation {@link #refPath} should be reset the initial value,
     * it forces the users to call {@link #inReference(String)} after each flow-terminate operation.
     * <p>
     * Flow-terminate operations are: <uL>
     * <li>{@link #setValue(Object)}</li>
     * <li>{@link #readValue(Class)}</li>
     * <li>{@link #onDataChange(Class)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #transaction(Class, Function)}</li>
     * </uL>
     */
    public void terminateOperation() {
        refPath = null;
        filters.clear();
        orderByClause = null;
    }

    private void checkDatabaseReference() {
        if (refPath == null)
            throw new DatabaseReferenceNotSetException("Please call GdxFIRDatabase#inReference() first.");
    }

}
