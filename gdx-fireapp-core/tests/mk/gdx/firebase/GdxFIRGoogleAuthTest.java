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

package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;

import static org.junit.Assert.assertNotNull;


public class GdxFIRGoogleAuthTest extends GdxAppTest {

    @Mock
    private GoogleAuthDistribution googleAuthDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRAuth.instance().google().platformObject = googleAuthDistribution;
    }

    @Test
    public void instance() throws Exception {
        GdxFIRAuth instance = GdxFIRAuth.instance();
        assertNotNull(instance);
        assertNotNull(instance.google());
    }

    @Test
    public void signIn() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().google().signIn(callback);

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).signIn(Mockito.refEq(callback));
    }

    @Test
    public void signOut() {
        // Given
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);

        // When
        GdxFIRAuth.instance().google().signOut(callback);

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).signOut(Mockito.refEq(callback));
    }

    @Test
    public void revokeAccess() {
        // Given
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);

        // When
        GdxFIRAuth.instance().google().revokeAccess(callback);

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).revokeAccess(Mockito.refEq(callback));
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals(GdxFIRAuth.instance().google().getIOSClassName(), "mk.gdx.firebase.ios.auth.GoogleAuth");
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals(GdxFIRAuth.instance().google().getAndroidClassName(), "mk.gdx.firebase.android.auth.GoogleAuth");
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals(GdxFIRAuth.instance().google().getWebGLClassName(), "mk.gdx.firebase.html.auth.GoogleAuth");
    }
}