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

package mk.gdx.firebase.ios.storage;

import com.badlogic.gdx.files.FileHandle;

import apple.foundation.NSError;
import bindings.google.firebasestorage.FIRStorage;
import bindings.google.firebasestorage.FIRStorageReference;
import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * iOS Firebase storage API implementation.
 * <p>
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution {

    private FIRStorageReference firStorage;
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
                uploadProcessor.upload(firStorage(), path, file, promise);
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
                uploadProcessor.upload(firStorage(), path, data, promise);
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
            public void accept(FuturePromise<byte[]> promise) {
                downloadProcessor.processDownload(firStorage(), path, bytesLimit, promise);
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
            public void accept(FuturePromise<FileHandle> promise) {
                downloadProcessor.processDownload(firStorage(), path, targetFile, promise);
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
                firStorage().child(path).deleteWithCompletion(new FIRStorageReference.Block_deleteWithCompletion() {
                    @Override
                    public void call_deleteWithCompletion(NSError arg0) {
                        if (ErrorHandler.handleError(arg0, voidFuturePromise)) return;
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
        firStorage = FIRStorage.storage().referenceForURL(url);
        return this;
    }

    /**
     * @return Lazy loaded instance of {@link FIRStorageReference}. It should be only one instance for this object, not null.
     */
    private FIRStorageReference firStorage() {
        if (firStorage == null)
            firStorage = FIRStorage.storage().reference();
        return firStorage;
    }
}
