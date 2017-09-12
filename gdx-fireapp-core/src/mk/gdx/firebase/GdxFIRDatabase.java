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

package mk.gdx.firebase;

import java.util.Map;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.exceptions.PlatformDistributorException;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Gets access to Firebase Database API in multi-modules.
 *
 * @see DatabaseDistribution
 * @see PlatformDistributor
 */
public class GdxFIRDatabase extends PlatformDistributor<DatabaseDistribution> implements DatabaseDistribution
{

    private static GdxFIRDatabase instance;

    /**
     * GdxFIRDatabase protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    protected GdxFIRDatabase() throws PlatformDistributorException
    {
    }

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRDatabase instance()
    {
        if (instance == null) {
            synchronized (GdxFIRAnalytics.class) {
                if (instance == null) {
                    try {
                        instance = new GdxFIRDatabase();
                    } catch (PlatformDistributorException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConnect(ConnectedListener connectedListener)
    {
        platformObject.onConnect(connectedListener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath)
    {
        return platformObject.inReference(databasePath);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value)
    {
        platformObject.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object value, CompleteCallback completeCallback)
    {
        platformObject.setValue(value, completeCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, E extends T> void readValue(Class<T> dataType, DataCallback<E> callback)
    {
        platformObject.readValue(dataType, callback);
    }

    @Override
    public <T, E extends T> void onDataChange(Class<T> dataType, DataChangeListener<E> listener)
    {
        platformObject.onDataChange(dataType, listener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push()
    {
        return platformObject.push();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue()
    {
        platformObject.removeValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeValue(CompleteCallback completeCallback)
    {
        platformObject.removeValue(completeCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChildren(Map<String, Object> data)
    {
        platformObject.updateChildren(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateChildren(Map<String, Object> data, CompleteCallback completeCallback)
    {
        platformObject.updateChildren(data, completeCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R extends T> void transaction(Class<T> dataType, TransactionCallback<R> transactionCallback, CompleteCallback completeCallback)
    {
        platformObject.transaction(dataType, transactionCallback, completeCallback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistenceEnabled(boolean enabled)
    {
        platformObject.setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced)
    {
        platformObject.keepSynced(synced);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName()
    {
        return "mk.gdx.firebase.ios.database.Database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName()
    {
        return "mk.gdx.firebase.android.database.Database";
    }
}
