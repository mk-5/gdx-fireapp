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

package pl.mk5.gdx.fireapp.ios.crash;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import apple.foundation.NSArray;
import bindings.fabric.fabric.Fabric;
import bindings.google.crashlytics.Crashlytics;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;

@PrepareForTest({Fabric.class, Crashlytics.class, bindings.google.crashlytics.c.Crashlytics.class, NatJ.class, NSArray.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrashTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(Fabric.class);
        PowerMockito.mockStatic(Crashlytics.class);
        PowerMockito.mockStatic(bindings.google.crashlytics.c.Crashlytics.class);
        PowerMockito.mockStatic(NSArray.class);
        NSArray nsArray = PowerMockito.mock(NSArray.class);
        Crashlytics crashlytics = PowerMockito.mock(Crashlytics.class);
        Mockito.when(Crashlytics.alloc()).thenReturn(crashlytics);
        Mockito.when(NSArray.arrayWithObject(Mockito.any())).thenReturn(nsArray);
    }

    @Test
    public void initialize() {
        // Given
        Crash crash = new Crash();

        // When
        crash.initialize();
        crash.initialize();
        crash.initialize();

        // Then
        PowerMockito.verifyStatic(Fabric.class, VerificationModeFactory.times(1)); // only one time
        Fabric.with(Mockito.any(NSArray.class));
    }

    @Test
    public void log() {
        // Given
        Crash crash = new Crash();

        // When
        crash.log("test");

        // Then
        PowerMockito.verifyStatic(bindings.google.crashlytics.c.Crashlytics.class, VerificationModeFactory.times(1)); // only one time
        bindings.google.crashlytics.c.Crashlytics.CLSLogv(Mockito.eq("test"), Mockito.any());
    }
}