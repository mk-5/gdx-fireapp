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
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.listeners.DataChangeListener;

public class DataChangeListenerMitmConverterTest {

    @Test
    public void get() {
        // Given
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataChangeListenerMitmConverter stringMitmConverter = new DataChangeListenerMitmConverter(String.class, listener, firebaseMapConverter);
        DataChangeListenerMitmConverter genericMitmConverter = new DataChangeListenerMitmConverter(DataChangeListenerMitmConverterTest.class, listener, firebaseMapConverter);

        // When
        DataChangeListener stringResult = stringMitmConverter.get();
        DataChangeListener typeResult = genericMitmConverter.get();

        // Then
        Assert.assertTrue(stringResult.getClass().getSimpleName().equals("GenericDataChangeListener"));
        Assert.assertTrue(typeResult.getClass().getSimpleName().equals("PojoDataChangeListener"));
    }

    @Test
    public void getPojoListener() {
        // Given
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(DataChangeListenerMitmConverterTest.class, listener, firebaseMapConverter);
        // When
        DataChangeListener result = mitmConverter.getPojoListener();
        result.onChange(Mockito.mock(Map.class));
        result.onCanceled(Mockito.mock(Exception.class));

        // Then
        Assert.assertTrue(result.getClass().getSimpleName().equals("PojoDataChangeListener"));
    }

    @Test
    public void getGenericListener() {
        // Given
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(DataChangeListenerMitmConverterTest.class, listener, firebaseMapConverter);
        // When
        DataChangeListener result = mitmConverter.getGenericListener();
        result.onChange(Mockito.mock(Map.class));
        result.onCanceled(Mockito.mock(Exception.class));

        // Then
        Assert.assertTrue(result.getClass().getSimpleName().equals("GenericDataChangeListener"));
    }

    @Test
    public void onChange() {
        // Given
        DataChangeListener listener = Mockito.mock(TestListenerWithMapConversion.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(new MyClass());
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(MyClass.class, listener, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");

        // When
        mitmConverter.onChange(rawData);

        // Then
        Mockito.verify(listener).onChange(Mockito.any(MyClass.class));
    }

    @Test
    public void onChange_singleValueList() {
        // Given
        DataChangeListener listener = Mockito.mock(TestListenerWithMapConversionAndList.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        MyClass myClass = new MyClass();
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(myClass);
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(List.class, listener, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");
        List<MyClass> expectedList = Collections.singletonList(myClass);

        // When
        mitmConverter.onChange(rawData);

        // Then
        Mockito.verify(listener).onChange(Mockito.eq(expectedList));
    }

    @Test(expected = ClassCastException.class)
    public void onChange_withoutMapConversion() {
        // Given
        DataChangeListener listener = Mockito.mock(TestListenerWithoutMapConversion.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(new MyClass());
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(MyClass.class, listener, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");

        // When
        mitmConverter.onChange(rawData);

        // Then exception
        Assert.fail();
    }

    @Test
    public void onCanceled() {
        // Given
        DataChangeListener listener = Mockito.mock(TestListenerWithMapConversionAndList.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        MyClass myClass = new MyClass();
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(myClass);
        DataChangeListenerMitmConverter mitmConverter = new DataChangeListenerMitmConverter(List.class, listener, firebaseMapConverter);
        Exception exception = Mockito.mock(Exception.class);

        // When
        mitmConverter.onCanceled(exception);

        // Then
        Mockito.verify(listener).onCanceled(Mockito.refEq(exception));
    }

    public class TestListenerWithMapConversionAndList implements DataChangeListener<List<MyClass>> {

        @Override
        @MapConversion(MyClass.class)
        public void onChange(List<MyClass> newValue) {

        }

        @Override
        public void onCanceled(Exception e) {

        }
    }

    public class TestListenerWithMapConversion implements DataChangeListener<MyClass> {


        @Override
        @MapConversion(MyClass.class)
        public void onChange(MyClass newValue) {

        }

        @Override
        public void onCanceled(Exception e) {

        }
    }

    public class TestListenerWithoutMapConversion implements DataChangeListener<MyClass> {

        @Override
        public void onChange(MyClass newValue) {

        }

        @Override
        public void onCanceled(Exception e) {

        }
    }

    public class MyClass {
        public String field;
    }
}