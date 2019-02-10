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

package pl.mk5.gdx.fireapp.android.auth;

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

import pl.mk5.gdx.fireapp.android.AndroidContextTest;
import pl.mk5.gdx.fireapp.android.utils.StringResource;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

import static org.mockito.Mockito.spy;

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
        // mock GoogleSignInOptions?
        GoogleAuth googleAuth = new GoogleAuth();

        // When
        googleAuth.signIn().exec();

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).startActivityForResult(Mockito.nullable(Intent.class), Mockito.anyInt());
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
    }

    @Test
    public void signOut_ok() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(true);
        Mockito.when(googleSignInClient.signOut()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        FuturePromise promise = (FuturePromise) spy(googleAuth.signOut().exec());

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).signOut();
//        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.isNull());
    }

    @Test
    public void signOut_fail() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(false);
        Mockito.when(googleSignInClient.signOut()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        FuturePromise promise = (FuturePromise) PowerMockito.spy(googleAuth.signOut().silentFail().exec());

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).signOut();
//        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(Exception.class));
    }

    @Test
    public void revokeAccess_ok() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(true);
        Mockito.when(googleSignInClient.revokeAccess()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        FuturePromise promise = (FuturePromise) spy(googleAuth.revokeAccess().exec());

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).revokeAccess();
//        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.isNull());
    }

    @Test
    public void revokeAccess_fail() {
        // Given
        GoogleAuth googleAuth = new GoogleAuth();
        Task task = Mockito.mock(Task.class);
        final Task task2 = Mockito.mock(Task.class);
        Mockito.when(task2.isSuccessful()).thenReturn(false);
        Mockito.when(googleSignInClient.revokeAccess()).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task2);
                return null;
            }
        });

        // When
        FuturePromise promise = (FuturePromise) spy(googleAuth.revokeAccess().silentFail().exec());

        // Then
        PowerMockito.verifyStatic(GoogleSignIn.class, VerificationModeFactory.times(1));
        GoogleSignIn.getClient((Activity) Mockito.refEq(Gdx.app), Mockito.any(GoogleSignInOptions.class));
        Mockito.verify(googleSignInClient, VerificationModeFactory.times(1)).revokeAccess();
//        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(Exception.class));
    }
}