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

package pl.mk5.gdx.fireapp.distributions;

import java.util.Map;

import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides access to Firebase database.
 * <p>
 * Before you do some operations on database you should chose on which parts of data you want to operate.<p>
 * To do that you need to call {@link #inReference(String)} after each of following methods:<p>
 * <ul>
 * <li>{@link #setValue(Object)}
 * <li>{@link #updateChildren(Map)}
 * <li>{@link #onDataChange(Class)}
 * <li>{@link #readValue(Class)}
 * <li>{@link #push()}
 * <li>{@link #transaction(Class, Function)}
 * </ul><p>
 * If you do not do this {@code RuntimeException} will be thrown.
 */
public interface DatabaseDistribution {

    /**
     * Listens for database connection events.
     * <p>
     * Catch moment when application is going to to be connected or disconnected to the database.
     */
    ListenerPromise<ConnectionStatus> onConnect();

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
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    Promise<Void> setValue(Object value);

    /**
     * Reads value from path given by {@code inReference(String)}.
     * <p>
     * POJO objects received from each platform should be represented as Map.
     * Conversion will be guarantee later by {@link pl.mk5.gdx.fireapp.deserialization.MapConverter}
     *
     * @param dataType Class you want to retrieve
     * @param <T>      Type of data you want to retrieve, associated with {@code dataType} for ex. {@code List.class}
     * @param <R>      More specific type of data you want - should be not-abstract type.
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    <T, R extends T> Promise<R> readValue(Class<T> dataType);

    /**
     * Handles value changes for path given by {@code inReference(String)}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     * <p>
     * POJO objects received from each platform should be represented as Map. Conversion will be guarantee later by {@link pl.mk5.gdx.fireapp.deserialization.MapConverter}
     * <p>
     *
     * @param dataType Class you want to retrieve
     * @param <T>      Type of data you want to retrieve, associated with {@code dataType} for ex. {@code List.class}
     * @param <R>      More specific type of data you want to retrieve - should be not-abstract type.
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    <T, R extends T> ListenerPromise<R> onDataChange(Class<T> dataType);

    /**
     * Applies filter to the next database query.
     * <p>
     * It should be applied only after {@link #readValue(Class)} or {@link #onDataChange(Class)} execution.
     * You can read more about filtering here: <a href="https://firebase.google.com/docs/database/android/lists-of-data">firebase filtering</a>
     *
     * @param filterType      Filter type that you want to applied, not null
     * @param filterArguments Arguments that will be pass to filter method
     * @param <V>             Type of filter argument, it should be one of the following: Integer, Double, String, Boolean
     * @return this
     */
    <V> DatabaseDistribution filter(FilterType filterType, V... filterArguments);

    /**
     * Applies order-by to the next database query.
     * <p>
     * Only one orderBy can be applied to one query otherwise error will be throw.
     * <p>
     * For now, the only mode which process argument is: {@link OrderByMode#ORDER_BY_CHILD}
     *
     * @param orderByMode Order-by mode, not null
     * @param argument    Order by func argument, may be null
     * @return this
     */
    DatabaseDistribution orderBy(OrderByMode orderByMode, String argument);

    /**
     * Same as {@link #orderBy(OrderByMode, String)} but with null argument.
     *
     * @see #orderBy(OrderByMode, String)
     */
    DatabaseDistribution orderBy(OrderByMode orderByMode);

    /**
     * Creates new object inside database and return {@code this instance} with reference to it set by {@code DatabaseDistribution#inReference()}
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @return this
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    DatabaseDistribution push();

    /**
     * Removes value in path given by {@code inReference(String)}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    Promise<Void> removeValue();

    /**
     * Updates children's for path given by {@code inReference(String)}.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param data New data
     * @throws RuntimeException if {@link #inReference(String)} was not call after.
     */
    Promise<Void> updateChildren(Map<String, Object> data);

    /**
     * Provides transaction for value at path give by {@code inReference(String)}
     * <p>
     * Value that you want to change will be get in {@link Function} - there you should
     * modify data and returns a new one.
     * <p>
     * Remember to set database reference earlier by calling the {@link #inReference(String)} method.
     *
     * @param dataType            Type of data you want to get, not null
     * @param transactionFunction Function to modify transaction data, not null
     * @throws RuntimeException if {@link #inReference(String)} was not call after call this method.
     */
    <T, R extends T> Promise<Void> transaction(Class<T> dataType, Function<R, R> transactionFunction);

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
     * @throws RuntimeException if {@link #inReference(String)} was not call after call this method.
     */
    void keepSynced(boolean synced);
}
