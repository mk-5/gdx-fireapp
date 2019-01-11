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

package mk.gdx.firebase.promises;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.deserialization.FirebaseMapConverter;
import mk.gdx.firebase.deserialization.MapMitmConverter;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.functional.Function;
import mk.gdx.firebase.reflection.AnnotationFinder;

/**
 * @param <R> Output promise type
 */
public class ConverterPromise<T, R> extends FuturePromise<R> {

    private MapMitmConverter mapConverter;
    private Function<T, R> modifier;

    public ConverterPromise<T, R> with(Function<T, R> modifier) {
        this.modifier = modifier;
        return this;
    }

    public ConverterPromise<T, R> with(FirebaseMapConverter converter) {
        this.mapConverter = new MapMitmConverter(converter);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized void doComplete(Object object) {
        if (mapConverter == null)
            throw new IllegalStateException();
        if (modifier != null) {
            object = (R) modifier.apply((T) object);
        }
        if (thenConsumer != null) {
            MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, thenConsumer);
            if (mapConversionAnnotation != null) {
                object = mapConverter.doMitmConversion(mapConversionAnnotation.value(), object);
                // TODO - need it?
//            if (ClassReflection.isAssignableFrom(List.class, dataType) && data.getClass() == mapConversionAnnotation.value()) {
//                data = Collections.singletonList(data);
//            }
            }
        }
        super.doComplete((R) object);
    }

    @SuppressWarnings("unchecked")
    public static <T, R> ConverterPromise<T, R> of(Object consumer) {
        if (!(consumer instanceof Consumer)) throw new IllegalArgumentException();
        ConverterPromise<T, R> promise = new ConverterPromise<>();
        ((Consumer<ConverterPromise<T, R>>) consumer).accept(promise);
        return promise;
    }
}
