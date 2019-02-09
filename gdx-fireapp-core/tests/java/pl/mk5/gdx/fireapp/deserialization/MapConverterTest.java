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

package pl.mk5.gdx.fireapp.deserialization;

import com.badlogic.gdx.utils.Json;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.io.StringWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

@PrepareForTest({GdxFIRLogger.class, Json.class, StringWriter.class})
public class MapConverterTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(GdxFIRLogger.class);
    }

    @Test
    public void convert() {
        // Given
        MapConverter mapConverter = new MapConverter();
        Map map = Collections.singletonMap("name", "bob");

        // When
        Object result = mapConverter.convert(map, TestClass.class);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof TestClass);
        Assert.assertEquals("bob", ((TestClass) result).name);
    }

    @Test
    public void convert_exception() throws Exception {
        // Given
        PowerMockito.mockStatic(StringWriter.class);
        PowerMockito.whenNew(StringWriter.class).withAnyArguments().thenReturn(Mockito.mock(StringWriter.class));
        MapConverter mapConverter = new MapConverter();
        Map map = new HashMap();
        map.put("name", "bob");

        // When
        Object result = mapConverter.convert(map, TestClass.class);

        // Then
        PowerMockito.verifyNew(StringWriter.class).withNoArguments();
        PowerMockito.verifyStatic(GdxFIRLogger.class, VerificationModeFactory.times(1));
        GdxFIRLogger.error(Mockito.anyString(), Mockito.any(Exception.class));
    }

    @Test
    public void unConvert() {
        // Given
        MapConverter mapConverter = new MapConverter();
        TestClass testClass = new TestClass();
        testClass.name = "fred";

        // When
        Object result = mapConverter.unConvert(testClass);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof Map);
        Assert.assertEquals("fred", ((Map) result).get("name"));
    }

    @Test
    public void unConvert_exception() {
        // Given
        MapConverter mapConverter = new MapConverter();
        String testString = "";

        // When
        Object result = mapConverter.unConvert(testString);

        // Then
        PowerMockito.verifyStatic(GdxFIRLogger.class, VerificationModeFactory.times(1));
        GdxFIRLogger.error(Mockito.anyString(), Mockito.any(Exception.class));
    }

    public static class TestClass {
        public String name;
    }
}