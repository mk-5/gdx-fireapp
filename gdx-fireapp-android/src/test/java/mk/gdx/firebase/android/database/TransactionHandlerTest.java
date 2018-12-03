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

package mk.gdx.firebase.android.database;

import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.TransactionCallback;

@PrepareForTest({GdxNativesLoader.class, MutableData.class, Transaction.class, GdxFIRLogger.class})
public class TransactionHandlerTest extends AndroidContextTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(MutableData.class);
        PowerMockito.mockStatic(Transaction.class);
        PowerMockito.mockStatic(GdxFIRLogger.class);
    }

    @Test
    public void doTransaction() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        MutableData mutableData = Mockito.mock(MutableData.class);
        Mockito.when(mutableData.getValue()).thenReturn("test_value");

        // When
        transactionHandler.doTransaction(mutableData);

        // Then
        PowerMockito.verifyStatic(Transaction.class, VerificationModeFactory.times(1));
        Transaction.success(Mockito.nullable(MutableData.class));
    }

    @Test
    public void doTransaction_nullData() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        MutableData mutableData = Mockito.mock(MutableData.class);
        Mockito.when(mutableData.getValue()).thenReturn(null);

        // When
        transactionHandler.doTransaction(mutableData);

        // Then
        PowerMockito.verifyStatic(Transaction.class, VerificationModeFactory.times(1));
        Transaction.abort();
    }

    @Test
    public void onComplete_withErrorAndCallback() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        DatabaseError databaseError = Mockito.mock(DatabaseError.class);
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        boolean status = true;

        // When
        transactionHandler.onComplete(databaseError, status, dataSnapshot);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }

    @Test
    public void onComplete_withErrorAndWithoutCallback() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = null;
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        DatabaseError databaseError = Mockito.mock(DatabaseError.class);
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        boolean status = true;

        // When
        transactionHandler.onComplete(databaseError, status, dataSnapshot);

        // Then
        PowerMockito.verifyStatic(GdxFIRLogger.class);
        GdxFIRLogger.error(Mockito.anyString(), Mockito.nullable(Exception.class));
    }

    @Test
    public void onComplete_withoutErrorAndWithCallback_statusOk() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        DatabaseError databaseError = null;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        boolean status = true;

        // When
        transactionHandler.onComplete(databaseError, status, dataSnapshot);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onSuccess();
    }

    @Test
    public void onComplete_withoutErrorAndWithCallback_statusError() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        DatabaseError databaseError = null;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        boolean status = false;

        // When
        transactionHandler.onComplete(databaseError, status, dataSnapshot);

        // Then
        Mockito.verify(callback, VerificationModeFactory.times(1)).onError(Mockito.nullable(Exception.class));
    }

    @Test
    public void onComplete_withoutErrorAndCallback_statusError() {
        // Given
        TransactionCallback transactionCallback = Mockito.mock(TransactionCallback.class);
        CompleteCallback callback = null;
        TransactionHandler transactionHandler = new TransactionHandler(transactionCallback, callback);
        DatabaseError databaseError = null;
        DataSnapshot dataSnapshot = Mockito.mock(DataSnapshot.class);
        boolean status = false;

        // When
        transactionHandler.onComplete(databaseError, status, dataSnapshot);

        // Then
        PowerMockito.verifyStatic(GdxFIRLogger.class);
        GdxFIRLogger.error(Mockito.anyString());
    }
}