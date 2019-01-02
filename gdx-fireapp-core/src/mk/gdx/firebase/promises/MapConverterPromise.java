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
import mk.gdx.firebase.reflection.AnnotationFinder;

/**
 * @param <R> Output promise type
 */
public class MapConverterPromise<R> extends FuturePromise<R> {

    private MapMitmConverter mapConverter;

    public MapConverterPromise<R> with(FirebaseMapConverter converter) {
        this.mapConverter = new MapMitmConverter(converter);
        return this;
    }

    @SuppressWarnings("unchecked")
    public synchronized void doCompleteWithConversion(Object object) {
        if (mapConverter == null)
            throw new IllegalStateException();
        MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, thenConsumer);
        if (mapConversionAnnotation != null) {
            object = mapConverter.doMitmConversion(mapConversionAnnotation.value(), object);
//            if (ClassReflection.isAssignableFrom(List.class, dataType) && data.getClass() == mapConversionAnnotation.value()) {
//                data = Collections.singletonList(data);
//            }
        }
        super.doComplete((R) object);
    }

    public static <R> MapConverterPromise<R> of(FirebaseMapConverter mapConverter, Consumer<MapConverterPromise<R>> consumer) {
        MapConverterPromise<R> promise = new MapConverterPromise<>();
        promise.with(mapConverter);
        consumer.accept(promise);
        return promise;
    }
}
