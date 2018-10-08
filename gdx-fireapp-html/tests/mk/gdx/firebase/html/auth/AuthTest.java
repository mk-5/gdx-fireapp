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

import com.badlogic.gdx.utils.Timer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.html.GdxHtmlAppTest;
import mk.gdx.firebase.html.firebase.ScriptRunner;

@PrepareForTest({
        ScriptRunner.class, AuthJS.class, FirebaseUserJS.class, Timer.class
})
@SuppressStaticInitializationFor("com.badlogic.gdx.utils.Timer")
public class AuthTest extends GdxHtmlAppTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(AuthJS.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.when(ScriptRunner.class, "firebaseScript", Mockito.any(Runnable.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        });
    }

    @Test
    public void getCurrentUser_null() {
        // Given
        FirebaseUserJS firebaseUserJS = PowerMockito.mock(FirebaseUserJS.class);
        Mockito.when(firebaseUserJS.isNULL()).thenReturn(true);
        Mockito.when(AuthJS.firebaseUser()).thenReturn(firebaseUserJS);
        Auth auth = new Auth();

        // When
        GdxFirebaseUser user = auth.getCurrentUser();

        // Then
        Assert.assertNull(user);
    }

    @Test
    public void getCurrentUser() {
        // Given
        FirebaseUserJS firebaseUserJS = PowerMockito.mock(FirebaseUserJS.class);
        Mockito.when(firebaseUserJS.getDisplayName()).thenReturn("display_name");
        Mockito.when(firebaseUserJS.getEmail()).thenReturn("email");
        Mockito.when(firebaseUserJS.getPhotoURL()).thenReturn("photo_url");
        Mockito.when(firebaseUserJS.getProviderId()).thenReturn("provider_id");
        Mockito.when(firebaseUserJS.getUID()).thenReturn("uid");
        Mockito.when(AuthJS.firebaseUser()).thenReturn(firebaseUserJS);
        Auth auth = new Auth();

        // When
        GdxFirebaseUser firebaseUser = auth.getCurrentUser();

        // Then
        Assert.assertNotNull(firebaseUser.getUserInfo());
        Assert.assertEquals("display_name", firebaseUser.getUserInfo().getDisplayName());
        Assert.assertEquals("email", firebaseUser.getUserInfo().getEmail());
        Assert.assertEquals("photo_url", firebaseUser.getUserInfo().getPhotoUrl());
        Assert.assertEquals("provider_id", firebaseUser.getUserInfo().getProviderId());
        Assert.assertEquals("uid", firebaseUser.getUserInfo().getUid());
    }

    @Test
    public void createUserWithEmailAndPassword() {
        // Given
        Auth auth = new Auth();
        String email = "email@com.pl";
        char[] password = {'s', 'e', 'c', 'r', 'e', 't'};
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        PowerMockito.mockStatic(Timer.class);
        Mockito.when(Timer.schedule(Mockito.any(Timer.Task.class), Mockito.anyFloat())).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Timer.Task) invocation.getArgument(0)).run();
                return null;
            }
        });

        // When
        auth.createUserWithEmailAndPassword(email, password, callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.createUserWithEmailAndPassword(Mockito.eq(email), Mockito.eq(new String(password)), Mockito.any(AuthCallback.class));
    }

    @Test
    public void signInWithEmailAndPassword() {
        // Given
        Auth auth = new Auth();
        String email = "email@com.pl";
        char[] password = {'s', 'e', 'c', 'r', 'e', 't'};
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        auth.signInWithEmailAndPassword(email, password, callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.signInWithEmailAndPassword(Mockito.eq(email), Mockito.eq(new String(password)), Mockito.refEq(callback));
    }

    @Test
    public void signInWithToken() {
        // Given
        Auth auth = new Auth();
        String token = "token";
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        auth.signInWithToken(token, callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.signInWithToken(Mockito.eq(token), Mockito.refEq(callback));
    }

    @Test
    public void signInAnonymously() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        auth.signInAnonymously(callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.signInAnonymously(Mockito.refEq(callback));
    }

    @Test
    public void signOut() {
        // Given
        Auth auth = new Auth();
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);

        // When
        auth.signOut(callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.signOut(Mockito.refEq(callback));
    }

    @Test
    public void sendPasswordResetEmail() {
        // Given
        Auth auth = new Auth();
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        String arg1 = "email";

        // When
        auth.sendPasswordResetEmail(arg1, callback);

        // Then
        PowerMockito.verifyStatic(AuthJS.class);
        AuthJS.sendPasswordResetEmail(Mockito.eq(arg1), Mockito.refEq(callback));
    }
}