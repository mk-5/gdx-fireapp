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

package mk.gdx.firebase.reflection;

import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;

import mk.gdx.firebase.annotations.NestedGenericType;

/**
 * Gets information from annotations.
 */
public class AnnotationFinder {

    /**
     * Gets {@code NestedGenericType} annotation from object.
     * <p>
     * Looks at all object methods and returns first encountered annotation.
     *
     * @param object Object to deal with, not null
     * @return Annotation, may be null
     */
    public static NestedGenericType getNestedGenericTypeAnnotation(Object object) {
        NestedGenericType result = null;
        Method[] methods = ClassReflection.getMethods(object.getClass());
        // TODO - use type annotation, not method?
        for (Method m : methods) {
            Annotation[] annotations = m.getDeclaredAnnotations();
            Annotation a = m.getDeclaredAnnotation(NestedGenericType.class);
            if (a != null) {
                result = a.getAnnotation(NestedGenericType.class);
                break;
            }
        }
        return result;
    }

    /**
     * Find object methods annotation.
     * <p>
     * Looks at all object methods and returns first encountered annotation.
     *
     * @param annotationType Annotation type, not null
     * @param object         Object to deal with, not null
     * @return Annotation, may be null
     */
    public static <T extends java.lang.annotation.Annotation> T getMethodAnnotation(Class<T> annotationType, Object object) {
        T result = null;
        Method[] methods = ClassReflection.getMethods(object.getClass());
        for (Method m : methods) {
            Annotation[] annotations = m.getDeclaredAnnotations();
            Annotation a = m.getDeclaredAnnotation(annotationType);
            if (a != null) {
                result = a.getAnnotation(annotationType);
                break;
            }
        }
        return result;
    }
}
