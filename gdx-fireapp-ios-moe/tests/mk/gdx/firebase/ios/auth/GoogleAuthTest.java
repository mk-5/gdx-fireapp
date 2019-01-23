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

package mk.gdx.firebase.ios.auth;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import bindings.google.firebasecore.FIRApp;
import bindings.google.googlesignin.GIDSignIn;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, GIDSignIn.class, FIRApp.class, GoogleAuthProvider.class})
public class GoogleAuthTest extends GdxIOSAppTest {

    private GIDSignIn gidSignIn;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(GoogleAuthProvider.class);
        PowerMockito.mockStatic(GIDSignIn.class);
        PowerMockito.mockStatic(FIRApp.class);
        gidSignIn = PowerMockito.mock(GIDSignIn.class);
        Mockito.when(GIDSignIn.sharedInstance()).thenReturn(gidSignIn);
        Mockito.when(FIRApp.defaultApp()).thenReturn(PowerMockito.mock(FIRApp.class));
    }

    @Test
    public void signIn() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Whitebox.setInternalState(googleAuth, "googleAuthProvider", Mockito.mock(GoogleAuthProvider.class));

        // When
        googleAuth.signIn();

        // Then
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).signIn();
        Mockito.verifyNoMoreInteractions(gidSignIn);
    }

    @Test
    public void signOut() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Whitebox.setInternalState(googleAuth, "googleAuthProvider", Mockito.mock(GoogleAuthProvider.class));

        // When
        googleAuth.signOut();

        // Then
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).signOut();
        Mockito.verifyNoMoreInteractions(gidSignIn);
    }

    @Test
    public void revokeAccess() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Whitebox.setInternalState(googleAuth, "googleAuthProvider", Mockito.mock(GoogleAuthProvider.class));

        // When
        googleAuth.revokeAccess();

        // Then
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).disconnect();
        Mockito.verifyNoMoreInteractions(gidSignIn);
    }
}