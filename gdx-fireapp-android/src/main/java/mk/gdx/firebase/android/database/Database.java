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
import com.badlogic.gdx.utils.ObjectMap;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.android.database.proxies.DatabaseReferenceFiltersProvider;
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
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Android Firebase database API implementation.
 * <p>
 *
 * @see DatabaseDistribution
 */
public class Database implements DatabaseDistribution
{

    private static final String TRANSACTION_NULL_VALUE_RETRIEVED = "Null value retrieved from database for transaction - aborting";
    private static final String TRANSACTION_NOT_ABLE_TO_UPDATE = "\"The database value at given path was not be able to update";
    private static final String TRANSACTION_ERROR = "Null value retrieved from database for transaction - aborting";
    private static final String MISSING_REFERENCE = "Please call GdxFIRDatabase#inReference() first";
    private static final String CONNECTION_LISTENER_CANCELED = "Connection listener was canceled";

    private DatabaseReference databaseReference;
    private String databasePath;
    private ObjectMap<String, Array<ValueEventListener>> valueEventListeners;
    private ConnectedListener connectedListener;
    private ConnectionValueListener connectionValueListener;
    private Array<Filter> filters;
    private OrderByClause orderByClause;

    /**
     * Constructor of android database distribution
     */
    public Database()
    {
        valueEventListeners = new ObjectMap<>();
        filters = new Array<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnect(final ConnectedListener listener)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(".info/connected");
        if (connectionValueListener == null && listener != null) {
            connectionValueListener = new ConnectionValueListener();
            ref.addValueEventListener(connectionValueListener);
        } else if (connectionValueListener != null && listener == null) {
            ref.removeEventListener(connectionValueListener);
            connectionValueListener = null;
        }
        connectedListener = listener;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath)
    {
        databaseReference = FirebaseDatabase.getInstance().getReference(databasePath);
        this.databasePath = databasePath;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value)
    {
        databaseReference().setValue(value);
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value, final CompleteCallback completeCallback)
    {
        databaseReference().setValue(value, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                if (databaseError != null) {
                    completeCallback.onError(databaseError.toException());
                } else {
                    completeCallback.onSuccess();
                }
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, E extends T> void readValue(final Class<T> dataType, final DataCallback<E> callback)
    {
        FilteringStateEnsurer.checkFilteringState(filters, orderByClause, dataType);
        new DatabaseReferenceFiltersProvider(filters, databaseReference()).with(orderByClause)
                .addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        DataCallbackOnDataResolver.resolve(dataType, orderByClause, dataSnapshot, callback);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        callback.onError(databaseError.toException());
                    }
                });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R extends T> void onDataChange(Class<T> dataType, DataChangeListener<R> listener)
    {
        FilteringStateEnsurer.checkFilteringState(filters, orderByClause, dataType);
        if (listener != null) {
            DataChangeValueListener<T, R> dataChangeListener = new DataChangeValueListener<>(dataType, listener, orderByClause);
            if (!valueEventListeners.containsKey(databasePath))
                valueEventListeners.put(databasePath, new Array<ValueEventListener>());
            valueEventListeners.get(databasePath).add(dataChangeListener);
            new DatabaseReferenceFiltersProvider(filters, databaseReference()).with(orderByClause).addValueEventListener(dataChangeListener);
        } else {
            Array<ValueEventListener> listeners = valueEventListeners.get(databasePath);
            for (ValueEventListener v : listeners) {
                databaseReference().removeEventListener(v);
            }
        }
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <V> DatabaseDistribution filter(FilterType filterType, V... filterArguments)
    {
        filters.add(new Filter(filterType, filterArguments));
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode, String argument)
    {
        orderByClause = new OrderByClause(orderByMode, argument);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push()
    {
        databaseReference = databaseReference().push();
        databasePath = databasePath + "/" + databaseReference.getKey();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue()
    {
        databaseReference().removeValue();
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue(final CompleteCallback completeCallback)
    {
        databaseReference().removeValue(new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                if (databaseError != null) {
                    completeCallback.onError(databaseError.toException());
                } else {
                    completeCallback.onSuccess();
                }
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChildren(Map<String, Object> data)
    {
        databaseReference().updateChildren(data);
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChildren(Map<String, Object> data, final CompleteCallback completeCallback)
    {
        databaseReference().updateChildren(data, new DatabaseReference.CompletionListener()
        {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference)
            {
                if (databaseError != null) {
                    completeCallback.onError(databaseError.toException());
                } else {
                    completeCallback.onSuccess();
                }
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> void transaction(final Class<T> dataType, final TransactionCallback<R> transactionCallback, final CompleteCallback completeCallback)
    {
        databaseReference().runTransaction(new Transaction.Handler()
        {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData)
            {
                if (mutableData.getValue() == null) {
                    GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
                    return Transaction.abort();
                }
                R transactionData = (R) mutableData.getValue();
                mutableData.setValue(transactionCallback.run(transactionData));
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot)
            {
                if (databaseError != null) {
                    if (completeCallback != null) {
                        completeCallback.onError(databaseError.toException());
                    } else {
                        GdxFIRLogger.error(TRANSACTION_ERROR, databaseError.toException());
                    }
                } else {
                    if (b) {
                        if (completeCallback != null) {
                            completeCallback.onSuccess();
                        }
                    } else {
                        if (completeCallback != null) {
                            completeCallback.onError(new Exception(TRANSACTION_NOT_ABLE_TO_UPDATE));
                        } else {
                            GdxFIRLogger.error(TRANSACTION_NOT_ABLE_TO_UPDATE);
                        }
                    }
                }
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistenceEnabled(boolean enabled)
    {
        FirebaseDatabase.getInstance().setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced)
    {
        databaseReference().keepSynced(synced);
    }

    /**
     * Simple getter of {@link DatabaseReference} which this {@link Database} instance will be deal with.
     *
     * @return FirebaseSDK Database reference. Every action will be deal with it.
     * @throws DatabaseReferenceNotSetException It is thrown when user forgot to call {@link #inReference(String)}
     */
    private DatabaseReference databaseReference()
    {
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
     * <li>{@link #setValue(Object, CompleteCallback)}</li>
     * <li>{@link #readValue(Class, DataCallback)}</li>
     * <li>{@link #onDataChange(Class, DataChangeListener)}</li>
     * <li>{@link #updateChildren(Map)}</li>
     * <li>{@link #updateChildren(Map, CompleteCallback)}</li>
     * <li>{@link #transaction(Class, TransactionCallback, CompleteCallback)}</li>
     * </uL>
     */
    private void terminateOperation()
    {
        databaseReference = null;
        databasePath = null;
        orderByClause = null;
        filters.clear();
    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     *
     * @param <T> Class of object that we want to listen for change
     * @param <R> Generic type of object that we want to listen for change. For ex. in case of List we cant put {@code List<String>.class} as dataType, so we can put it here.
     */
    private class DataChangeValueListener<T, R extends T> implements ValueEventListener
    {

        private Class<T> dataType;
        private DataChangeListener<R> dataChangeListener;
        private OrderByClause orderByClause;

        public DataChangeValueListener(Class<T> dataType, DataChangeListener<R> dataChangeListener, OrderByClause orderByClause)
        {
            this.dataChangeListener = dataChangeListener;
            this.dataType = dataType;
            this.orderByClause = orderByClause;
        }


        @Override
        @SuppressWarnings("unchecked")
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            DataListenerOnDataChangeResolver.resolve(dataType, orderByClause, dataSnapshot, dataChangeListener);
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            dataChangeListener.onCanceled(databaseError.toException());
        }


    }

    /**
     * Wrapper for {@link ValueEventListener} used when need to deal with {@link DatabaseReference#addValueEventListener(ValueEventListener)}
     * and getting information from {@code .info/connected} path.
     */
    private class ConnectionValueListener implements ValueEventListener
    {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot)
        {
            if (connectedListener == null) return;
            boolean connected = dataSnapshot.getValue(Boolean.class);
            if (connected) {
                connectedListener.onConnect();
            } else {
                connectedListener.onDisconnect();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError)
        {
            GdxFIRLogger.log(CONNECTION_LISTENER_CANCELED, databaseError.toException());
        }
    }
}
