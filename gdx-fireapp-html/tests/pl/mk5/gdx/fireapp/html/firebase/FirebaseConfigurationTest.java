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
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import pl.mk5.gdx.fireapp.html.GdxHtmlAppTest;

public class FirebaseConfigurationTest extends GdxHtmlAppTest {

    @Test
    public void load() {
        // Given
        Mockito.when(configFileMock.exists()).thenReturn(true);
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());

        // When
        firebaseConfiguration.load();

        // Then
        Assert.assertNotNull(Whitebox.getInternalState(firebaseConfiguration, "rawHtml"));
        Assert.assertNotNull(Whitebox.getInternalState(firebaseConfiguration, "configParser"));
        Mockito.verify(Gdx.app, VerificationModeFactory.times(0)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void load_fail() {
        // Given
        Mockito.when(configFileMock.exists()).thenReturn(false);
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());

        // When
        firebaseConfiguration.load();

        // Then
        Assert.assertNull(Whitebox.getInternalState(firebaseConfiguration, "rawHtml"));
        Mockito.verify(Gdx.app, VerificationModeFactory.times(1)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void init() {
        // Given
        Mockito.when(configFileMock.exists()).thenReturn(true);
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());

        // When
        firebaseConfiguration.load().init();

        // Then
        PowerMockito.verifyStatic(ScriptInjector.class, VerificationModeFactory.times(1));
        ScriptInjector.fromUrl(Mockito.anyString());
    }

    @Test
    public void init_fail() {
        // Given
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());

        // When
        firebaseConfiguration.init();

        // Then
        Assert.assertNull(Whitebox.getInternalState(firebaseConfiguration, "rawHtml"));
        Mockito.verify(Gdx.app, VerificationModeFactory.times(1)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getConfigParser() {
        // Given
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());
        Whitebox.setInternalState(firebaseConfiguration, "configParser", Mockito.mock(FirebaseConfigParser.class));

        // When
        FirebaseConfigParser parser = firebaseConfiguration.getConfigParser();

        // Then
        Assert.assertNotNull(parser);
    }

    @Test
    public void getConfigParser_withoutParser() {
        // Given
        FirebaseConfiguration firebaseConfiguration = Mockito.spy(new FirebaseConfiguration());

        // When
        FirebaseConfigParser parser = firebaseConfiguration.getConfigParser();

        // Then
        Assert.assertNull(parser);
    }
}