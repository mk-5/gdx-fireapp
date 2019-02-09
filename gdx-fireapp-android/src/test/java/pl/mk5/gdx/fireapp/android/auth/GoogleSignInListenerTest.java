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
import android.content.Intent;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.android.AndroidContextTest;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

@PrepareForTest({GdxNativesLoader.class, GoogleSignIn.class, GoogleAuthProvider.class, FirebaseAuth.class, GdxFIRAuth.class})
public class GoogleSignInListenerTest extends AndroidContextTest {

    private FirebaseAuth firebaseAuth;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(GdxFIRAuth.class);
        PowerMockito.mockStatic(FirebaseAuth.class);
        PowerMockito.mockStatic(GoogleSignIn.class);
        PowerMockito.mockStatic(GoogleAuthProvider.class);
        Mockito.when(GoogleAuthProvider.getCredential(Mockito.nullable(String.class), Mockito.nullable(String.class)))
                .thenReturn(Mockito.mock(AuthCredential.class));
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        Mockito.when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);
        Mockito.when(GdxFIRAuth.instance()).thenReturn(Mockito.mock(GdxFIRAuth.class));
    }

    @Test
    public void onActivityResult_wrongRequestCode() {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        GoogleSignInListener listener = new GoogleSignInListener(promise);
        Intent intent = PowerMockito.mock(Intent.class);
        int requestCode = -1;
        int resultCode = -1;

        // When
        listener.onActivityResult(requestCode, resultCode, intent);

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).removeAndroidEventListener(Mockito.refEq(listener));
    }

    @Test
    public void onActivityResult_validRequestCode_successTask() throws Throwable {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        GoogleSignInListener listener = new GoogleSignInListener(promise);
        Intent intent = PowerMockito.mock(Intent.class);
        int requestCode = Const.GOOGLE_SIGN_IN;
        int resultCode = -1;
        final Task task = Mockito.mock(Task.class);
        Mockito.when(task.getResult(ApiException.class)).thenReturn(Mockito.mock(GoogleSignInAccount.class));
        Mockito.when(GoogleSignIn.getSignedInAccountFromIntent(Mockito.any(Intent.class))).thenReturn(task);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.when(firebaseAuth.signInWithCredential(Mockito.any(AuthCredential.class))).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(Activity.class), Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(1)).onComplete(task);
                return null;
            }
        });

        // When
        listener.onActivityResult(requestCode, resultCode, intent);

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).removeAndroidEventListener(Mockito.refEq(listener));
        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.nullable(GdxFirebaseUser.class));
    }

    @Test
    public void onActivityResult_validRequestCode_failTask() throws Throwable {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        GoogleSignInListener listener = new GoogleSignInListener(promise);
        Intent intent = PowerMockito.mock(Intent.class);
        int requestCode = Const.GOOGLE_SIGN_IN;
        int resultCode = -1;
        final Task task = Mockito.mock(Task.class);
        Mockito.when(task.getResult(ApiException.class)).thenReturn(Mockito.mock(GoogleSignInAccount.class));
        Mockito.when(GoogleSignIn.getSignedInAccountFromIntent(Mockito.any(Intent.class))).thenReturn(task);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(firebaseAuth.signInWithCredential(Mockito.any(AuthCredential.class))).thenReturn(task);
        Mockito.when(task.addOnCompleteListener(Mockito.any(Activity.class), Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(1)).onComplete(task);
                return null;
            }
        });

        // When
        listener.onActivityResult(requestCode, resultCode, intent);

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).removeAndroidEventListener(Mockito.refEq(listener));
        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(Exception.class));
    }

    @Test
    public void onActivityResult_validRequestCode_apiException() throws Throwable {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        GoogleSignInListener listener = new GoogleSignInListener(promise);
        Intent intent = PowerMockito.mock(Intent.class);
        int requestCode = Const.GOOGLE_SIGN_IN;
        int resultCode = -1;
        final Task task = Mockito.mock(Task.class);
        Mockito.when(task.getResult(ApiException.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                throw new ApiException(Status.RESULT_CANCELED);
            }
        });
        Mockito.when(GoogleSignIn.getSignedInAccountFromIntent(Mockito.any(Intent.class))).thenReturn(task);

        // When
        listener.onActivityResult(requestCode, resultCode, intent);

        // Then
        Mockito.verify(((AndroidApplication) Gdx.app), VerificationModeFactory.times(1)).removeAndroidEventListener(Mockito.refEq(listener));
        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(Exception.class));
    }
}