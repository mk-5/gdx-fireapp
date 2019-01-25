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

package mk.gdx.firebase.android.storage;

import android.net.Uri;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;

import mk.gdx.firebase.android.AndroidContextTest;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.storage.FileMetadata;

@PrepareForTest({GdxNativesLoader.class, FirebaseStorage.class})
public class StorageTest extends AndroidContextTest {

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(FirebaseStorage.class);
        firebaseStorage = Mockito.mock(FirebaseStorage.class);
        storageReference = Mockito.mock(StorageReference.class);
        Mockito.when(FirebaseStorage.getInstance()).thenReturn(firebaseStorage);
        Mockito.when(firebaseStorage.getReference()).thenReturn(storageReference);
        Mockito.when(firebaseStorage.getReference().child(Mockito.anyString())).thenReturn(storageReference);
        Mockito.when(firebaseStorage.getReference(Mockito.nullable(String.class))).thenReturn(storageReference);
    }

    @Test
    public void upload() {
        // Given
        Storage storage = new Storage();
        FileHandle fileHandle = Mockito.mock(FileHandle.class);
        File file = Mockito.mock(File.class);
        Mockito.when(fileHandle.file()).thenReturn(file);
        UploadCallback callback = Mockito.mock(UploadCallback.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FileMetadata) invocation.getArgument(0)).getDownloadUrl().getUrl(new Consumer<String>() {
                    @Override
                    public void accept(String s) {

                    }
                });
                return null;
            }
        }).when(callback).onSuccess(Mockito.any(FileMetadata.class));
        final UploadTask uploadTask = Mockito.mock(UploadTask.class);
        final Task task = Mockito.mock(Task.class);
        final UploadTask.TaskSnapshot taskSnapshot = Mockito.mock(UploadTask.TaskSnapshot.class);
        StorageMetadata storageMetadata = Mockito.mock(StorageMetadata.class);
        Mockito.when(taskSnapshot.getMetadata()).thenReturn(storageMetadata);
        Mockito.when(storageMetadata.getPath()).thenReturn("test");
        Mockito.when(storageReference.putFile(Mockito.any(Uri.class))).thenReturn(uploadTask);
        Mockito.when(storageReference.getDownloadUrl()).thenReturn(task);
        Mockito.when(task.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(Mockito.mock(Uri.class));
                return null;
            }
        });
        Mockito.when(uploadTask.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(uploadTask);
        Mockito.when(uploadTask.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(taskSnapshot);
                return uploadTask;
            }
        });

        // When
        storage.upload(fileHandle, "test", callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).putFile(Mockito.any(Uri.class));
        Mockito.verify(uploadTask, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(uploadTask, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void upload1() {
        // Given
        Storage storage = new Storage();
        byte[] data = new byte[]{0, 0, 0, 1, 1, 1};
        UploadCallback callback = Mockito.mock(UploadCallback.class);
        final UploadTask uploadTask = Mockito.mock(UploadTask.class);
        Mockito.when(storageReference.putBytes(Mockito.any(byte[].class))).thenReturn(uploadTask);
        Mockito.when(uploadTask.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(uploadTask);
        Mockito.when(uploadTask.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(Mockito.mock(UploadTask.TaskSnapshot.class));
                return uploadTask;
            }
        });

        // When
        storage.upload(data, "test", callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).putBytes(Mockito.any(byte[].class));
        Mockito.verify(uploadTask, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(uploadTask, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void download_file() {
        // Given
        Storage storage = new Storage();
        File file = Mockito.mock(File.class);
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        FileDownloadTask task = Mockito.mock(FileDownloadTask.class);
        Mockito.when(storageReference.getFile(Mockito.any(File.class))).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(task);
        Mockito.when(task.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(Mockito.mock(FileDownloadTask.TaskSnapshot.class));
                return null;
            }
        });

        // When
        storage.download("test", file, callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).getFile(Mockito.any(File.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void download_file_failure() {
        // Given
        Storage storage = new Storage();
        File file = Mockito.mock(File.class);
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        final FileDownloadTask task = Mockito.mock(FileDownloadTask.class);
        Mockito.when(storageReference.getFile(Mockito.any(File.class))).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnFailureListener) invocation.getArgument(0)).onFailure(new Exception());
                return task;
            }
        });

        // When
        storage.download("test", file, callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).getFile(Mockito.any(File.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void download_nullFile() {
        // Given
        Storage storage = new Storage();
        PowerMockito.mockStatic(File.class);
        File file = null;
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        FileDownloadTask task = Mockito.mock(FileDownloadTask.class);
        Mockito.when(storageReference.getFile(Mockito.any(File.class))).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(task);

        // When
        storage.download("test", file, callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).getFile(Mockito.any(File.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void download1_path() {
        // Given
        Storage storage = new Storage();
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        final Task task = Mockito.mock(Task.class);
        Mockito.when(storageReference.getBytes(Mockito.anyLong())).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(task);
        Mockito.when(task.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(new byte[0]);
                return task;
            }
        });
        long byteLimit = 1000;

        // When
        storage.download("test", byteLimit, callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).getBytes(Mockito.anyLong());
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void download1_path_failure() {
        // Given
        Storage storage = new Storage();
        DownloadCallback callback = Mockito.mock(DownloadCallback.class);
        final Task task = Mockito.mock(Task.class);
        Mockito.when(storageReference.getBytes(Mockito.anyLong())).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnFailureListener) invocation.getArgument(0)).onFailure(new Exception());
                return task;
            }
        });
        long byteLimit = 1000;

        // When
        storage.download("test", byteLimit, callback);

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).getBytes(Mockito.anyLong());
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void delete() {
        // Given
        Storage storage = new Storage();
        final Task task = Mockito.mock(Task.class);
        Mockito.when(storageReference.delete()).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).thenReturn(task);
        Mockito.when(task.addOnSuccessListener(Mockito.any(OnSuccessListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnSuccessListener) invocation.getArgument(0)).onSuccess(null);
                return task;
            }
        });
        long byteLimit = 1000;

        // When
        storage.delete("test");

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).delete();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void delete_failure() {
        // Given
        Storage storage = new Storage();
        final Task task = Mockito.mock(Task.class);
        Mockito.when(storageReference.delete()).thenReturn(task);
        Mockito.when(task.addOnFailureListener(Mockito.any(OnFailureListener.class))).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((OnFailureListener) invocation.getArgument(0)).onFailure(new Exception());
                return task;
            }
        });
        long byteLimit = 1000;

        // When
        storage.delete("test");

        // Then
        Mockito.verify(storageReference, VerificationModeFactory.times(1)).delete();
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnFailureListener(Mockito.any(OnFailureListener.class));
        Mockito.verify(task, VerificationModeFactory.times(1)).addOnSuccessListener(Mockito.any(OnSuccessListener.class));
    }

    @Test
    public void inBucket() {
        // Given
        Storage storage = new Storage();

        // When
        storage.inBucket("test");

        // Then
        PowerMockito.verifyStatic(FirebaseStorage.class, VerificationModeFactory.times(1));
        FirebaseStorage.getInstance(Mockito.eq("test"));
    }
}