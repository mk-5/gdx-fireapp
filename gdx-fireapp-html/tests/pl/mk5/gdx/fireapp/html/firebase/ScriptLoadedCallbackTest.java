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

package pl.mk5.gdx.fireapp.html.firebase;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import pl.mk5.gdx.fireapp.html.GdxHtmlAppTest;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, FirebaseJS.class, FirebaseScriptInformant.class})
public class ScriptLoadedCallbackTest extends GdxHtmlAppTest {

    @Test
    public void onFailure() {
        // Given
        ScriptLoadedCallback callback = new ScriptLoadedCallback(Mockito.mock(FirebaseConfigParser.class));

        // When
        callback.onFailure(new Exception());

        // Then
        Mockito.verify(Gdx.app).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void onSuccess() {
        // Given
        PowerMockito.mockStatic(FirebaseJS.class);
        PowerMockito.mockStatic(FirebaseScriptInformant.class);
        Mockito.when(FirebaseScriptInformant.isFirebaseScriptLoaded()).thenReturn(false);
        FirebaseConfigParser parser = Mockito.mock(FirebaseConfigParser.class);
        Mockito.when(parser.getInitializationScript()).thenReturn("");
        ScriptLoadedCallback callback = new ScriptLoadedCallback(parser);

        // When
        callback.onSuccess(null);

        // Then
        PowerMockito.verifyStatic(FirebaseJS.class, VerificationModeFactory.times(1));
        FirebaseJS.initializeFirebase(Mockito.anyString());
        PowerMockito.verifyStatic(FirebaseScriptInformant.class, VerificationModeFactory.times(1));
        FirebaseScriptInformant.isFirebaseScriptLoaded();
    }

    @Test
    public void onSuccess_shouldInitializeFirebaseOnlyOnce() throws Exception {
        // Given
        PowerMockito.mockStatic(FirebaseJS.class);
        FirebaseConfigParser parser = Mockito.mock(FirebaseConfigParser.class);
        Mockito.when(parser.getInitializationScript()).thenReturn("");
        PowerMockito.doCallRealMethod().when(FirebaseJS.class, "setFirebaseScriptIsLoaded");
        PowerMockito.doAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                FirebaseJS.setFirebaseScriptIsLoaded();
                return null;
            }
        }).when(FirebaseJS.class, "initializeFirebase", Mockito.anyString());
        ScriptLoadedCallback callback = new ScriptLoadedCallback(parser);
        FirebaseScriptInformant.setIsLoaded(false);

        // When
        callback.onSuccess(null);
        callback.onSuccess(null);
        callback.onSuccess(null);

        // Then
        PowerMockito.verifyStatic(FirebaseJS.class, VerificationModeFactory.times(1));
        FirebaseJS.initializeFirebase(Mockito.anyString());
    }
}