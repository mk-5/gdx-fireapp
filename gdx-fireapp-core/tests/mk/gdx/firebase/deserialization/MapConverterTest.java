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

package mk.gdx.firebase.deserialization;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class MapConverterTest {

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

    public static class TestClass {
        public String name;
    }
}