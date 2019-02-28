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

package pl.mk5.gdx.fireapp.html.auth;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;

@PrepareForTest({GdxFIRAuth.class})
public class AuthJSTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    public void getUserBridge() {
        // Given
        PowerMockito.mockStatic(GdxFIRAuth.class);
        GdxFIRAuth gdxFIRAuth = Mockito.mock(GdxFIRAuth.class);
        Mockito.when(GdxFIRAuth.instance()).thenReturn(gdxFIRAuth);

        // When
        GdxFirebaseUser user = AuthJS.getUserBridge();

        // Then
        Mockito.verify(gdxFIRAuth, VerificationModeFactory.times(1)).getCurrentUser();
    }
}