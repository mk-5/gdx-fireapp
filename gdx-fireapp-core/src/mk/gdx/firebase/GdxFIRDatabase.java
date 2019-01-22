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

import mk.gdx.firebase.database.ConnectionStatus;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.deserialization.FirebaseMapConverter;
import mk.gdx.firebase.deserialization.MapConverter;
import mk.gdx.firebase.distributions.DatabaseDistribution;
import mk.gdx.firebase.functional.Function;
import mk.gdx.firebase.promises.ListenerPromise;
import mk.gdx.firebase.promises.Promise;

/**
 * Gets access to Firebase Database API in multi-modules.
 *
 * @see DatabaseDistribution
 * @see PlatformDistributor
 */
public class GdxFIRDatabase extends PlatformDistributor<DatabaseDistribution> implements DatabaseDistribution {

    private static volatile GdxFIRDatabase instance;
    private FirebaseMapConverter mapConverter;

    /**
     * GdxFIRDatabase protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    private GdxFIRDatabase() {
        mapConverter = new MapConverter();
    }

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRDatabase instance() {
        GdxFIRDatabase result = instance;
        if (result == null) {
            synchronized (GdxFIRDatabase.class) {
                result = instance;
                if (result == null) {
                    instance = result = new GdxFIRDatabase();
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ListenerPromise<ConnectionStatus> onConnect() {
        return platformObject.onConnect();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution inReference(String databasePath) {
        platformObject.inReference(databasePath);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> setValue(Object value) {
        return platformObject.setValue(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, E extends T> Promise<E> readValue(Class<T> dataType) {
        return platformObject.readValue(dataType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, E extends T> ListenerPromise<E> onDataChange(Class<T> dataType) {
        return platformObject.onDataChange(dataType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <V> DatabaseDistribution filter(FilterType filterType, V... filterArguments) {
        platformObject.filter(filterType, filterArguments);
        return this;
    }

    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode, String argument) {
        platformObject.orderBy(orderByMode, argument);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution push() {
        return platformObject.push();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> removeValue() {
        return platformObject.removeValue();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> updateChildren(Map<String, Object> data) {
        return platformObject.updateChildren(data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <T, R extends T> Promise<Void> transaction(Class<T> dataType, Function<R, R> transaction) {
//        TransactionMitmConverter<T, R> mitmConverter = new TransactionMitmConverter<T, R>(dataType, transactionCallback, mapConverter);
//        if (mitmConverter.isPojo(dataType)) {
//            platformObject.transaction(Map.class, mitmConverter.getPojoCallback(), completeCallback);
//        } else {
//            platformObject.transaction(dataType, mitmConverter.getGenericCallback(), completeCallback);
//        }
        // maybe conversion is not needed? TODO - check data types in transactions
        return platformObject.transaction(dataType, transaction);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPersistenceEnabled(boolean enabled) {
        platformObject.setPersistenceEnabled(enabled);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void keepSynced(boolean synced) {
        platformObject.keepSynced(synced);
    }

    /**
     * Sets map converter which is use to do conversion between database maps into instance objects.
     *
     * @param mapConverter Map convert instance, not null
     */
    public void setMapConverter(FirebaseMapConverter mapConverter) {
        if (mapConverter == null) throw new IllegalArgumentException();
        this.mapConverter = mapConverter;
    }

    /**
     * @return Map converter instance, not null
     */
    public FirebaseMapConverter getMapConverter() {
        return mapConverter;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "mk.gdx.firebase.ios.database.Database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "mk.gdx.firebase.android.database.Database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "mk.gdx.firebase.html.database.Database";
    }
}
