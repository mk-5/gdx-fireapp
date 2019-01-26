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
    public void instance() {
        GdxFIRAuth instance = GdxFIRAuth.instance();
        assertNotNull(instance);
        assertNotNull(instance.google());
    }

    @Test
    public void signIn() {
        // Given
        // When
        GdxFIRAuth.instance().google().signIn();

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).signIn();
    }

    @Test
    public void signOut() {
        // Given
        // When
        GdxFIRAuth.instance().google().signOut();

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).signOut();
    }

    @Test
    public void revokeAccess() {
        // Given
        // When
        GdxFIRAuth.instance().google().revokeAccess();

        // Then
        Mockito.verify(googleAuthDistribution, VerificationModeFactory.times(1)).revokeAccess();
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals("mk.gdx.firebase.ios.auth.GoogleAuth", GdxFIRAuth.instance().google().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.auth.GoogleAuth", GdxFIRAuth.instance().google().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.auth.GoogleAuth", GdxFIRAuth.instance().google().getWebGLClassName());
    }
}