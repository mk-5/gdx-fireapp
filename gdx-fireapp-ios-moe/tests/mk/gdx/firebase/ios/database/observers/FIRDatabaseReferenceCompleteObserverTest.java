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

package mk.gdx.firebase.ios.database.observers;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import apple.foundation.NSError;
import bindings.google.firebasedatabase.FIRDatabaseReference;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, NSError.class, FIRDatabaseReference.class})
public class FIRDatabaseReferenceCompleteObserverTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(NSError.class);
        PowerMockito.mockStatic(FIRDatabaseReference.class);
    }

    @Test
    public void process() {
        // Given
        FIRDatabaseReference firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        FIRDatabaseReferenceCompleteObserver observer = new FIRDatabaseReferenceCompleteObserver(callback);

        // When
        observer.process(null, firDatabaseReference);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void process_withError() {
        // Given
        FIRDatabaseReference firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        FIRDatabaseReferenceCompleteObserver observer = new FIRDatabaseReferenceCompleteObserver(callback);
        NSError nsError = Mockito.mock(NSError.class);

        // When
        observer.process(nsError, firDatabaseReference);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.any(Exception.class));
    }

    @Test
    public void call_setValueWithCompletionBlock() {
        // Given
        FIRDatabaseReference firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        FIRDatabaseReferenceCompleteObserver observer = new FIRDatabaseReferenceCompleteObserver(callback);

        // When
        observer.call_setValueWithCompletionBlock(null, firDatabaseReference);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void call_removeValueWithCompletionBlock() {
        // Given
        FIRDatabaseReference firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        FIRDatabaseReferenceCompleteObserver observer = new FIRDatabaseReferenceCompleteObserver(callback);

        // When
        observer.call_removeValueWithCompletionBlock(null, firDatabaseReference);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void call_updateChildValuesWithCompletionBlock() {
        // Given
        FIRDatabaseReference firDatabaseReference = Mockito.mock(FIRDatabaseReference.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        FIRDatabaseReferenceCompleteObserver observer = new FIRDatabaseReferenceCompleteObserver(callback);

        // When
        observer.call_updateChildValuesWithCompletionBlock(null, firDatabaseReference);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }
}