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

package pl.mk5.gdx.fireapp.html;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import pl.mk5.gdx.fireapp.html.firebase.FirebaseConfigParser;
import pl.mk5.gdx.fireapp.html.firebase.FirebaseConfiguration;

@PrepareForTest({FirebaseConfiguration.class, FirebaseConfigParser.class, ClassReflection.class, Constructor.class, ScriptInjector.class})
public class AppTest extends GdxHtmlAppTest {

    @Test
    public void configure() throws Exception {
        // Given
        App app = new App();
        FirebaseConfigParser configParser = PowerMockito.mock(FirebaseConfigParser.class);
        PowerMockito.whenNew(FirebaseConfigParser.class).withAnyArguments().thenReturn(configParser);
        ScriptInjector.FromUrl fromUrl = PowerMockito.mock(ScriptInjector.FromUrl.class);
        Mockito.when(ScriptInjector.fromUrl(Mockito.nullable(String.class))).thenReturn(fromUrl);
        Mockito.when(fromUrl.setCallback(Mockito.nullable(Callback.class))).thenReturn(fromUrl);
        Mockito.when(fromUrl.setRemoveTag(Mockito.anyBoolean())).thenReturn(fromUrl);
        Mockito.when(fromUrl.setWindow(Mockito.nullable(JavaScriptObject.class))).thenReturn(fromUrl);

        // When
        app.configure();

        // Then
        PowerMockito.verifyNew(FirebaseConfigParser.class, VerificationModeFactory.times(1)).withArguments(Mockito.anyString());
        Mockito.verify(configParser, VerificationModeFactory.times(1)).getFirebaseScriptSrc();
    }
}