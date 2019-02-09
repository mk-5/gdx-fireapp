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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.storage.DownloadUrl;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

/**
 * Transforms {@code UploadTask.TaskSnapshot} to {@code FileMetadata}.
 * <p>
 * Transformation is needed because of need of shared code between modules.
 */
class FileMetadataBuilder {

    private final UploadTask.TaskSnapshot uploadTask;
    private final FirebaseStorage firebaseStorage;

    FileMetadataBuilder(UploadTask.TaskSnapshot uploadTask, FirebaseStorage firebaseStorage) {
        this.uploadTask = uploadTask;
        this.firebaseStorage = firebaseStorage;
    }

    FileMetadata build() {
        FileMetadata.Builder builder = new FileMetadata.Builder();
        if (uploadTask.getMetadata() != null) {
            builder.setMd5Hash(uploadTask.getMetadata().getMd5Hash())
                    .setName(uploadTask.getMetadata().getName())
                    .setPath(uploadTask.getMetadata().getPath())
                    .setSizeBytes(uploadTask.getMetadata().getSizeBytes())
                    .setUpdatedTimeMillis(uploadTask.getMetadata().getUpdatedTimeMillis())
                    .setCreationTimeMillis(uploadTask.getMetadata().getCreationTimeMillis())
                    .setDownloadUrl(new DownloadUrl(new Consumer<Consumer<String>>() {
                        @Override
                        public void accept(final Consumer<String> stringConsumer) {
                            firebaseStorage.getReference(uploadTask.getMetadata().getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    stringConsumer.accept(uri.toString());
                                }
                            });
                        }
                    }));
        }
        return builder.build();
    }
}
