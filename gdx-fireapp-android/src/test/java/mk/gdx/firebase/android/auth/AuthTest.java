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
import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.Promise;

@PrepareForTest({FirebaseAuth.class, GdxNativesLoader.class})
public class AuthTest extends AndroidContextTest {

    private FirebaseAuth firebaseAuth;
    private Task task;

    @Override
    public void setup() throws Exception {
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
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).createUserWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        Promise promise = auth.createUserWithEmailAndPassword("user", "password".toCharArray())
                .then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any(GdxFirebaseUser.class));
    }

    @Test
    public void createUserWithEmailAndPassword_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).createUserWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.createUserWithEmailAndPassword("user", "password".toCharArray())
                .fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void signInWithEmailAndPassword() {
        // Given
        Auth auth = new Auth();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.signInWithEmailAndPassword("user", "password".toCharArray())
                .then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any(GdxFirebaseUser.class));
    }

    @Test
    public void signInWithEmailAndPassword_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInWithEmailAndPassword(Mockito.anyString(), Mockito.anyString());

        // When
        auth.signInWithEmailAndPassword("user", "password".toCharArray())
                .fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void signInWithToken() {
        // Given
        Auth auth = new Auth();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInWithCustomToken(Mockito.anyString());

        // When
        auth.signInWithToken("token")
                .then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithCustomToken(Mockito.eq("token"));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any(GdxFirebaseUser.class));
    }

    @Test
    public void signInWithToken_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInWithCustomToken(Mockito.anyString());

        // When
        auth.signInWithToken("token")
                .fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInWithCustomToken(Mockito.eq("token"));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void signInAnonymously() {
        // Given
        Auth auth = new Auth();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).signInAnonymously();

        // When
        auth.signInAnonymously()
                .then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(2));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInAnonymously();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any(GdxFirebaseUser.class));
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).getCurrentUser();
    }

    @Test
    public void signInAnonymously_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).signInAnonymously();

        // When
        auth.signInAnonymously()
                .fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signInAnonymously();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnCompleteListener(Mockito.any(OnCompleteListener.class));
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void signOut() {
        // Given
        Auth auth = new Auth();
        Consumer consumer = Mockito.mock(Consumer.class);

        // When
        auth.signOut().then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any());
    }

    @Test
    public void signOut_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.doThrow(new RuntimeException()).when(firebaseAuth).signOut();

        // When
        auth.signOut().fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).signOut();
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }

    @Test
    public void sendPasswordResetEmail() {
        // Given
        Auth auth = new Auth();
        Consumer consumer = Mockito.mock(Consumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.doReturn(task).when(firebaseAuth).sendPasswordResetEmail(Mockito.anyString());
        String arg1 = "email";

        // When
        auth.sendPasswordResetEmail(arg1).then(consumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).sendPasswordResetEmail(Mockito.eq(arg1));
        Mockito.verify(consumer, VerificationModeFactory.times(1)).accept(Mockito.any());
    }

    @Test
    public void sendPasswordResetEmail_fail() {
        // Given
        Auth auth = new Auth();
        BiConsumer biConsumer = Mockito.mock(BiConsumer.class);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(task.getException()).thenReturn(new Exception());
        Mockito.doReturn(task).when(firebaseAuth).sendPasswordResetEmail(Mockito.anyString());
        String arg1 = "email";

        // When
        auth.sendPasswordResetEmail(arg1).fail(biConsumer);

        // Then
        PowerMockito.verifyStatic(FirebaseAuth.class, VerificationModeFactory.times(1));
        FirebaseAuth.getInstance();
        Mockito.verify(firebaseAuth, VerificationModeFactory.times(1)).sendPasswordResetEmail(Mockito.eq(arg1));
        Mockito.verify(biConsumer, VerificationModeFactory.times(1)).accept(Mockito.nullable(String.class), Mockito.any(Exception.class));
    }
}