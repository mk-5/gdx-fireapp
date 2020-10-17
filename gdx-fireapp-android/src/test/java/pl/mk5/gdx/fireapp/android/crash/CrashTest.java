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
package pl.mk5.gdx.fireapp.android.crash;

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.HashMap;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest({FirebaseCrashlytics.class, GdxNativesLoader.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CrashTest extends AndroidContextTest {

    @Test(expected = Exception.class)
    public void shouldThrowExceptionWhenInvalidKey() {
        // Given
        PowerMockito.mockStatic(FirebaseCrashlytics.class);
        when(FirebaseCrashlytics.getInstance()).thenReturn(mock(FirebaseCrashlytics.class));
        Crash crash = new Crash();

        // When
        crash.setCustomKey("key", new HashMap<>());

        // Then - no exceptions
        Assert.fail();
    }

    @Test
    public void shouldRunMethodsWithoutException() {
        // Given
        PowerMockito.mockStatic(FirebaseCrashlytics.class);
        when(FirebaseCrashlytics.getInstance()).thenReturn(mock(FirebaseCrashlytics.class));
        Crash crash = new Crash();

        // When
        crash.recordException(new IllegalArgumentException());
        crash.log("abc");
        crash.setUserId("Abc");
        crash.setCustomKey("key", true);
        crash.setCustomKey("key", 1);
        crash.setCustomKey("key", 1L);
        crash.setCustomKey("key", 1.0);
        crash.setCustomKey("key", 1f);
        crash.setCustomKey("key", "abc");

        // Then - no exceptions
        Assert.assertTrue(true);
    }
}