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

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;

@RunWith(BlockJUnit4ClassRunner.class)
public class FirebaseScriptInformantTest {

    @AfterClass
    public static void afterClass() {
        FirebaseScriptInformant.setIsLoaded(true);
        FirebaseScriptInformant.setIsLoaded(false);
    }

    @BeforeClass
    public static void beforeClass() {
        FirebaseScriptInformant.setIsLoaded(true);
        FirebaseScriptInformant.setIsLoaded(false);
    }

    @Test
    public void isFirebaseScriptLoaded() {
        // Given
        // When
        FirebaseScriptInformant.setIsLoaded(true);
        boolean val1 = FirebaseScriptInformant.isFirebaseScriptLoaded();
        FirebaseScriptInformant.setIsLoaded(false);
        boolean val2 = FirebaseScriptInformant.isFirebaseScriptLoaded();

        // Then
        Assert.assertTrue(val1);
        Assert.assertFalse(val2);
    }

    @Test
    public void addWaitingAction() {
        // Given
        Runnable runnable = Mockito.mock(Runnable.class);
        Runnable runnable2 = Mockito.mock(Runnable.class);
        Runnable runnable3 = Mockito.mock(Runnable.class);

        // When
        FirebaseScriptInformant.addWaitingAction(runnable);
        FirebaseScriptInformant.addWaitingAction(runnable2);
        FirebaseScriptInformant.addWaitingAction(runnable3);
        FirebaseScriptInformant.setIsLoaded(true);

        // Then
        Mockito.verify(runnable).run();
        Mockito.verify(runnable2).run();
        Mockito.verify(runnable3).run();
    }

    @Test
    public void setIsLoaded() {
        // Given
        boolean isLoaded = true;

        // When
        FirebaseScriptInformant.setIsLoaded(isLoaded);

        // Then
        Assert.assertTrue(FirebaseScriptInformant.isFirebaseScriptLoaded());
    }

    @Test
    public void setIsLoaded_false() {
        // Given
        boolean isLoaded = false;

        // When
        FirebaseScriptInformant.setIsLoaded(isLoaded);

        // Then
        Assert.assertFalse(FirebaseScriptInformant.isFirebaseScriptLoaded());
    }
}