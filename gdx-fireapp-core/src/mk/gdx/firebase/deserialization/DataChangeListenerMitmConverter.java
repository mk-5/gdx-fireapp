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

import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.annotations.NestedGenericType;
import mk.gdx.firebase.listeners.DataChangeListener;
import mk.gdx.firebase.reflection.AnnotationFinder;

/**
 * Wraps data listener results and do conversion from map to objects, if necessary.
 * <p>
 * We call {@code DataChangeListener<T>} because of nested generic type. For ex. it can be List<User> and we can't do such conversion directly to into the database.
 * {@code coveredListener} data will be cast to {@code <E>} after conversion.
 *
 * @param <T> Result type of listener
 */
public class DataChangeListenerMitmConverter<T, E extends T> extends MapMitmConverter {

    private DataChangeListener<E> coveredListener;
    private Class<T> dataType;

    public DataChangeListenerMitmConverter(Class<T> dataType, DataChangeListener<E> coveredListener, FirebaseMapConverter mapConverter) {
        super(mapConverter);
        this.coveredListener = coveredListener;
    }

    /**
     * Detect if client want POJO class or not and return appropriate callback.
     * <p>
     * If wanted type is POJO we need to retrieve Map and then convert it.
     * Otherwise we need to retrieve type given by user (as T generic type).
     *
     * @return DataChangeListener which retrieves data from database, not null.
     */
    public DataChangeListener<?> get() {
        return isPojo(dataType) ? getPojoListener() : getGenericListener();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public DataChangeListener<Map> getPojoListener() {
        return new PojoDataChangeListener();
    }

    /**
     * Get callback appropriate for pojo retrieving.
     *
     * @return New Pojo data callback instance, not null.
     */
    public DataChangeListener<T> getGenericListener() {
        return new GenericDataChangeListener();
    }

    /**
     * Do MITM Map conversion and pass through to {@code coveredListener}.
     *
     * @param data Received data from Firebase - data was cast specified generic type {@code <T>}, may be null.
     * @see #doMitmConversion(Class, Object)
     */
    @SuppressWarnings("unchecked")
    public void onChange(Object data) {
        MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, coveredListener);
        // If MapConversions was not indicated - do nothing.
        if (mapConversionAnnotation != null) {
            data = (E) doMitmConversion(mapConversionAnnotation.value(), data);
        } else {
            // Depracated - will be remove in next releases.
            NestedGenericType nestedGenericTypeAnnotation = AnnotationFinder.getMethodAnnotation(NestedGenericType.class, coveredListener);
            if (nestedGenericTypeAnnotation != null) {
                data = (E) doMitmConversion(nestedGenericTypeAnnotation.value(), data);
            }
        }
        coveredListener.onChange((E) data);
    }

    /**
     * Pass through to {@code coveredListener}.
     *
     * @param e Exception with description what was wrong.
     */
    public void onCanceled(Exception e) {
        coveredListener.onCanceled(e);
    }

    /**
     * Retrieve map from database and pass to {@link #onChange(Object)}
     */
    private class PojoDataChangeListener implements DataChangeListener<Map> {

        @Override
        public void onChange(Map data) {
            DataChangeListenerMitmConverter.this.onChange(data);
        }

        @Override
        public void onCanceled(Exception e) {
            DataChangeListenerMitmConverter.this.onCanceled(e);
        }
    }

    /**
     * Retrieve {@code T} object from database and pass to  {@link #onChange(Object)}
     */
    private class GenericDataChangeListener implements DataChangeListener<T> {

        @Override
        public void onChange(T data) {
            DataChangeListenerMitmConverter.this.onChange(data);
        }

        @Override
        public void onCanceled(Exception e) {
            DataChangeListenerMitmConverter.this.onCanceled(e);
        }
    }
}
