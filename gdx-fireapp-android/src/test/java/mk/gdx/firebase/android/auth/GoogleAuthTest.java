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

package mk.gdx.firebase.android.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.android.utils.StringResource;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;

@PrepareForTest({GdxNativesLoader.class, GoogleSignInOptions.class, GoogleSignInClient.class, GoogleSignIn.class, StringResource.class})
public class GoogleAuthTest extends AndroidContextTest {

    private GoogleSignInClient googleSignInClient;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(GoogleSignInOptions.class);
        PowerMockito.mockStatic(GoogleSignInClient.class);
        PowerMockito.mockStatic(StringResource.class);
        PowerMockito.mockStatic(GoogleSignIn.class);
        googleSignInClient = Mockito.mock(GoogleSignInClient.class);
        Mockito.when(GoogleSignIn.getClient(Mockito.any(Context.class), Mockito.any(GoogleSignInOptions.class))).thenReturn(googleSignInClient);
        Mockito.when(GoogleSignIn.getClient(Mockito.any(Activity.class), Mockito.any(GoogleSignInOptions.class))).thenReturn(googleSignInClient);
        Mockito.when(StringResource.getStringResourceByName(Mockito.anyString())).thenReturn("test");
    }

    @Test
    public void signIn() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        // mock GoogleSignInOptions?
        GoogleAuth googleAuth = new GoogleAuth();

        // When
        googleAuth.signIn(callback);

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).startActivityForResult(Mockito.nullable(Intent.class), Mockito.anyInt());
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
    }

    @Test
    public void signOut_ok() {
        // Given
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(true);
        Mockito.when(googleSignInClient.signOut()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        googleAuth.signOut(callback);

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void signOut_fail() {
        // Given
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(false);
        Mockito.when(googleSignInClient.signOut()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        googleAuth.signOut(callback);

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.nullable(Exception.class));
    }

    @Test
    public void revokeAccess_ok() {
        // Given
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(true);
        Mockito.when(googleSignInClient.revokeAccess()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        googleAuth.revokeAccess(callback);

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).revokeAccess();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void revokeAccess_fail() {
        // Given
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(false);
        Mockito.when(googleSignInClient.revokeAccess()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        googleAuth.revokeAccess(callback);

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).revokeAccess();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }
}