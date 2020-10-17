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

package pl.mk5.gdx.fireapp.android.storage;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

class UploadProcessor {

    void processUpload(final FirebaseStorage firebaseStorage, final String path, final byte[] data, final FuturePromise<FileMetadata> promise) {
        StorageReference dataRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = dataRef.putBytes(data);
        processUpload(firebaseStorage, uploadTask, promise);
    }

    void processUpload(final FirebaseStorage firebaseStorage, final String path, final FileHandle file, final FuturePromise<FileMetadata> promise) {
        StorageReference dataRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = dataRef.putFile(Uri.fromFile(file.file()));
        processUpload(firebaseStorage, uploadTask, promise);
    }

    private void processUpload(final FirebaseStorage firebaseStorage, UploadTask uploadTask, final FuturePromise<FileMetadata> promise) {
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                promise.doFail(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                FileMetadata fileMetadata = new FileMetadataBuilder(taskSnapshot, firebaseStorage).build();
                promise.doComplete(fileMetadata);
            }
        });
    }
}
