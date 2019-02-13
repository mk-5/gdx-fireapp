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

package pl.mk5.gdx.fireapp.ios.database;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.moe.natj.general.NatJ;
import org.moe.natj.objc.ObjCRuntime;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSMutableArray;
import apple.foundation.NSNull;
import apple.foundation.NSNumber;
import apple.foundation.NSString;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, NSString.class, NSNumber.class,
        NSArray.class, NSDictionary.class, NSNull.class, NSMutableArray.class,
        ObjCRuntime.class, NSDictionaryHelper.class, NSNumberHelper.class, NSArrayHelper.class
})
@SuppressStaticInitializationFor({"apple.foundation.NSString"})
public class DataProcessorTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(NSString.class);
        PowerMockito.mockStatic(NSNumber.class);
        PowerMockito.mockStatic(NSArray.class);
        PowerMockito.mockStatic(NSMutableArray.class);
        PowerMockito.mockStatic(NSDictionary.class);
        PowerMockito.mockStatic(ObjCRuntime.class);
        PowerMockito.mockStatic(NSDictionaryHelper.class);
        PowerMockito.mockStatic(NSNumberHelper.class);
        PowerMockito.mockStatic(NSArrayHelper.class);
        Mockito.when(NSDictionaryHelper.toMap(Mockito.any(NSDictionary.class))).thenReturn(Mockito.mock(Map.class));
        Mockito.when(NSDictionaryHelper.toNSDictionary(Mockito.any(Object.class))).thenReturn(Mockito.mock(NSDictionary.class));
        Mockito.when(NSNumberHelper.getNSNumberPrimitive(Mockito.any(NSNumber.class))).thenReturn(Mockito.mock(Number.class));
        Mockito.when(NSArrayHelper.toList(Mockito.any(NSArray.class))).thenReturn(Mockito.mock(List.class));
        Mockito.when(NSArrayHelper.toArray(Mockito.any(List.class))).thenReturn(Mockito.mock(NSMutableArray.class));
    }

    @Test
    public void iosDataToJava_null() {
        // Given
        Object iosObject = Mockito.mock(NSNull.class);
        Class wantedType = String.class;

        // When
        Object result = DataProcessor.iosDataToJava(iosObject, wantedType);

        // Then
        Assert.assertNull(result);
    }

    @Test
    public void iosDataToJava_string() {
        // Given
        Object iosObject = Mockito.mock(NSString.class);
        Class wantedType = String.class;
        PowerMockito.when(ObjCRuntime.createJavaString(Mockito.nullable(Long.class))).thenReturn("test");

        // When
        Object result = DataProcessor.iosDataToJava(iosObject, wantedType);

        // Then
        Assert.assertEquals("test", result);
    }

    @Test
    public void iosDataToJava_boolean() {
        // Given
        NSNumber iosObject = PowerMockito.mock(NSNumber.class);
        Class wantedType = Boolean.class;

        // When
        Object result = DataProcessor.iosDataToJava(iosObject, wantedType);

        // Then
        Assert.assertTrue(result instanceof Boolean);
    }

    @Test
    public void iosDataToJava_number() {
        // Given
        NSNumber iosObject = PowerMockito.mock(NSNumber.class);

        // When
        Object resultLong = DataProcessor.iosDataToJava(iosObject, Long.class);
        Object resultInteger = DataProcessor.iosDataToJava(iosObject, Integer.class);
        Object resultDouble = DataProcessor.iosDataToJava(iosObject, Double.class);
        Object resultFloat = DataProcessor.iosDataToJava(iosObject, Float.class);

        // Then
        Assert.assertTrue(resultLong instanceof Long);
        Assert.assertTrue(resultInteger instanceof Integer);
        Assert.assertTrue(resultDouble instanceof Double);
        Assert.assertTrue(resultFloat instanceof Float);
    }

    @Test
    public void iosDataToJava_sameReturnTypeAsWanted() {
        // Given
        final String data = "abc";

        // When
        final String result = DataProcessor.iosDataToJava(data, String.class);

        // Then
        Assert.assertEquals(data, result);
    }

    @Test
    public void iosDataToJava_array() {
        // Given
        NSArray iosObject = PowerMockito.mock(NSArray.class);
        Class wantedType = List.class;

        // When
        Object result = DataProcessor.iosDataToJava(iosObject, wantedType);

        // Then
        Assert.assertTrue(result instanceof List);
    }

    @Test
    public void iosDataToJava_map() {
        // Given
        NSDictionary iosObject = PowerMockito.mock(NSDictionary.class);
        Class wantedType = HashMap.class;

        // When
        Object result = DataProcessor.iosDataToJava(iosObject, wantedType);

        // Then
        Assert.assertTrue(result instanceof Map);
    }

    @Test
    public void iosDataToJava1() {
        // Given
        NSString nsString = PowerMockito.mock(NSString.class);
        NSNumber nsNumber = PowerMockito.mock(NSNumber.class);
        NSArray nsArray = PowerMockito.mock(NSArray.class);
        NSDictionary nsDictionary = PowerMockito.mock(NSDictionary.class);
        PowerMockito.when(ObjCRuntime.createJavaString(Mockito.nullable(Long.class))).thenReturn("test");

        // When
        Object resultString = DataProcessor.iosDataToJava(nsString);
        Object resultNumber = DataProcessor.iosDataToJava(nsNumber);
        Object resultArray = DataProcessor.iosDataToJava(nsArray);
        Object resultDictionary = DataProcessor.iosDataToJava(nsDictionary);

        // Then
        Assert.assertTrue(resultString instanceof String);
        Assert.assertTrue(resultNumber instanceof Number);
        Assert.assertTrue(resultArray instanceof List);
        Assert.assertTrue(resultDictionary instanceof Map);
    }

    @Test
    public void processPrimitiveData_string() {
        // Given
        NSString iosObject = PowerMockito.mock(NSString.class);
        PowerMockito.when(ObjCRuntime.createJavaString(Mockito.nullable(Long.class))).thenReturn("test");

        // When
        Object result = DataProcessor.processPrimitiveData(iosObject, String.class);

        // Then
        Assert.assertTrue(result instanceof String);
    }

    @Test
    public void processPrimitiveData_boolean() {
        // Given
        NSNumber iosObject = PowerMockito.mock(NSNumber.class);

        // When
        Object result = DataProcessor.processPrimitiveData(iosObject, Boolean.class);

        // Then
        Assert.assertTrue(result instanceof Boolean);
    }

    @Test
    public void processPrimitiveData_number() {
        // Given
        NSNumber iosObject = PowerMockito.mock(NSNumber.class);

        // When
        Object resultLong = DataProcessor.processPrimitiveData(iosObject, Long.class);
        Object resultInteger = DataProcessor.processPrimitiveData(iosObject, Integer.class);
        Object resultFloat = DataProcessor.processPrimitiveData(iosObject, Float.class);
        Object resultDouble = DataProcessor.processPrimitiveData(iosObject, Double.class);

        // Then
        Assert.assertTrue(resultLong instanceof Long);
        Assert.assertTrue(resultInteger instanceof Integer);
        Assert.assertTrue(resultDouble instanceof Double);
        Assert.assertTrue(resultFloat instanceof Float);
    }

    @Test
    public void javaDataToIos_string() {
        // Given
        String javaObject = "";
        NSString nsString = Mockito.mock(NSString.class);
        Mockito.when(NSString.alloc()).thenReturn(nsString);
        Mockito.when(nsString.initWithString(Mockito.anyString())).thenReturn(nsString);

        // When
        Object result = DataProcessor.javaDataToIos(javaObject);

        // Then
        Assert.assertTrue(result instanceof NSString);
    }

    @Test
    public void javaDataToIos_number() {
        // Given
        NSNumber nsNumber = Mockito.mock(NSNumber.class);
        Mockito.when(NSNumber.alloc()).thenReturn(nsNumber);
        Mockito.when(NSNumber.numberWithBool(Mockito.anyBoolean())).thenReturn(nsNumber);
        Mockito.when(NSNumber.numberWithInt(Mockito.anyInt())).thenReturn(nsNumber);
        Mockito.when(NSNumber.numberWithLong(Mockito.anyLong())).thenReturn(nsNumber);
        Mockito.when(NSNumber.numberWithFloat(Mockito.anyFloat())).thenReturn(nsNumber);
        Mockito.when(NSNumber.numberWithDouble(Mockito.anyDouble())).thenReturn(nsNumber);

        // When
        Object resultBoolean = DataProcessor.javaDataToIos(true);
        Object resultLong = DataProcessor.javaDataToIos(5L);
        Object resultInt = DataProcessor.javaDataToIos(3);
        Object resultDouble = DataProcessor.javaDataToIos(4.0);
        Object resultFloat = DataProcessor.javaDataToIos(1f);

        // Then
        Assert.assertTrue(resultBoolean instanceof NSNumber);
        Assert.assertTrue(resultLong instanceof NSNumber);
        Assert.assertTrue(resultInt instanceof NSNumber);
        Assert.assertTrue(resultDouble instanceof NSNumber);
        Assert.assertTrue(resultFloat instanceof NSNumber);
    }

    @Test
    public void javaDataToIos_array() {
        // Given
        List javaObject = Mockito.mock(List.class);

        // When
        Object result = DataProcessor.javaDataToIos(javaObject);

        // Then
        Assert.assertTrue(result instanceof NSArray);
    }

    @Test
    public void javaDataToIos_map() {
        // Given
        Map javaObject = Mockito.mock(Map.class);

        // When
        Object result = DataProcessor.javaDataToIos(javaObject);

        // Then
        Assert.assertTrue(result instanceof NSDictionary);
    }
}