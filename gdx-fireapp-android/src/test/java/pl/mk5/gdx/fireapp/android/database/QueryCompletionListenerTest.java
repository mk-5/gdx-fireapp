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

package pl.mk5.gdx.fireapp.android.database;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import pl.mk5.gdx.fireapp.android.AndroidContextTest;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

public class QueryCompletionListenerTest extends AndroidContextTest {

    @Test
    public void onComplete_withoutError() {
        // Given
        DatabaseError databaseError = null;
        DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);
        FuturePromise promise = Mockito.spy(FuturePromise.class);
        QueryCompletionListener listener = new QueryCompletionListener(promise);

        // When
        listener.onComplete(databaseError, databaseReference);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.any());
    }

    @Test
    public void onComplete_error() {
        // Given
        DatabaseError databaseError = Mockito.mock(DatabaseError.class);
        DatabaseReference databaseReference = Mockito.mock(DatabaseReference.class);
        FuturePromise promise = Mockito.spy(FuturePromise.class);
        promise.silentFail();
        QueryCompletionListener listener = new QueryCompletionListener(promise);

        // When
        listener.onComplete(databaseError, databaseReference);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doFail(Mockito.nullable(Exception.class));
    }
}