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

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.reflection.AnnotationFinder;

/**
 * Wraps data callback results and do conversion from map to objects, if necessary.
 * <p>
 * DataCallbackMitmConverter means: Data callback man-in-the-middle converter.
 * We call {@code DataCallback<T>} because of nested generic type. For ex. it can be List<User> and we can't do such conversion directly to into the database.
 * {@code coveredCallback} data will be cast to {@code <E>} after conversion.
 * TODO - get mapConverter from GdxFIRDatabase instance? After converter change it may occur invalid result.
 *
 * @param <T> Result type of callback
 */
public class DataCallbackMitmConverter<T, E extends T> extends MapMitmConverter {

    private DataCallback<E> coveredCallback;
    private Class<T> dataType;

    public DataCallbackMitmConverter(Class<T> dataType, DataCallback<E> coveredCallback, FirebaseMapConverter mapConverter) {
        super(mapConverter);
        this.coveredCallback = coveredCallback;
        this.dataType = dataType;
    }

    /**
     * Detect if client want POJO class or not and return appropriate callback.
     * <p>
     * If wanted type is POJO we need to retrieve Map and then convert it.
     * Otherwise we need to retrieve type given by user (as T generic type).
     *
     * @return DataCallback which retrieves data from database, not null.
     */
    public DataCallback<?> get() {
        return isPojo(dataType) ? getPojoDataCallback() : getGenericDataCallback();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public DataCallback<Map> getPojoDataCallback() {
        return new PojoDataCallback();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public DataCallback<T> getGenericDataCallback() {
        return new GenericDataCallback();
    }

    /**
     * Do MITM Map conversion and pass through to {@code coveredCallback}.
     *
     * @param data Received data from Firebase - data was cast specified generic type {@code <T>}, may be null.
     * @see #doMitmConversion(Class, Object)
     */
    @SuppressWarnings("unchecked")
    public void onData(Object data) {
        MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, coveredCallback);
        if (mapConversionAnnotation != null) {
            data = doMitmConversion(mapConversionAnnotation.value(), data);
            if (ClassReflection.isAssignableFrom(List.class, dataType) && data.getClass() == mapConversionAnnotation.value()) {
                data = Collections.singletonList(data);
            }
        }
        coveredCallback.onData((E) data);
    }

    /**
     * Pass through to {@code coveredCallback}.
     *
     * @param e Exception with description what was wrong.
     */
    public void onError(Exception e) {
        coveredCallback.onError(e);
    }

    /**
     * Retrieve map from database and pass to {@link #onData(Object)}
     */
    private class PojoDataCallback implements DataCallback<Map> {

        @Override
        public void onData(Map data) {
            DataCallbackMitmConverter.this.onData(data);
        }

        @Override
        public void onError(Exception e) {
            DataCallbackMitmConverter.this.onError(e);
        }
    }

    /**
     * Retrieve {@code T} object from database and pass to {@link #onData(Object)}
     */
    private class GenericDataCallback implements DataCallback<T> {

        @Override
        public void onData(T data) {
            DataCallbackMitmConverter.this.onData(data);
        }

        @Override
        public void onError(Exception e) {
            DataCallbackMitmConverter.this.onError(e);
        }
    }
}
