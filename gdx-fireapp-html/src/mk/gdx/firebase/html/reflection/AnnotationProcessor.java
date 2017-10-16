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

package mk.gdx.firebase.html.reflection;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.Annotation;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;

import mk.gdx.firebase.annotations.NestedGenericType;

/**
 * Gets information from annotations.
 */
public class AnnotationProcessor
{

    public static NestedGenericType getNestedGenericTypeAnnotation(Object object)
    {
        NestedGenericType annotation = null;
        Method[] methods = ClassReflection.getMethods(object.getClass());
        Gdx.app.log("AnnotationProcessor", "class: " + ClassReflection.getSimpleName(object.getClass()));
        Gdx.app.log("AnnotationProcessor", "methods: " + methods.length);
        Gdx.app.log("AnnotationProcessor", "annotations: " + ClassReflection.getDeclaredAnnotations(object.getClass()).length);
        for (Method m : methods) {
            Annotation a = m.getDeclaredAnnotation(NestedGenericType.class);
            if (a != null) {
                annotation = a.getAnnotation(NestedGenericType.class);
                break;
            }
        }
        return annotation;
    }
}
