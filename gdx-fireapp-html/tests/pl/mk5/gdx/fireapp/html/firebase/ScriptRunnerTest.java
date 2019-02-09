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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({FirebaseScriptInformant.class})
public class ScriptRunnerTest {

    @Test
    public void firebaseScript_withFirebaseLoaded() {
        // Given
        PowerMockito.mockStatic(FirebaseScriptInformant.class);
        Mockito.when(FirebaseScriptInformant.isFirebaseScriptLoaded()).thenReturn(true);
        Runnable runnable = Mockito.mock(Runnable.class);

        // When
        ScriptRunner.firebaseScript(runnable);

        // Then
        Mockito.verify(runnable, VerificationModeFactory.times(1)).run();
    }

    @Test
    public void firebaseScript_withoutFirebaseLoaded() {
        // Given
        PowerMockito.mockStatic(FirebaseScriptInformant.class);
        Mockito.when(FirebaseScriptInformant.isFirebaseScriptLoaded()).thenReturn(false);
        Runnable runnable = Mockito.mock(Runnable.class);

        // When
        ScriptRunner.firebaseScript(runnable);

        // Then
        Mockito.verify(runnable, VerificationModeFactory.times(0)).run();
        PowerMockito.verifyStatic(FirebaseScriptInformant.class, VerificationModeFactory.times(1));
        FirebaseScriptInformant.addWaitingAction(Mockito.refEq(runnable));
    }
}