/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.reflection;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

public class DefaultTypeRecognizer {

    private static final String CANT_FIND_DEFAULT_VALUE_FOR_GIVEN_TYPE = "Cant find create default transaction value for given type";

    private static final Array<String> kotlinLongNumberClassNames = new Array<>();
    private static final Array<String> kotlinFloatingPointNumberClassNames = new Array<>();
    private static final Array<String> kotlinPrimitivesNames = new Array<>();

    static {
        kotlinLongNumberClassNames.addAll("long", "byte");
        kotlinFloatingPointNumberClassNames.addAll("float", "double");
        kotlinPrimitivesNames.addAll("long", "float", "double", "boolean", "char", "byte");
    }

    public static Object getDefaultValue(Class type) {
        if (isFloatingPointNumberType(type)) {
            return 0.;
        } else if (isLongNumberType(type)) {
            return 0L;
        } else if (isBooleanType(type)) {
            return false;
        } else if (isStringType(type)) {
            return "";
        } else if (isCharType(type)) {
            return '\u0000';
        } else if (ClassReflection.isAssignableFrom(Map.class, type)) {
            return new HashMap<>();
        } else if (ClassReflection.isAssignableFrom(List.class, type)) {
            return new ArrayList<>();
        } else {
            try {
                return ClassReflection.newInstance(type);
            } catch (ReflectionException e) {
                GdxFIRLogger.error(CANT_FIND_DEFAULT_VALUE_FOR_GIVEN_TYPE);
                return null;
            }
        }
    }

    public static boolean isLongNumberType(Class type) {
        return Long.class.equals(type)
                || long.class.equals(type)
                || Integer.class.equals(type)
                || int.class.equals(type)
                || kotlinLongNumberClassNames.contains(type.getName(), false);
    }

    public static boolean isFloatingPointNumberType(Class type) {
        return Float.class.equals(type)
                || float.class.equals(type)
                || Double.class.equals(type)
                || double.class.equals(type)
                || kotlinFloatingPointNumberClassNames.contains(type.getName(), false);
    }

    private static boolean isBooleanType(Class type) {
        return Boolean.class.equals(type)
                || "boolean".equals(type.getName());
    }

    private static boolean isCharType(Class type) {
        return Character.class.equals(type) || "char".equals(type.getName());
    }

    private static boolean isStringType(Class type) {
        return String.class.equals(type);
    }

}
