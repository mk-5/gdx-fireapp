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
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, JsonProcessor.class})
public class JsonDataPromiseTest extends GdxHtmlAppTest {

    @Test
    public void onChange() {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        JsonDataPromise<String> jsonDataPromise = new JsonDataPromise<>(String.class, promise);
        String data = "test";
        PowerMockito.mockStatic(JsonProcessor.class);
        Mockito.when(JsonProcessor.process(Mockito.any(Class.class), Mockito.anyString())).thenReturn(data);

        // When
        jsonDataPromise.doComplete(data);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.eq("test"));
        PowerMockito.verifyStatic(JsonProcessor.class, VerificationModeFactory.times(1));
        JsonProcessor.process(Mockito.eq(String.class), Mockito.eq(data));
    }

    @Test
    public void onCanceled() {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        JsonDataPromise<String> jsonDataPromise = new JsonDataPromise<>(String.class, promise);
        Exception exception = Mockito.mock(Exception.class);

        // When
        jsonDataPromise.doFail(exception);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.refEq(exception));
    }
}