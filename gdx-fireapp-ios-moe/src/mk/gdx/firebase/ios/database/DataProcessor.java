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

package mk.gdx.firebase.ios.database;

import org.moe.natj.objc.ObjCRuntime;

import java.util.List;
import java.util.Map;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSNull;
import apple.foundation.NSNumber;
import apple.foundation.NSString;
import mk.gdx.firebase.ios.exceptions.ConvertingException;
import mk.gdx.firebase.ios.helpers.GenericPlaceholder;
import mk.gdx.firebase.ios.helpers.MapDeserializer;
import mk.gdx.firebase.ios.helpers.NSArrayHelper;
import mk.gdx.firebase.ios.helpers.NSDictionaryHelper;
import mk.gdx.firebase.ios.helpers.NSNumberHelper;

/**
 * Is responsible for transform ios objects to normal Java objects.
 * <p>
 * Following transformations are going on (in both direction):
 * <ul>
 * <li>{@link NSNumber} to {@link Number} or {@link Boolean}
 * <li>{@link NSString} to {@link String}
 * <li>{@link NSArray} to {@link List}
 * <li>{@link NSDictionary} to {@link Map} or to POJO object
 * </ul>
 * <p>
 * In case of transform from {@link NSDictionary} to POJO object,
 * {@link NSDictionary} is transformed to Json string then it is transformed by {@link com.badlogic.gdx.utils.Json#fromJson(Class, String)}
 */
public class DataProcessor
{

    /**
     * Transforms ios object to java with preserved GenericType.
     *
     * @param iosObject          iOS object, in most cases instance of {@link NSObject}
     * @param genericPlaceholder {@link GenericPlaceholder}
     * @param <T>                Type of object which you want to get - needed by transforming types flow.
     * @return {@code iosObject} java representation. For ex. {@link NSString} was transformed to {@link String}
     */
    @SuppressWarnings("unchecked")
    public static <T> T iosDataToJava(Object iosObject, GenericPlaceholder genericPlaceholder)
    {
        Class resultType = iosObject.getClass();
        Class<T> wantedType = (Class<T>) genericPlaceholder.getGenericType();
        T result = null;
        try {
            if (resultType == NSString.class && wantedType == String.class) {
                result = processPrimitiveData(iosObject, wantedType);
            } else if (resultType == NSNumber.class && (Number.class.isAssignableFrom(wantedType) || wantedType == Boolean.class)) { // TODO - check isInstance
                result = processPrimitiveData(iosObject, wantedType);
            } else if (NSArray.class.isAssignableFrom(resultType) && List.class.isAssignableFrom(wantedType)) {
                // T is assignable from list
                result = (T) NSArrayHelper.toList((NSArray) iosObject);
                Class listGenericType = genericPlaceholder.getGenericGenericType();
                // If wanted List type has own generic and that type is not assignable from Map.
                if (listGenericType != null && !Map.class.isAssignableFrom(listGenericType))
                    // Go through all list elements get try to convert elements to wanted List generic type.
                    for (int i = 0; i < ((List) result).size(); i++) {
                        Object o = ((List) result).get(i);
                        // If List element is assignable from Map do deserialization and cast object to T.
                        // Every class-object was retrieved from DB as NSDictionary(Map), so we need to do this processing manually.
                        if (Map.class.isAssignableFrom(o.getClass())) {
                            T deserialized = (T) MapDeserializer.deserialize((Map) o, listGenericType);
                            // If deserialized was succeed - change HashMap to deserialized object
                            if (deserialized != null) {
                                ((List) result).set(i, deserialized);
                            }
                        }
                    }

            } else if (NSDictionary.class.isAssignableFrom(resultType)) {
                // Few scenarios
                // 1. Create Map<String, Object>
                // 2. Create class
                Map map = NSDictionaryHelper.toMap((NSDictionary<NSString, NSObject>) iosObject);
                if (!Map.class.isAssignableFrom(wantedType)) {
                    T deserialized = (T) MapDeserializer.deserialize(map, wantedType);
                    if (deserialized != null)
                        return deserialized;
                }
                result = (T) map;
            } else {
                throw new ConvertingException("Result data type mismatch. Wanted type: " + wantedType
                        + ", result data type: " + resultType);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    /**
     * Transforms ios object to java primitive type object.
     * TODO - better docs.
     *
     * @param data       Object instance to be transformed.
     * @param wantedType Type of object which should be returned after transformation.
     * @param <T>        Generic type of wanted data.
     * @return Transformed object.  If {@link NSObject} instance has passed object was transformed - otherwise {@code data} itself was returned.
     */
    @SuppressWarnings("unchecked")
    public static <T> T processPrimitiveData(Object data, Class<T> wantedType)
    {
        T result = null;
        if (data.getClass() == wantedType) {
            return (T) data;
        }
        if (data instanceof NSNumber) {
            if (wantedType == Long.class) {
                result = (T) Long.valueOf(((NSNumber) data).longValue());
            } else if (wantedType == Integer.class) {
                result = (T) Integer.valueOf(((NSNumber) data).intValue());
            } else if (wantedType == Float.class) {
                result = (T) Float.valueOf(((NSNumber) data).floatValue());
            } else if (wantedType == Double.class) {
                result = (T) Double.valueOf(((NSNumber) data).doubleValue());
            } else if (wantedType == Boolean.class) {
                result = (T) Boolean.valueOf(((NSNumber) data).boolValue());
            }
        } else if (data instanceof NSString) {
            result = (T) ObjCRuntime.createJavaString(((NSString) data).getPeerPointer());
        } else {
            throw new ConvertingException("" + data.getClass() + " is not primitive data");
        }
        return result;
    }

    /**
     * Transforms ios object to java.
     *
     * @param iosObject Any java object got by multi-os-engine classes. In most cases it should be instance of {@link NSObject}
     * @return Java object which is equals to multi-os-engine object representation.
     */
    @SuppressWarnings("unchecked")
    public static Object iosDataToJava(Object iosObject)
    {
        if (iosObject instanceof NSString) {
            return DataProcessor.processPrimitiveData(iosObject, String.class);
        } else if (iosObject instanceof NSNumber) {
            return NSNumberHelper.getNSNumberPrimitive((NSNumber) iosObject);
        } else if (iosObject instanceof NSArray) {
            return NSArrayHelper.toList((NSArray) iosObject);
        } else if (iosObject instanceof NSDictionary) {
            return NSDictionaryHelper.toMap((NSDictionary<NSString, NSObject>) iosObject);
        }
        return iosObject;
    }

    /**
     * Transforms java object to ios.
     *
     * @param javaObject Any java object instance.
     * @return {@link NSObject} instance equals to {@code javaObject}.
     */
    @SuppressWarnings("unchecked")
    public static NSObject javaDataToIos(Object javaObject)
    {
        if (javaObject instanceof String) {
            return NSString.alloc().initWithString((String) javaObject);
        } else if (javaObject instanceof Boolean) {
            return NSNumber.numberWithBool((Boolean) javaObject);
        } else if (javaObject instanceof Integer) {
            return NSNumber.numberWithInt((Integer) javaObject);
        } else if (javaObject instanceof Long) {
            return NSNumber.numberWithLong((Long) javaObject);
        } else if (javaObject instanceof Float) {
            return NSNumber.numberWithFloat((Float) javaObject);
        } else if (javaObject instanceof Double) {
            return NSNumber.numberWithDouble((Double) javaObject);
        } else if (javaObject instanceof java.util.List) {
            return NSArrayHelper.toArray((java.util.List) javaObject);
        } else {
            // Every other value try to serialize to Map.
            try {
                return NSDictionaryHelper.toNSDictionary(javaObject);
            } catch (Exception e) {
                return NSNull.alloc().init();
            }
        }
    }
}
