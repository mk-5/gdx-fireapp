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

package pl.mk5.gdx.fireapp.promises;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.Collections;
import java.util.List;

import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.deserialization.FirebaseMapConverter;
import pl.mk5.gdx.fireapp.deserialization.MapMitmConverter;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.functional.Function;
import pl.mk5.gdx.fireapp.reflection.AnnotationFinder;

/**
 * @param <R> Output promise type
 */
public class ConverterPromise<T, R> extends FutureListenerPromise<R> {

    private MapMitmConverter mapConverter;
    private Function<T, R> modifier;
    private Class<T> wantedDataType;

    public ConverterPromise<T, R> with(Function<T, R> modifier) {
        this.modifier = modifier;
        return this;
    }

    public ConverterPromise<T, R> with(FirebaseMapConverter converter, Class<T> wantedDataType) {
        this.mapConverter = new MapMitmConverter(converter);
        this.wantedDataType = wantedDataType;
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void doComplete(Object object) {
        if (canceled) return;
        if (mapConverter == null)
            throw new IllegalStateException();
        if (modifier != null) {
            object = modifier.apply((T) object);
        }
        if (!ClassReflection.isAssignableFrom(List.class, wantedDataType)) {
            if (mapConverter.isPojo(wantedDataType)) {
                object = mapConverter.doMitmConversion(wantedDataType, object);
            }
        } else {
            if (thenConsumer.isSet()) {
                MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, thenConsumer.first());
                if (mapConversionAnnotation != null) {
                    object = mapConverter.doMitmConversion(mapConversionAnnotation.value(), object);
                    if (object.getClass() == mapConversionAnnotation.value()) {
                        object = Collections.singletonList(object);
                    }
                }
            }
        }
        super.doComplete((R) object);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> ConverterPromise<T, R> whenWithConvert(final Consumer<ConverterPromise<T, R>> consumer) {
        final ConverterPromise<T, R> promise = new ConverterPromise<>();
        promise.execution = new Runnable() {
            @Override
            public void run() {
                promise.execution = null;
                try {
                    consumer.accept(promise);
                } catch (Exception e) {
                    promise.getBottomThenPromise().doFail(e);
                }
            }
        };
        return promise;
    }
}
