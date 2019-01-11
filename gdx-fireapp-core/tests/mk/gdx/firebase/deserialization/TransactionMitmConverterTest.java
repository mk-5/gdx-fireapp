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
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mk.gdx.firebase.annotations.MapConversion;
import mk.gdx.firebase.callbacks.TransactionCallback;
import mk.gdx.firebase.exceptions.MapConversionNotPossibleException;
import mk.gdx.firebase.reflection.AnnotationFinder;

@PrepareForTest({AnnotationFinder.class})
public class TransactionMitmConverterTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    public void get() {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter stringCallbackMitmConverter = new TransactionMitmConverter(String.class, callback, firebaseMapConverter);
        TransactionMitmConverter genericCallbackMitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, firebaseMapConverter);

        // When
        TransactionCallback stringResultCallback = stringCallbackMitmConverter.get();
        TransactionCallback typeResultCallback = genericCallbackMitmConverter.get();

        // Then
        Assert.assertTrue(stringResultCallback.getClass().getSimpleName().equals("GenericTransactionCallback"));
        Assert.assertTrue(typeResultCallback.getClass().getSimpleName().equals("PojoTransactionCallback"));
    }

    @Test
    public void getPojoCallback() {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(String.class, callback, firebaseMapConverter);

        // When
        TransactionCallback resultCallback = mitmConverter.getPojoCallback();
        resultCallback.run(Mockito.mock(Map.class));

        // Then
        Assert.assertTrue(resultCallback.getClass().getSimpleName().equals("PojoTransactionCallback"));
    }

    @Test
    public void getGenericCallback() {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, firebaseMapConverter);

        // When
        TransactionCallback resultCallback = mitmConverter.getGenericCallback();
        resultCallback.run(Mockito.mock(Map.class));

        // Then
        Assert.assertTrue(resultCallback.getClass().getSimpleName().equals("GenericTransactionCallback"));
    }

    @Test
    public void run_withoutMapConversionAnnotation() {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, firebaseMapConverter);
        String transactionData = "test";
        Mockito.when(callback.run(Mockito.any())).thenReturn("test");
        PowerMockito.mockStatic(AnnotationFinder.class);

        // When
        Object result = mitmConverter.run(transactionData);

        // Then
        Assert.assertNotNull(result);
        Assert.assertEquals("test", result);
    }

    @Test
    public void run_withMapConversionAnnotation_List() throws Exception {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, mapConverter);
        ArrayList transactionData = new ArrayList();
        transactionData.add("test");
        Mockito.when(callback.run(Mockito.any())).thenReturn("test_callback_value");
        PowerMockito.mockStatic(AnnotationFinder.class);
        MapConversion annotation = Mockito.mock(MapConversion.class);
        Mockito.doReturn(String.class).when(annotation).value();
        PowerMockito.when(AnnotationFinder.class, "getMethodAnnotation", Mockito.any(), Mockito.any()).thenReturn(annotation);

        // When
        Object result = mitmConverter.run(transactionData);

        // Then
        Assert.assertNotNull(result);
        Mockito.verify(mapConverter, VerificationModeFactory.times(1)).unConvert(Mockito.eq("test_callback_value"));
    }

    @Test
    public void run_withMapConversionAnnotation_Map() throws Exception {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter mapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, mapConverter);
        HashMap transactionData = new HashMap();
        transactionData.put("name", "bob");
        Mockito.when(callback.run(Mockito.any())).thenReturn("test_callback_value");
        PowerMockito.mockStatic(AnnotationFinder.class);
        MapConversion annotation = Mockito.mock(MapConversion.class);
        Mockito.doReturn(String.class).when(annotation).value();
        PowerMockito.when(AnnotationFinder.class, "getMethodAnnotation", Mockito.any(), Mockito.any()).thenReturn(annotation);

        // When
        Object result = mitmConverter.run(transactionData);

        // Then
        Assert.assertNotNull(result);
        Mockito.verify(mapConverter, VerificationModeFactory.times(1)).unConvert(Mockito.eq("test_callback_value"));
    }

    @Test(expected = MapConversionNotPossibleException.class)
    public void run_withException() throws Exception {
        // Given
        TransactionCallback callback = Mockito.mock(TransactionCallback.class);
        FirebaseMapConverter firebaseMapConverter = Mockito.mock(FirebaseMapConverter.class);
        TransactionMitmConverter mitmConverter = new TransactionMitmConverter(TransactionMitmConverterTest.class, callback, firebaseMapConverter);
        String transactionData = "test";
        Mockito.when(callback.run(Mockito.any())).thenReturn("test");
        PowerMockito.mockStatic(AnnotationFinder.class);
        MapConversion annotation = Mockito.mock(MapConversion.class);
        Mockito.doReturn(String.class).when(annotation).value();
        PowerMockito.when(AnnotationFinder.class, "getMethodAnnotation", Mockito.any(), Mockito.any()).thenReturn(annotation);

        // When
        Object result = mitmConverter.run(transactionData);

        // Then
        Assert.fail();
    }
}