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
package mk.gdx.firebase.android.crash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.crashlytics.android.Crashlytics;

import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import io.fabric.sdk.android.Fabric;
import mk.gdx.firebase.GdxFIRCrash;
import mk.gdx.firebase.android.AndroidContextTest;

@PrepareForTest({Crashlytics.class, Fabric.class, GdxNativesLoader.class})
public class CrashTest extends AndroidContextTest {

    @Test
    public void initialize() {
        // Given
        PowerMockito.mockStatic(Crashlytics.class);
        PowerMockito.mockStatic(Fabric.class);
        GdxFIRCrash gdxFIRCrash = GdxFIRCrash.instance();

        // When
        gdxFIRCrash.initialize();

        // Then
        PowerMockito.verifyStatic(Fabric.class, VerificationModeFactory.times(1));
        Fabric.with((AndroidApplication) Gdx.app, new Crashlytics());
        PowerMockito.verifyNoMoreInteractions(Fabric.class);
    }

    @Test
    public void log() {
        // Given
        PowerMockito.mockStatic(Crashlytics.class);
        PowerMockito.mockStatic(Fabric.class);
        GdxFIRCrash gdxFIRCrash = GdxFIRCrash.instance();

        // When
        gdxFIRCrash.log("abc");

        // Then
        PowerMockito.verifyStatic(Crashlytics.class, VerificationModeFactory.times(1));
        Crashlytics.log("abc");
        PowerMockito.verifyNoMoreInteractions(Crashlytics.class);
    }
}