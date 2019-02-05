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

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({GdxNativesLoader.class, FirebaseAuth.class})
public class AuthPromiseConsumerTest extends AndroidContextTest {

    private Task task;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseAuth.class);
        FirebaseAuth firebaseAuth = PowerMockito.mock(FirebaseAuth.class);
        Mockito.when(firebaseAuth.getCurrentUser()).thenReturn(Mockito.mock(FirebaseUser.class));
        Mockito.when(FirebaseAuth.getInstance()).thenReturn(firebaseAuth);
        task = Mockito.mock(Task.class);
        Mockito.when(task.addOnCompleteListener(Mockito.any(OnCompleteListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnCompleteListener) invocation.getArgument(0)).onComplete(task);
                return null;
            }
        });
    }

    @Test
    public void accept() {
        // Given
        Mockito.when(task.isSuccessful()).thenReturn(true);
        FuturePromise promise = Mockito.spy(FuturePromise.empty());
        promise.then(Mockito.mock(Consumer.class));
        AuthPromiseConsumer authPromiseConsumer = new AuthPromiseConsumer(task);

        // When
        authPromiseConsumer.accept(promise);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.any());

    }

    @Test
    public void accept_fail() {
        // Given
        Mockito.when(task.isSuccessful()).thenReturn(false);
        FuturePromise promise = Mockito.spy(FuturePromise.empty());
        promise.then(Mockito.mock(Consumer.class));
        AuthPromiseConsumer authPromiseConsumer = new AuthPromiseConsumer(task);

        // When
        authPromiseConsumer.accept(promise);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(String.class), Mockito.nullable(Exception.class));
    }
}