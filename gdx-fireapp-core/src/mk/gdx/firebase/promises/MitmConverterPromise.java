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

import mk.gdx.firebase.deserialization.FirebaseMapConverter;

public class MitmConverterPromise<T> extends FuturePromise<T> {

    private FirebaseMapConverter mapConverter;
    private Class dataType;

    public MitmConverterPromise<T> withConverter(FirebaseMapConverter mapConverter) {
        this.mapConverter = mapConverter;
        return this;
    }

    public MitmConverterPromise<T> forDataType(Class dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    public synchronized void doComplete(Object result) {
//        MapConversion mapConversionAnnotation = AnnotationFinder.getMethodAnnotation(MapConversion.class, coveredCallback);
//        if (mapConversionAnnotation != null) {
//            result = doMitmConversion(mapConversionAnnotation.value(), data);
//            if (ClassReflection.isAssignableFrom(List.class, dataType) && data.getClass() == mapConversionAnnotation.value()) {
//                result = Collections.singletonList(data);
//            }
//        }
        super.doComplete((T) result);
    }
}
