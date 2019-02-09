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

package pl.mk5.gdx.fireapp.ios;

import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import bindings.google.firebasecore.FIRApp;

@PrepareForTest({FIRApp.class, NatJ.class})
public class AppTest extends GdxIOSAppTest {

    @Test
    public void configure() {
        // Given
        PowerMockito.mockStatic(FIRApp.class);
        App app = new App();

        // When
        app.configure();

        // Then
        PowerMockito.verifyStatic(FIRApp.class, VerificationModeFactory.times(1));
        FIRApp.defaultApp();
        PowerMockito.verifyStatic(FIRApp.class, VerificationModeFactory.times(1));
        FIRApp.configure();
    }
}