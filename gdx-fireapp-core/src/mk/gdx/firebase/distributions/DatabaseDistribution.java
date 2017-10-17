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

package mk.gdx.firebase.distributions;

import java.util.Map;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Provides access to Firebase database.
 * <p>
 * Before you do some operations on database you should chose on which parts of data you want to operate.<p>
 * To do that you need to call {@link #inReference(String)} before each of following methods:<p>
 * <ul>
 * <li>{@link #setValue(Object)}
 * <li>{@link #setValue(Object, CompleteCallback)}
 * <li>{@link #updateChildren(Map)}
 * <li>{@link #updateChildren(Map, CompleteCallback)}
 * <li>{@link #onDataChange(Class, DataChangeListener)}
 * <li>{@link #readValue(Class, DataCallback)}
 * <li>{@link #push()}
 * <li>{@link #transaction(Class, TransactionCallback, CompleteCallback)}
 * </ul><p>
 * If you do not do this {@code RuntimeException} will be thrown.
 */
public interface DatabaseDistribution {

    /**
     * Listens for database connection events.
     * <p>
     * Catch moment when application is going to to be connected or disconnected to the database.
     *
     * @param connectedListener Listener that handles moments when connection status into database was change
     */
    void onConnect(ConnectedListener connectedListener);

    /**
     * Sets database path you want to deal with in next action.
     *
     * @param databasePath Reference inside your database for ex. {@code "/users"}
     * @return this instance with the path set
     */
    DatabaseDistribution inReference(String databasePath);

    /**
     * Sets value for path given by {@code inReference(String)}.
     *
     * @param value Any value which you want to store. Given object will be transformed to Firebase-like data type.
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     */
    void setValue(Object value);

    /**
     * Sets value for path given by {@code inReference(String)} and gives response by {@code CompleteCallback}.
     *
     * @param value            Any value which you want to store. Given object will be transformed to Firebase-like data type.
     * @param completeCallback Callback that handles response
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     * @see CompleteCallback
     */
    void setValue(Object value, CompleteCallback completeCallback);


    /**
     * Reads value from path given by {@code inReference(String)} and gives response by {@code DataCallback}.
     *
     * @param dataType Class you want to retrieve
     * @param callback Callback that handles response
     * @param <T>      Type of data you want to retrieve, associated with {@code dataType} for ex. {@code List.class}
     * @param <R>      More specific type of data you want to retrieve associated with {@code callback} - should be not-abstract type.
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     * @see DataCallback
     */
    <T, R extends T> void readValue(Class<T> dataType, DataCallback<R> callback);

    /**
     * Handles value changes for path given by {@code inReference(String)} and gives response by {@code DataChangeListener}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param dataType Class you want to retrieve
     * @param listener Listener, may by null - if null all listeners for specified database reference will be removed.
     * @param <T>      Type of data you want to retrieve, associated with {@code dataType} for ex. {@code List.class}
     * @param <R>      More specific type of data you want to retrieve associated with {@code listener} - should be not-abstract type.
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     * @see DataChangeListener
     */
    <T, R extends T> void onDataChange(Class<T> dataType, DataChangeListener<R> listener);

    /**
     * Creates new object inside database and return {@code this instance} with reference to it set by {@code DatabaseDistribution#inReference()}
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @return this
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     */
    DatabaseDistribution push();

    /**
     * Removes value in path given by {@code inReference(String)}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     */
    void removeValue();

    /**
     * Removes value for path given by {@code inReference(String)} and gives response by {@code DataChangeListener}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param completeCallback Complete callback
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     * @see CompleteCallback
     */
    void removeValue(CompleteCallback completeCallback);

    /**
     * Updates children's for path given by {@code inReference(String)}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param data New data
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     */
    void updateChildren(Map<String, Object> data);

    /**
     * Updates children's for path given by {@code inReference(String)} and gives response by {@code CompleteCallback}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param data             New data
     * @param completeCallback Callback when done
     * @throws RuntimeException if {@link #inReference(String)} was not call before.
     */
    void updateChildren(Map<String, Object> data, CompleteCallback completeCallback);

    /**
     * Provides transaction for value describe by path given by {@code inReference(String)} and gives response by {@code CompleteCallback}
     * <p>
     * Value that you want to change will be get in {@link TransactionCallback#run(Object)} - there you should<p>
     * modify data and returns a new one.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param dataType            Type of data you want to get.
     * @param transactionCallback Callback called when transaction is complete.
     * @param completeCallback    Can be null
     * @throws RuntimeException if {@link #inReference(String)} was not call before call this method.
     * @see CompleteCallback
     * @see TransactionCallback
     */
    <T, R extends T> void transaction(Class<T> dataType, TransactionCallback<R> transactionCallback, CompleteCallback completeCallback);

    /**
     * Keeps your data for offline usage.
     * <p>
     * You can read more <a href="https://firebase.google.com/docs/database/android/offline-capabilities">here</a> and <a href="https://firebase.google.com/docs/database/ios/offline-capabilities">here</a>
     *
     * @param enabled e
     */
    void setPersistenceEnabled(boolean enabled);

    /**
     * Keeps data fresh.
     * <p>
     * You can read more <a href="https://firebase.google.com/docs/database/android/offline-capabilities">here</a> and <a href="https://firebase.google.com/docs/database/ios/offline-capabilities">here</a>
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param synced If true sync for specified database path will be enabled
     * @throws RuntimeException if {@link #inReference(String)} was not call before call this method.
     */
    void keepSynced(boolean synced);
}
