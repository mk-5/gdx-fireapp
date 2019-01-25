/*
 * Copyright 2017 mk
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
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import mk.gdx.firebase.GdxFIRLogger;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.DownloadUrl;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * Android Firebase storage API implementation.
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution {

    private FirebaseStorage firebaseStorage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(FileHandle file, String path, UploadCallback callback) {
        StorageReference dataRef = firebaseStorage().getReference().child(path);
        UploadTask uploadTask = dataRef.putFile(Uri.fromFile(file.file()));
        processUpload(uploadTask, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(byte[] data, String path, @NonNull final UploadCallback callback) {
        StorageReference dataRef = firebaseStorage().getReference().child(path);
        UploadTask uploadTask = dataRef.putBytes(data);
        processUpload(uploadTask, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void download(String path, long bytesLimit, @NonNull final DownloadCallback<byte[]> callback) {
        StorageReference pathRef = firebaseStorage().getReference().child(path);
        pathRef.getBytes(bytesLimit).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                callback.onSuccess(bytes);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void download(String path, File targetFile, @NonNull final DownloadCallback<File> callback) {
        StorageReference pathRef = firebaseStorage().getReference().child(path);
        if (targetFile == null) {
            try {
                targetFile = File.createTempFile(UUID.randomUUID().toString(), "");
            } catch (IOException e) {
                GdxFIRLogger.error("Error while creating temporary file.", e);
            }
        }
        if (targetFile == null)
            throw new IllegalArgumentException("Target file is null and is unable to create temporary file.");
        final File finalTargetFile = targetFile;
        pathRef.getFile(targetFile)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onFail(e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {

                callback.onSuccess(finalTargetFile);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> delete(final String path) {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                StorageReference pathRef = firebaseStorage().getReference().child(path);
                pathRef.delete().addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        voidFuturePromise.doFail(e);
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StorageDistribution inBucket(String url) {
        firebaseStorage = FirebaseStorage.getInstance(url);
        return this;
    }

    /**
     * @return Lazy loaded instance of {@link FirebaseStorage}. It should be only one instance for one instance of {@link Storage}
     */
    private FirebaseStorage firebaseStorage() {
        if (firebaseStorage == null)
            firebaseStorage = FirebaseStorage.getInstance();
        return firebaseStorage;
    }

    /**
     * Add onFailure and onSuccess listeners to uploadTask.
     *
     * @param uploadTask Upload task which we want to deal with.
     * @param callback   Callback which will be call from {@link UploadTask#addOnFailureListener(OnFailureListener)} and {@link UploadTask#addOnSuccessListener(OnSuccessListener)}
     */
    private void processUpload(UploadTask uploadTask, final UploadCallback callback) {
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                callback.onFail(e);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                FileMetadata fileMetadata = buildMetadata(taskSnapshot);
                callback.onSuccess(fileMetadata);
            }
        });
    }

    /**
     * Transforms {@code UploadTask.TaskSnapshot} to {@code FileMetadata}.
     * <p>
     * Transformation is needed because of need of shared code between modules.
     *
     * @param taskSnapshot Snapshot of just uploaded data
     * @return Firebase storage file metadata wrapped by {@link FileMetadata}
     */
    private FileMetadata buildMetadata(final UploadTask.TaskSnapshot taskSnapshot) {
        FileMetadata.Builder builder = new FileMetadata.Builder();
        if (taskSnapshot.getMetadata() != null) {
            builder.setMd5Hash(taskSnapshot.getMetadata().getMd5Hash())
                    .setName(taskSnapshot.getMetadata().getName())
                    .setPath(taskSnapshot.getMetadata().getPath())
                    .setSizeBytes(taskSnapshot.getMetadata().getSizeBytes())
                    .setUpdatedTimeMillis(taskSnapshot.getMetadata().getUpdatedTimeMillis())
                    .setCreationTimeMillis(taskSnapshot.getMetadata().getCreationTimeMillis())
                    .setDownloadUrl(new DownloadUrl(new Consumer<Consumer<String>>() {
                        @Override
                        public void accept(final Consumer<String> stringConsumer) {
                            firebaseStorage().getReference(taskSnapshot.getMetadata().getPath()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
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
