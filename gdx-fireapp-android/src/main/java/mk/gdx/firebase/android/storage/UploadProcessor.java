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
import android.support.annotation.NonNull;

import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.storage.FileMetadata;

class UploadProcessor {


    /**
     * Process bytes upload
     *
     * @param firebaseStorage FirebaseStorage, not null
     * @param path            Firebase storage path, not null
     * @param data            Data to upload, not null
     * @param promise         The future promise, not null
     */
    void processUpload(final FirebaseStorage firebaseStorage, final String path, final byte[] data, final FuturePromise<FileMetadata> promise) {
        StorageReference dataRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = dataRef.putBytes(data);
        processUpload(firebaseStorage, uploadTask, promise);
    }

    /**
     * Process upload of file
     *
     * @param firebaseStorage FirebaseStorage, not null
     * @param path            Firebase storage path, not null
     * @param file            File to upload, not null
     * @param promise         The future promise, not null
     */
    void processUpload(final FirebaseStorage firebaseStorage, final String path, final FileHandle file, final FuturePromise<FileMetadata> promise) {
        StorageReference dataRef = firebaseStorage.getReference().child(path);
        UploadTask uploadTask = dataRef.putFile(Uri.fromFile(file.file()));
        processUpload(firebaseStorage, uploadTask, promise);
    }

    /**
     * Process upload
     *
     * @param firebaseStorage FirebaseStorage, not null
     * @param uploadTask      Upload task which we want to deal with, not null
     * @param promise         The future promise, not null
     */
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
