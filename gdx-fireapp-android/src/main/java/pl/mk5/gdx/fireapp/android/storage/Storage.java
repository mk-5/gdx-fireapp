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

package pl.mk5.gdx.fireapp.android.storage;

import android.support.annotation.NonNull;

import com.badlogic.gdx.files.FileHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import pl.mk5.gdx.fireapp.distributions.StorageDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

/**
 * Android Firebase storage API implementation.
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution {

    private FirebaseStorage firebaseStorage;
    private static final UploadProcessor uploadProcessor = new UploadProcessor();
    private static final DownloadProcessor downloadProcessor = new DownloadProcessor();

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileMetadata> upload(final String path, final FileHandle file) {
        return FuturePromise.when(new Consumer<FuturePromise<FileMetadata>>() {
            @Override
            public void accept(FuturePromise<FileMetadata> promise) {
                uploadProcessor.processUpload(firebaseStorage(), path, file, promise);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileMetadata> upload(final String path, final byte[] data) {
        return FuturePromise.when(new Consumer<FuturePromise<FileMetadata>>() {
            @Override
            public void accept(FuturePromise<FileMetadata> promise) {
                uploadProcessor.processUpload(firebaseStorage(), path, data, promise);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<byte[]> download(final String path, final long bytesLimit) {
        return FuturePromise.when(new Consumer<FuturePromise<byte[]>>() {
            @Override
            public void accept(FuturePromise<byte[]> futurePromise) {
                downloadProcessor.processDownload(firebaseStorage(), path, bytesLimit, futurePromise);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileHandle> download(final String path, final FileHandle targetFile) {
        return FuturePromise.when(new Consumer<FuturePromise<FileHandle>>() {
            @Override
            public void accept(FuturePromise<FileHandle> fileHandleFuturePromise) {
                downloadProcessor.processDownload(firebaseStorage(), path, targetFile, fileHandleFuturePromise);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> delete(final String path) {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
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
}
