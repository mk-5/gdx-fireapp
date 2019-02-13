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

package pl.mk5.gdx.fireapp.android.analytics;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.HashMap;
import java.util.Map;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;

@PrepareForTest({FirebaseAnalytics.class, GdxNativesLoader.class})
public class AnalyticsTest extends AndroidContextTest {

    private FirebaseAnalytics firebaseAnalytics;

    @Before
    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseAnalytics.class);
        firebaseAnalytics = PowerMockito.mock(FirebaseAnalytics.class);
        Mockito.when(FirebaseAnalytics.getInstance(Mockito.any(Context.class))).thenReturn(firebaseAnalytics);
    }

    @Test
    public void logEvent() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.logEvent("test", new HashMap<String, String>());

        // Then
        PowerMockito.verifyStatic(FirebaseAnalytics.class, VerificationModeFactory.times(1));
        FirebaseAnalytics.getInstance((Context) Gdx.app);
        Mockito.verify(firebaseAnalytics, VerificationModeFactory.times(1)).logEvent(Mockito.eq("test"), Mockito.any(Bundle.class));
        Mockito.verifyNoMoreInteractions(firebaseAnalytics);
    }

    @Test
    public void logEvent_withParams() {
        // Given
        Analytics analytics = new Analytics();
        Map<String, String> params = new HashMap<>();
        params.put("param_string", "lorem ipsum");
        params.put("param_string_starting_with_number", "12:00 hour");
        params.put("param_number_int", "15");
        params.put("param_number_float", "15.54");

        // When
        analytics.logEvent("test", params);

        // Then
        PowerMockito.verifyStatic(FirebaseAnalytics.class, VerificationModeFactory.times(1));
        FirebaseAnalytics.getInstance((Context) Gdx.app);
        Mockito.verify(firebaseAnalytics, VerificationModeFactory.times(1)).logEvent(Mockito.eq("test"), Mockito.any(Bundle.class));
        Mockito.verifyNoMoreInteractions(firebaseAnalytics);
    }

    @Test
    public void setScreen() {
        // Given
        Analytics analytics = new Analytics();
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        }).when(((AndroidApplication) Gdx.app)).runOnUiThread(Mockito.any(Runnable.class));


        // When
        analytics.setScreen("test", AnalyticsTest.class);

        // Then
        PowerMockito.verifyStatic(FirebaseAnalytics.class, VerificationModeFactory.times(1));
        FirebaseAnalytics.getInstance((Context) Gdx.app);
        Mockito.verify(firebaseAnalytics, VerificationModeFactory.times(1))
                .setCurrentScreen(Mockito.eq((Activity) Gdx.app), Mockito.eq("test"), Mockito.eq(AnalyticsTest.class.getSimpleName()));
        Mockito.verifyNoMoreInteractions(firebaseAnalytics);
    }

    @Test
    public void setUserProperty() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserProperty("test_name", "test_value");

        // Then
        PowerMockito.verifyStatic(FirebaseAnalytics.class, VerificationModeFactory.times(1));
        FirebaseAnalytics.getInstance((Context) Gdx.app);
        Mockito.verify(firebaseAnalytics, VerificationModeFactory.times(1))
                .setUserProperty(Mockito.eq("test_name"), Mockito.eq("test_value"));
        Mockito.verifyNoMoreInteractions(firebaseAnalytics);
    }

    @Test
    public void setUserId() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserId("test");

        // Then
        PowerMockito.verifyStatic(FirebaseAnalytics.class, VerificationModeFactory.times(1));
        FirebaseAnalytics.getInstance((Context) Gdx.app);
        Mockito.verify(firebaseAnalytics, VerificationModeFactory.times(1))
                .setUserId(Mockito.eq("test"));
        Mockito.verifyNoMoreInteractions(firebaseAnalytics);
    }
}