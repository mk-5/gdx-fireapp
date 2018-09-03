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

package mk.gdx.firebase.html;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.html.firebase.FirebaseConfiguration;

@PrepareForTest({FirebaseConfiguration.class, ClassReflection.class, Constructor.class, ScriptInjector.class, App.class})
public class AppTest extends GdxHtmlAppTest {

    @Test
    public void configure() throws Exception {
        // Given
        App app = new App();
        FirebaseConfiguration firebaseConfiguration = PowerMockito.spy(new FirebaseConfiguration());
        PowerMockito.whenNew(FirebaseConfiguration.class).withNoArguments().thenReturn(firebaseConfiguration);

        // When
        app.configure();

        // Then
        PowerMockito.verifyNew(FirebaseConfiguration.class, VerificationModeFactory.times(1)).withNoArguments();
        Mockito.verify(firebaseConfiguration, VerificationModeFactory.times(1)).load();
        Mockito.verify(firebaseConfiguration, VerificationModeFactory.times(1)).init();
    }
}