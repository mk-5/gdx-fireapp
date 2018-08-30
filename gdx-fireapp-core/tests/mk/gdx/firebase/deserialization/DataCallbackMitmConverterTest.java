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
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.callbacks.DataCallback;

@RunWith(PowerMockRunner.class)
public class DataCallbackMitmConverterTest {

    @Test
    public void get() {
        // Given
        DataCallback dataCallback = Mockito.mock(DataCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataCallbackMitmConverter stringCallbackMitmConverter = new DataCallbackMitmConverter(String.class, dataCallback, firebaseMapConverter);
        DataCallbackMitmConverter genericCallbackMitmConverter = new DataCallbackMitmConverter(DataCallbackMitmConverterTest.class, dataCallback, firebaseMapConverter);

        // When
        DataCallback stringResultCallback = stringCallbackMitmConverter.get();
        DataCallback typeResultCallback = genericCallbackMitmConverter.get();

        // Then
        Assert.assertTrue(stringResultCallback.getClass().getSimpleName().equals("GenericDataCallback"));
        Assert.assertTrue(typeResultCallback.getClass().getSimpleName().equals("PojoDataCallback"));
    }

    @Test
    public void getPojoDataCallback() {
        // Given
        DataCallback dataCallback = Mockito.mock(DataCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataCallbackMitmConverter dataCallbackMitmConverter = new DataCallbackMitmConverter(String.class, dataCallback, firebaseMapConverter);

        // When
        DataCallback resultCallback = dataCallbackMitmConverter.getPojoDataCallback();

        // Then
        Assert.assertTrue(resultCallback.getClass().getSimpleName().equals("PojoDataCallback"));
    }

    @Test
    public void getGenericDataCallback() {
        // Given
        DataCallback dataCallback = Mockito.mock(DataCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        DataCallbackMitmConverter dataCallbackMitmConverter = new DataCallbackMitmConverter(DataCallbackMitmConverterTest.class, dataCallback, firebaseMapConverter);

        // When
        DataCallback resultCallback = dataCallbackMitmConverter.getGenericDataCallback();

        // Then
        Assert.assertTrue(resultCallback.getClass().getSimpleName().equals("GenericDataCallback"));
    }

    @Test
    public void onData() {
        // Given
        DataCallback dataCallback = Mockito.mock(TestCallbackWithMapConversion.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(new MyClass());
        DataCallbackMitmConverter dataCallbackMitmConverter = new DataCallbackMitmConverter(MyClass.class, dataCallback, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");

        // When
        dataCallbackMitmConverter.onData(rawData);

        // Then
        Mockito.verify(dataCallback).onData(Mockito.any(MyClass.class));
    }

    @Test
    public void onData_singleValueList() {
        // Given
        DataCallback dataCallback = Mockito.mock(TestCallbackWithMapConversionAndList.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        MyClass myClass = new MyClass();
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(myClass);
        DataCallbackMitmConverter dataCallbackMitmConverter = new DataCallbackMitmConverter(List.class, dataCallback, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");
        List<MyClass> expectedList = Collections.singletonList(myClass);

        // When
        dataCallbackMitmConverter.onData(rawData);

        // Then
        Mockito.verify(dataCallback).onData(Mockito.eq(expectedList));
    }

    @Test(expected = ClassCastException.class)
    public void onData_withoutMapConversion() {
        // Given
        DataCallback dataCallback = Mockito.mock(TestCallbackWithoutMapConversion.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        Mockito.when(firebaseMapConverter.convert(Mockito.any(Map.class), Mockito.eq(MyClass.class))).thenReturn(new MyClass());
        DataCallbackMitmConverter dataCallbackMitmConverter = new DataCallbackMitmConverter(MyClass.class, dataCallback, firebaseMapConverter);
        Map rawData = Collections.singletonMap("field", "test");

        // When
        dataCallbackMitmConverter.onData(rawData);

        // Then exception
    }

    @Test
    public void onError() {
    }

    public class TestCallbackWithMapConversionAndList implements DataCallback<List<MyClass>> {

        @Override
        @MapConversion(MyClass.class)
        public void onData(List data) {

        }

        @Override
        public void onError(Exception e) {

        }
    }

    public class TestCallbackWithMapConversion implements DataCallback<MyClass> {

        @Override
        @MapConversion(MyClass.class)
        public void onData(MyClass data) {

        }

        @Override
        public void onError(Exception e) {

        }
    }

    public class TestCallbackWithoutMapConversion implements DataCallback<MyClass> {

        @Override
        public void onData(MyClass data) {

        }

        @Override
        public void onError(Exception e) {

        }
    }

    public class MyClass {
        public String field;
    }
}