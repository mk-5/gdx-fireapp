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
import com.badlogic.gdx.utils.reflect.ClassReflection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.List;
import java.util.Map;

@PrepareForTest({JsonListMapDeserializer.class, ClassReflection.class})
public class JsonProcessorTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private Json json;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(JsonListMapDeserializer.class);
        PowerMockito.whenNew(JsonListMapDeserializer.class).withAnyArguments().thenReturn(PowerMockito.mock(JsonListMapDeserializer.class));
        PowerMockito.mockStatic(ClassReflection.class);
        Mockito.when(ClassReflection.isAssignableFrom(Mockito.any(Class.class), Mockito.any(Class.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return ((Class) invocation.getArgument(0)).isAssignableFrom((Class) invocation.getArgument(1));
            }
        });
        Mockito.when(ClassReflection.newInstance(Mockito.any(Class.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return ((Class) invocation.getArgument(0)).newInstance();
            }
        });
    }

    @Test
    public void process_withList() throws Exception {
        // Given
        Class wantedType = List.class;
        String jsonString = "[]";

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof List);
    }

    @Test
    public void process_withMap() throws Exception {
        // Given
        Class wantedType = Map.class;
        String jsonString = "{ }";

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Map);
    }

    @Test
    public void process_withAnyValue() throws Exception {
        // Given
        Class wantedType = String.class;
        String jsonString = "test";

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        Assert.assertEquals("test", result);
    }

    @Test
    public void process_withAnyValue2() throws Exception {
        // Given
        Class wantedType = Double.class;
        String jsonString = "2.0";

        // When
        Object result = JsonProcessor.process(wantedType, jsonString);

        // Then
        Assert.assertEquals(2.0, result);
    }
}