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

import com.google.firebasedatabase.FIRDataSnapshot;
import com.google.firebasedatabase.FIRDatabase;
import com.google.firebasedatabase.FIRDatabaseReference;
import com.google.firebasedatabase.FIRMutableData;
import com.google.firebasedatabase.FIRTransactionResult;
import com.google.firebasedatabase.enums.FIRDataEventType;

import java.io.FileNotFoundException;
import java.util.Map;

import apple.foundation.NSError;
import apple.foundation.NSNull;
import apple.foundation.NSNumber;
import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.exceptions.DatabaseReferenceNotSetException;
import mk.gdx.firebase.ios.helpers.GenericPlaceholder;
import mk.gdx.firebase.ios.helpers.NSDictionaryHelper;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * iOS Firebase database API implementation.
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

    FIRDatabaseReference dbReference;
    private String databasePath;

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnect(final ConnectedListener connectedListener)
    {
        FIRDatabase.database().referenceWithPath(".info/connected").observeEventTypeWithBlock(FIRDataEventType.Value, new FIRDatabaseReference.Block_observeEventTypeWithBlock()
        {
            @Override
            public void call_observeEventTypeWithBlock(FIRDataSnapshot arg0)
            {
                boolean connected = ((NSNumber) arg0.value()).boolValue();
                if (connected)
                    connectedListener.onConnect();
                else
                    connectedListener.onDisconnect();
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath)
    {
        dbReference = FIRDatabase.database().referenceWithPath(databasePath);
        this.databasePath = databasePath;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value)
    {
        dbReference().setValue(DataProcessor.javaDataToIos(value));
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value, CompleteCallback completeCallback)
    {
        dbReference().setValueWithCompletionBlock(DataProcessor.javaDataToIos(value), new FIRDatabaseReference.Block_setValueWithCompletionBlock()
        {
            @Override
            public void call_setValueWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1)
            {
                if (arg0 != null) {
                    completeCallback.onError(new Exception(arg0.localizedDescription()));
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
    public <T, R extends T> void readValue(Class<T> dataType, DataCallback<R> callback)
    {
        dbReference().observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(FIRDataEventType.Value, new FIRDatabaseReference.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1()
        {
            @Override
            public void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1(FIRDataSnapshot arg0, String arg1)
            {
                if (arg0.value() == null) {
                    // TODO - ::onFileNotFound
                    callback.onError(new FileNotFoundException());
                } else {
                    T data = null;
                    try {
                        data = DataProcessor.iosDataToJava(arg0.value(), dataType);
                    } catch (Exception e) {
                        if (callback != null) {
                            callback.onError(e);
                        } else {
                            GdxFIRLogger.error(e.getLocalizedMessage(), e);
                        }
                        return;
                    }
                    callback.onData((R) data);
                }
            }
        }, new FIRDatabaseReference.Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2()
        {
            @Override
            public void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2(NSError arg0)
            {
                callback.onError(new Exception(arg0.localizedDescription()));
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T, R extends T> void onDataChange(Class<T> dataType, DataChangeListener<R> listener)
    {
        dbReference().observeEventTypeWithBlockWithCancelBlock(FIRDataEventType.Value, new FIRDatabaseReference.Block_observeEventTypeWithBlockWithCancelBlock_1()
        {

            @Override
            public void call_observeEventTypeWithBlockWithCancelBlock_1(FIRDataSnapshot arg0)
            {
                if (arg0.value() == null) {
                    // TODO - onFileNotFound
                    listener.onCanceled(new FileNotFoundException());
                } else {
                    T data = null;
                    try {
                        data = DataProcessor.iosDataToJava(arg0.value(), dataType);
                    } catch (Exception e) {
                        if (listener != null) {
                            listener.onCanceled(e);
                        } else {
                            GdxFIRLogger.error(e.getLocalizedMessage(), e);
                        }
                        return;
                    }
                    listener.onChange((R) data);
                }
            }
        }, new FIRDatabaseReference.Block_observeEventTypeWithBlockWithCancelBlock_2()
        {

            @Override
            public void call_observeEventTypeWithBlockWithCancelBlock_2(NSError arg0)
            {
                listener.onCanceled(new Exception(arg0.localizedDescription()));
            }
        });
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push()
    {
        dbReference = dbReference().childByAutoId();
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue()
    {
        dbReference().removeValue();
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue(CompleteCallback completeCallback)
    {
        dbReference().removeValueWithCompletionBlock(new FIRDatabaseReference.Block_removeValueWithCompletionBlock()
        {
            @Override
            public void call_removeValueWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1)
            {
                if (arg0 != null) {
                    completeCallback.onError(new Exception(arg0.localizedDescription()));
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
        dbReference().updateChildValues(NSDictionaryHelper.toNSDictionary(data));
        terminateOperation();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChildren(Map<String, Object> data, CompleteCallback completeCallback)
    {
        dbReference().updateChildValuesWithCompletionBlock(NSDictionaryHelper.toNSDictionary(data), new FIRDatabaseReference.Block_updateChildValuesWithCompletionBlock()
        {
            @Override
            public void call_updateChildValuesWithCompletionBlock(NSError arg0, FIRDatabaseReference arg1)
            {
                if (arg0 != null) {
                    completeCallback.onError(new Exception(arg0.localizedDescription()));
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
    public <T, R extends T> void transaction(Class<T> dataType, TransactionCallback<R> transactionCallback, CompleteCallback completeCallback)
    {
        dbReference().runTransactionBlockAndCompletionBlock(new FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_0()
        {
            @Override
            public FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(FIRMutableData arg0)
            {
                if (NSNull.class.isAssignableFrom(arg0.value().getClass())) {
                    GdxFIRLogger.error(TRANSACTION_NULL_VALUE_RETRIEVED);
                    return FIRTransactionResult.abort();
                }
                T transactionObject = DataProcessor.iosDataToJava(arg0.value(), dataType);
                arg0.setValue(DataProcessor.javaDataToIos(transactionCallback.run((R) transactionObject)));
                return FIRTransactionResult.successWithValue(arg0);
            }
        }, new FIRDatabaseReference.Block_runTransactionBlockAndCompletionBlock_1()
        {
            @Override
            public void call_runTransactionBlockAndCompletionBlock_1(NSError arg0, boolean arg1, FIRDataSnapshot arg2)
            {
                if (arg0 != null) {
                    if (completeCallback != null) {
                        completeCallback.onError(new Exception(arg0.localizedDescription()));
                    } else {
                        GdxFIRLogger.error(TRANSACTION_ERROR, new Exception(arg0.localizedDescription()));
                    }
                } else {
                    if (arg1) {
                        completeCallback.onSuccess();
                    } else {
                        completeCallback.onError(new Exception(TRANSACTION_NOT_ABLE_TO_UPDATE));
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
        FIRDatabase.database().setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced)
    {
        dbReference().keepSynced(synced);
        terminateOperation();
    }

    /**
     * Simple getter of {@link FIRDatabaseReference} which this {@link Database} instance will be deal with.
     *
     * @return FirebaseSDK Database reference. Every action will be deal with it.
     * @throws DatabaseReferenceNotSetException It is thrown when user forgot to call {@link #inReference(String)}
     */
    private FIRDatabaseReference dbReference()
    {
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
        dbReference = null;
        databasePath = null;
    }

}
