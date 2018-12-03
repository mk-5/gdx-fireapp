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

package mk.gdx.firebase.html.database;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class JsonListMapDeserializerTest {

    @Test
    public void read_withObjectData() {
        // Given
        Json json = Mockito.mock(Json.class);
        JsonValue jsonValue = Mockito.mock(JsonValue.class);
        Mockito.when(jsonValue.isObject()).thenReturn(true);
        Mockito.when(json.fromJson(Mockito.any(Class.class), Mockito.nullable(String.class))).thenReturn("deserialized_object");
        Class jsonObjectType = HashMap.class;
        JsonListMapDeserializer jsonListMapDeserializer = new JsonListMapDeserializer(jsonObjectType);

        // When
        Object result = jsonListMapDeserializer.read(json, jsonValue, JsonListMapDeserializerTest.class); // class has no affection to result

        // Then
        Mockito.verify(json, VerificationModeFactory.times(1)).fromJson(Mockito.eq(jsonObjectType), Mockito.nullable(String.class));
        Assert.assertEquals("deserialized_object", result);
    }

    @Test
    public void read_withPrimitive() {
        // Given
        Json json = Mockito.mock(Json.class);
        JsonValue booleanValue = Mockito.mock(JsonValue.class);
        Mockito.when(booleanValue.isValue()).thenReturn(true);
        Mockito.when(booleanValue.isBoolean()).thenReturn(true);
        Mockito.when(booleanValue.asBoolean()).thenReturn(true);
        JsonValue stringValue = Mockito.mock(JsonValue.class);
        Mockito.when(stringValue.isValue()).thenReturn(true);
        Mockito.when(stringValue.isString()).thenReturn(true);
        Mockito.when(stringValue.asString()).thenReturn("test_value");
        JsonValue longValue = Mockito.mock(JsonValue.class);
        Mockito.when(longValue.isValue()).thenReturn(true);
        Mockito.when(longValue.isLong()).thenReturn(true);
        Mockito.when(longValue.asLong()).thenReturn(3L);
        JsonValue doubleValue = Mockito.mock(JsonValue.class);
        Mockito.when(doubleValue.isValue()).thenReturn(true);
        Mockito.when(doubleValue.isDouble()).thenReturn(true);
        Mockito.when(doubleValue.asDouble()).thenReturn(2.0);
        JsonValue nullValue = Mockito.mock(JsonValue.class);
        Mockito.when(nullValue.isValue()).thenReturn(true);
        Mockito.when(nullValue.isNull()).thenReturn(true);
        Class jsonObjectType = HashMap.class;
        JsonListMapDeserializer jsonListMapDeserializer = new JsonListMapDeserializer(jsonObjectType);

        // When
        Object booleanResult = jsonListMapDeserializer.read(json, booleanValue, JsonListMapDeserializerTest.class); // class has no affection to result
        Object stringResult = jsonListMapDeserializer.read(json, stringValue, JsonListMapDeserializerTest.class);
        Object longResult = jsonListMapDeserializer.read(json, longValue, JsonListMapDeserializerTest.class);
        Object doubleResult = jsonListMapDeserializer.read(json, doubleValue, JsonListMapDeserializerTest.class);
        Object nullResult = jsonListMapDeserializer.read(json, nullValue, JsonListMapDeserializerTest.class);

        // Then
        Assert.assertEquals(true, booleanResult);
        Assert.assertEquals("test_value", stringResult);
        Assert.assertEquals(3L, longResult);
        Assert.assertEquals(2.0, doubleResult);
        Assert.assertEquals(null, nullResult);
    }
}