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

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;

@PrepareForTest({FirebaseAuth.class, GdxNativesLoader.class})
public class AuthTest extends AndroidContextTest {

    private FirebaseAuth firebaseAuth;
    private Task task;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FirebaseAuth.class);
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(Mockito.mock(FirebaseUser.class));
        task = PowerMockito.mock(Task.class);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task);
                return null;
            }
        });
        Mockito.when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);
    }

    @Test
    public void getCurrentUser() {
        // Given
        Auth auth = new Auth();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        Mockito.when(firebaseUser.getDisplayName()).thenReturn("display_name");
        Mockito.when(firebaseUser.getEmail()).thenReturn("email");
        Mockito.when(firebaseUser.getUid()).thenReturn("uid");
        Mockito.when(firebaseUser.getProviderId()).thenReturn("provider_id");
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);

        // When
        GdxFirebaseUser user = auth.getCurrentUser();

        // Then
        Assert.assertNotNull(user.getUserInfo());
        Assert.assertEquals(user.getUserInfo().getDisplayName(), firebaseUser.getDisplayName());
        Assert.assertEquals(user.getUserInfo().getEmail(), firebaseUser.getEmail());
        Assert.assertEquals(user.getUserInfo().getUid(), firebaseUser.getUid());
        Assert.assertEquals(user.getUserInfo().getProviderId(), firebaseUser.getProviderId());
    }

    @Test
    public void createUserWithEmailAndPassword() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).createUserWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.createUserWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(GdxFirebaseUser.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void createUserWithEmailAndPassword_fail() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).createUserWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.createUserWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.any(Exception.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInWithEmailAndPassword() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.signInWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(GdxFirebaseUser.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInWithEmailAndPassword_fail() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.signInWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.any(Exception.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInWithToken() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInWithCustomToken(Mockito.anyString());

        // When
        auth.signInWithToken("token", callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithCustomToken(Mockito.eq("token"));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(GdxFirebaseUser.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInWithToken_fail() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInWithCustomToken(Mockito.anyString());

        // When
        auth.signInWithToken("token", callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithCustomToken(Mockito.eq("token"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.any(Exception.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInAnonymously() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInAnonymously();

        // When
        auth.signInAnonymously(callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInAnonymously();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess(Mockito.any(GdxFirebaseUser.class));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signInAnonymously_fail() {
        // Given
        Auth auth = new Auth();
        AuthCallback callback = Mockito.mock(AuthCallback.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInAnonymously();

        // When
        auth.signInAnonymously(callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInAnonymously();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.any(Exception.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signOut() {
        // Given
        Auth auth = new Auth();
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);

        // When
        auth.signOut(callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }

    @Test
    public void signOut_fail() {
        // Given
        Auth auth = new Auth();
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);
        Mockito.doThrow(new RuntimeException()).when(firebaseAuth).signOut();

        // When
        auth.signOut(callback);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onFail(Mockito.any(RuntimeException.class));
        Mockito.verifyNoMoreInteractions(firebaseAuth);
    }
}