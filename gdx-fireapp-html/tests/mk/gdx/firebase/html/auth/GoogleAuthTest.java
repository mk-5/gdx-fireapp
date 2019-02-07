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

package mk.gdx.firebase.html.auth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.html.firebase.ScriptRunner;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

import static org.mockito.ArgumentMatchers.refEq;

@PrepareForTest({
        ScriptRunner.class, GoogleAuthJS.class, FirebaseUserJS.class, GdxFIRAuth.class
})
public class GoogleAuthTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();


    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(GoogleAuthJS.class);
        PowerMockito.mockStatic(GdxFIRAuth.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.when(ScriptRunner.class, "firebaseScript", Mockito.any(Runnable.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        });
    }

    @Test
    public void signIn() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();

        // When
        Promise promise = googleAuth.signIn().exec();

        // Then
        PowerMockito.verifyStatic(GoogleAuthJS.class);
        GoogleAuthJS.signIn((FuturePromise) refEq(promise));
    }

    @Test
    public void signOut() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Auth auth = Mockito.mock(Auth.class);
        GdxFIRAuth gdxFIRAuth = Mockito.mock(GdxFIRAuth.class);
        Mockito.when(GdxFIRAuth.instance()).thenReturn(gdxFIRAuth);
        Mockito.when(gdxFIRAuth.signOut()).thenReturn((FuturePromise) Mockito.spy(FuturePromise.empty()));

        // When
        googleAuth.signOut().exec();

        // Then
        Mockito.verify(gdxFIRAuth, VerificationModeFactory.times(1)).signOut();
    }

    @Test
    public void revokeAccess() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Auth auth = Mockito.mock(Auth.class);
        GdxFIRAuth gdxFIRAuth = Mockito.mock(GdxFIRAuth.class);
        Mockito.when(GdxFIRAuth.instance()).thenReturn(gdxFIRAuth);
        Mockito.when(gdxFIRAuth.signOut()).thenReturn(Mockito.spy((FuturePromise) Mockito.spy(FuturePromise.empty())));

        // When
        googleAuth.revokeAccess().exec();

        // Then
        Mockito.verify(gdxFIRAuth, VerificationModeFactory.times(1)).signOut();
    }
}