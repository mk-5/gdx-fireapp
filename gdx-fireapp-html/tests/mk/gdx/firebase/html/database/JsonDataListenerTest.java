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

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.html.GdxHtmlAppTest;
import mk.gdx.firebase.listeners.DataChangeListener;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, JsonProcessor.class})
public class JsonDataListenerTest extends GdxHtmlAppTest {

    @Test
    public void onChange() {
        // Given
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        JsonDataListener<String> jsonDataListener = new JsonDataListener<>(String.class, listener);
        String data = "test";
        PowerMockito.mockStatic(JsonProcessor.class);
        Mockito.when(JsonProcessor.process(Mockito.any(Class.class), Mockito.anyString())).thenReturn(data);

        // When
        jsonDataListener.onChange(data);

        // Then
        Mockito.verify(listener, VerificationModeFactory.times(1)).onChange(Mockito.eq("test"));
        PowerMockito.verifyStatic(JsonProcessor.class, VerificationModeFactory.times(1));
        JsonProcessor.process(Mockito.eq(String.class), Mockito.eq(data));
    }

    @Test
    public void onCanceled() {
        // Given
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        JsonDataListener<String> jsonDataListener = new JsonDataListener<>(String.class, listener);
        Exception exception = Mockito.mock(Exception.class);

        // When
        jsonDataListener.onCanceled(exception);

        // Then
        Mockito.verify(listener, VerificationModeFactory.times(1)).onCanceled(Mockito.refEq(exception));
    }
}