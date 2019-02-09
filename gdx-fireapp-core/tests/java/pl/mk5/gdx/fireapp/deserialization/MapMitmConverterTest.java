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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.exceptions.MapConversionNotPossibleException;

public class MapMitmConverterTest {

    @Test
    public void doMitmConversion_map() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };
        Map map = Collections.singletonMap("field", "test");
        Class wantedType = MyClass.class;

        // When
        Object object1 = mapMitmConverter.doMitmConversion(wantedType, map);

        // Then
        Assert.assertNotNull(mapConverter);
        Mockito.verify(mapConverter, VerificationModeFactory.times(1)).convert(Mockito.refEq(map), Mockito.eq(wantedType));
    }

    @Test
    public void doMitmConversion_list() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };
        List list = new ArrayList();
        list.add(Collections.singletonMap("field", "test"));
        list.add(Collections.singletonMap("field", "test2"));
        list.add(Collections.singletonMap("field", "test3"));
        list.add("wrong_value_convert_should_drop_it");
        list.add(Collections.singletonMap("field", "test4"));
        Class wantedType = MyClass.class;

        // When
        Object object1 = mapMitmConverter.doMitmConversion(wantedType, list);

        // Then
        Assert.assertNotNull(mapConverter);
        Mockito.verify(mapConverter, VerificationModeFactory.times(4)).convert(Mockito.any(Map.class), Mockito.eq(wantedType));
    }

    @Test
    public void doMitmConversion_null() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };

        // When
        Object object1 = mapMitmConverter.doMitmConversion(MyClass.class, null);

        // Then
        Assert.assertNull(object1);
    }

    @Test(expected = MapConversionNotPossibleException.class)
    public void doMitmConversion_wrongInputData() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };

        // When
        Object object1 = mapMitmConverter.doMitmConversion(MyClass.class, "abc");

        // Then exception
        Assert.fail();
    }

    @Test
    public void isConversionPossible() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };

        // When
        boolean isPossibleOk = mapMitmConverter.isConversionPossible(new HashMap<>());
        boolean isPossibleOk2 = mapMitmConverter.isConversionPossible(new ArrayList<>());
        boolean isPossibleWrong = mapMitmConverter.isConversionPossible("");

        // Then
        Assert.assertTrue(isPossibleOk);
        Assert.assertTrue(isPossibleOk2);
        Assert.assertFalse(isPossibleWrong);
    }

    @Test
    public void isPojo() {
        // Given
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        Class typeClass = MapMitmConverterTest.class;
        Class javaClass = String.class;
        MapMitmConverter mapMitmConverter = new MapMitmConverter(mapConverter) {
        };

        // When
        boolean isPojoType = mapMitmConverter.isPojo(typeClass);
        boolean isPojoString = mapMitmConverter.isPojo(javaClass);

        // Then
        Assert.assertTrue(isPojoType);
        Assert.assertFalse(isPojoString);
    }

    public class MyClass {
        public String field;
    }
}