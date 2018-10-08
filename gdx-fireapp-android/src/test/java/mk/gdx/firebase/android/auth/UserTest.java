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

import junit.framework.Assert;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.callbacks.CompleteCallback;

@PrepareForTest({FirebaseAuth.class, GdxNativesLoader.class})
public class UserTest extends AndroidContextTest {

    private FirebaseAuth firebaseAuth;
    private Task task;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseAuth.class);
        firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
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

    @Test(expected = IllegalStateException.class)
    public void updateEmail_noUser() {
        // Given
        User user = new User();

        // When
        user.updateEmail("test", Mockito.mock(CompleteCallback.class));

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void sendEmailVerification_noUser() {
        // Given
        User user = new User();

        // When
        user.sendEmailVerification(Mockito.mock(CompleteCallback.class));

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void updatePassword_noUser() {
        // Given
        User user = new User();

        // When
        user.updatePassword(new char[]{'a', 'b'}, Mockito.mock(CompleteCallback.class));

        // Then
        Assert.fail();
    }

    @Test(expected = IllegalStateException.class)
    public void delete_noUser() {
        // Given
        User user = new User();

        // When
        user.delete(Mockito.mock(CompleteCallback.class));

        // Then
        Assert.fail();
    }

    @Test
    public void updateEmail() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.when(firebaseUser.updateEmail(Mockito.anyString())).thenReturn(task);
        String arg1 = "newEmail";

        // When
        user.updateEmail(arg1, callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).updateEmail(arg1);
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void updateEmail_fail() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(firebaseUser.updateEmail(Mockito.anyString())).thenReturn(task);
        String arg1 = "newEmail";

        // When
        user.updateEmail(arg1, callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).updateEmail(arg1);
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }

    @Test
    public void sendEmailVerification() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.when(firebaseUser.sendEmailVerification()).thenReturn(task);

        // When
        user.sendEmailVerification(callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).sendEmailVerification();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void sendEmailVerification_fail() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(firebaseUser.sendEmailVerification()).thenReturn(task);

        // When
        user.sendEmailVerification(callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).sendEmailVerification();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }

    @Test
    public void updatePassword() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.when(firebaseUser.updatePassword(Mockito.anyString())).thenReturn(task);
        char[] arg1 = {'a', 'b', 'c'};

        // When
        user.updatePassword(arg1, callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).updatePassword(Mockito.eq(new String(arg1)));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void updatePassword_fail() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(firebaseUser.updatePassword(Mockito.anyString())).thenReturn(task);
        char[] arg1 = {'a', 'b', 'c'};

        // When
        user.updatePassword(arg1, callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).updatePassword(Mockito.eq(new String(arg1)));
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }

    @Test
    public void delete() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(true);
        Mockito.when(firebaseUser.delete()).thenReturn(task);

        // When
        user.delete(callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).delete();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void delete_fail() {
        // Given
        User user = new User();
        FirebaseUser firebaseUser = Mockito.mock(FirebaseUser.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(firebaseUser);
        Mockito.when(task.isSuccessful()).thenReturn(false);
        Mockito.when(firebaseUser.delete()).thenReturn(task);

        // When
        user.delete(callback);

        // Then
        Mockito.verify(firebaseUser, VerificationModeFactory.times(1)).delete();
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }
}