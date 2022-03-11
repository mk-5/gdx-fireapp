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

package pl.mk5.gdx.fireapp;

import java.util.Map;

import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.database.FirebaseMapConverter;
import pl.mk5.gdx.fireapp.database.MapConverter;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;
import pl.mk5.gdx.fireapp.promises.Promise;

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
    GdxFIRDatabase(String dbUrl) {
        super(dbUrl);
        mapConverter = new MapConverter();
    }

    GdxFIRDatabase() {
        mapConverter = new MapConverter();
    }

    public static GdxFIRDatabase inst(String dbUrl) {
        return (GdxFIRDatabase) Api.instance(DatabaseDistribution.class, dbUrl);
    }

    public static GdxFIRDatabase instance() {
        return (GdxFIRDatabase) Api.instance(DatabaseDistribution.class);
    }

    /**
     * Alias of {@link #instance()}
     */
    public static GdxFIRDatabase inst() {
        return instance();
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
    public DatabaseDistribution.OnDisconnect onDisconnect() {
        return platformObject.onDisconnect();
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
    public <T, R extends T> ListenerPromise<R> onChildChange(Class<T> dataType, ChildEventType... eventsType) {
        return platformObject.onChildChange(dataType, eventsType);
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

    @Override
    public DatabaseDistribution orderBy(OrderByMode orderByMode) {
        return platformObject.orderBy(orderByMode, null);
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
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution goOffline() {
        return platformObject.goOffline();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public DatabaseDistribution goOnline() {
        return platformObject.goOnline();
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
        return "pl.mk5.gdx.fireapp.ios.database.Database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.android.database.Database";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "pl.mk5.gdx.fireapp.html.database.Database";
    }
}
