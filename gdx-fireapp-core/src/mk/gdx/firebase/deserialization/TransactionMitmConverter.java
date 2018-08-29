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

package mk.gdx.firebase.deserialization;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.List;
import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.exceptions.MapConversionNotPossibleException;
import mk.gdx.firebase.reflection.AnnotationFinder;

/**
 * Wraps transaction results and do conversion from map to objects, if necessary.
 * <p>
 * Keep {@code TransactionCallback<T>} because of {@code coveredCallback} data will be cast to {@code <E>} after conversion.
 *
 * @param <T> Result type of callback
 */
public class TransactionMitmConverter<T, R extends T> extends MapMitmConverter implements TransactionCallback<T> {

    private TransactionCallback<R> coveredCallback;
    private Class<T> dataType;

    public TransactionMitmConverter(Class<T> dataType, TransactionCallback<R> coveredCallback, FirebaseMapConverter mapConverter) {
        super(mapConverter);
        this.dataType = dataType;
        this.coveredCallback = coveredCallback;
    }

    /**
     * Detect if client want POJO class or not and return appropriate callback.
     * <p>
     * If wanted type is POJO we need to retrieve Map and then convert it.
     * Otherwise we need to retrieve type given by user (as T generic type).
     *
     * @return DataCallback which retrieves data from database, not null.
     */
    public TransactionCallback<?> get() {
        return isPojo(dataType) ? getPojoCallback() : getGenericCallback();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public TransactionCallback<Map> getPojoCallback() {
        return new PojoTransactionCallback();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public TransactionCallback<T> getGenericCallback() {
        return new GenericTransactionCallback();
    }

    /**
     * Do MITM Map conversion pass to the client transaction callback as POJO, retrieve changed POJO and convert it back to the map.
     *
     * @param transactionData Data from database, we do modification on it.
     * @return Modified data
     */
    @Override
    @SuppressWarnings("unchecked")
    public Object run(Object transactionData) {
        MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, coveredCallback);
        // If user not indicated to do MapConversion.
        if (mapConversionAnnotation == null) {
            return coveredCallback.run((R) transactionData);
        } else {
            if (ClassReflection.isAssignableFrom(Map.class, transactionData.getClass())
                    || ClassReflection.isAssignableFrom(List.class, transactionData.getClass())) {
                R mitmData = (R) doMitmConversion(mapConversionAnnotation.value(), transactionData);
                mitmData = coveredCallback.run(mitmData);
                return mapConverter.unConvert(mitmData);
            } else {
                throw new MapConversionNotPossibleException(MapMitmConverter.CANT_DO_MAP_CONVERSION_FROM_TYPE + transactionData.getClass().getSimpleName());
            }
        }
    }

    /**
     * Retrieve map pass to {@link #run(Object)}
     */
    private class PojoTransactionCallback implements TransactionCallback<Map> {
        @Override
        public Map run(Map transactionData) {
            return (Map) TransactionMitmConverter.this.run(transactionData);
        }
    }

    /**
     * Retrieve {@code T} object and pass to  {@link #run(Object)}
     */
    private class GenericTransactionCallback implements TransactionCallback<T> {

        @Override
        @SuppressWarnings("unchecked")
        public T run(T transactionData) {
            return (T) TransactionMitmConverter.this.run(transactionData);
        }
    }
}
