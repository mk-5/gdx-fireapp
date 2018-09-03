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

package mk.gdx.firebase.html.database.json;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PrepareForTest({Json.class, JsonListMapDeserializer.class, ClassReflection.class, JsonProcessor.class})
public class JsonProcessorTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private Json json;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Json.class);
        PowerMockito.mockStatic(JsonListMapDeserializer.class);
        json = PowerMockito.mock(Json.class);
        PowerMockito.whenNew(Json.class).withAnyArguments().thenReturn(json);
        PowerMockito.whenNew(JsonListMapDeserializer.class).withAnyArguments().thenReturn(PowerMockito.mock(JsonListMapDeserializer.class));
        PowerMockito.mockStatic(ClassReflection.class);
        Mockito.when(ClassReflection.isAssignableFrom(Mockito.any(Class.class), Mockito.any(Class.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return ((Class) invocation.getArgument(0)).isAssignableFrom((Class) invocation.getArgument(1));
            }
        });
    }

    @Test
    public void process_withList() throws Exception {
        // Given
        Class wantedType = List.class;
        String jsonString = "test";
        Mockito.when(json.fromJson(Mockito.any(Class.class), Mockito.anyString())).thenReturn("test_result");

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        Assert.assertEquals("test_result", result);
        PowerMockito.verifyNew(JsonListMapDeserializer.class).withArguments(Mockito.any());
        Mockito.verify(json).fromJson(Mockito.eq(ArrayList.class), Mockito.eq(jsonString));
    }

    @Test
    public void process_withMap() throws Exception {
        // Given
        Class wantedType = Map.class;
        String jsonString = "test";
        Mockito.when(json.fromJson(Mockito.any(Class.class), Mockito.anyString())).thenReturn("test_result");

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        PowerMockito.verifyNew(JsonListMapDeserializer.class).withArguments(Mockito.any());
        Mockito.verify(json).fromJson(Mockito.eq(HashMap.class), Mockito.eq(jsonString));
    }

    @Test
    public void process_withAnyValue() throws Exception {
        // Given
        Class wantedType = String.class;
        String jsonString = "test";
        Mockito.when(json.fromJson(Mockito.any(Class.class), Mockito.anyString())).thenReturn("test_result");

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        PowerMockito.verifyNew(JsonListMapDeserializer.class, VerificationModeFactory.times(0)).withArguments(Mockito.any());
        Mockito.verify(json).fromJson(Mockito.eq(String.class), Mockito.eq(jsonString));
    }
}