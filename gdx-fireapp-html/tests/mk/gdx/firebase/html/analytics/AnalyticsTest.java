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
package mk.gdx.firebase.html.analytics;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.HashMap;

import mk.gdx.firebase.html.GdxHtmlAppTest;
import mk.gdx.firebase.html.firebase.ScriptRunner;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, ScriptRunner.class})
public class AnalyticsTest extends GdxHtmlAppTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(ScriptRunner.class);
    }

    @Test
    public void logEvent() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.logEvent("test", new HashMap<String, String>());

        // Then
        PowerMockito.verifyStatic(ScriptRunner.class, VerificationModeFactory.times(0));
        ScriptRunner.firebaseScript(Mockito.any(Runnable.class));
    }

    @Test
    public void setScreen() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setScreen("test", AnalyticsTest.class);

        // Then
        PowerMockito.verifyStatic(ScriptRunner.class, VerificationModeFactory.times(0));
        ScriptRunner.firebaseScript(Mockito.any(Runnable.class));
    }

    @Test
    public void setUserProperty() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserProperty("test", "test_value");

        // Then
        PowerMockito.verifyStatic(ScriptRunner.class, VerificationModeFactory.times(0));
        ScriptRunner.firebaseScript(Mockito.any(Runnable.class));
    }

    @Test
    public void setUserId() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserId("user_id");

        // Then
        PowerMockito.verifyStatic(ScriptRunner.class, VerificationModeFactory.times(0));
        ScriptRunner.firebaseScript(Mockito.any(Runnable.class));
    }
}