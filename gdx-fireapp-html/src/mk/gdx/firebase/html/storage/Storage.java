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

package mk.gdx.firebase.html.storage;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;

import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.html.firebase.ScriptRunner;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * GWT Firebase storage api.
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution {

    private String bucketUrl = "";

    /**
     * Not supported in WEB api - you do not have access to file system.
     *
     * @param file Source file
     * @param path Path
     */
    @Override
    public Promise<FileMetadata> upload(FileHandle file, String path) {
        Gdx.app.error("GdxFireapp", "This method is not supported in Firebase Web API");
        return FuturePromise.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileMetadata> upload(final byte[] data, final String path) {
        return FuturePromise.when(new Consumer<FuturePromise<FileMetadata>>() {
            @Override
            public void accept(final FuturePromise<FileMetadata> promise) {
                ScriptRunner.firebaseScript(new ScriptRunner.ScriptStorageAction(bucketUrl()) {
                    @Override
                    public void run() {
                        StorageJS.upload(scriptBucketUrl, path, new String(Base64Coder.encode(data)), promise);
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<byte[]> download(final String path, long bytesLimit) {
        return FuturePromise.when(new Consumer<FuturePromise<byte[]>>() {
            @Override
            public void accept(final FuturePromise<byte[]> futurePromise) {
                ScriptRunner.firebaseScript(new ScriptRunner.ScriptStorageAction(bucketUrl()) {
                    @Override
                    public void run() {
                        StorageJS.download(scriptBucketUrl, path, new UrlDownloader(futurePromise));
                    }
                });
            }
        });
    }

    /**
     * Not supported in WEB api - you do not have access to file system.
     *
     * @param path       Path
     * @param targetFile Target file, if null the temporary file will be created.
     */
    @Override
    public Promise<FileHandle> download(String path, FileHandle targetFile) {
        Gdx.app.error("GdxFireapp", "This method is not supported in Firebase Web API");
        return FuturePromise.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> delete(final String path) {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> futurePromise) {
                ScriptRunner.firebaseScript(new ScriptRunner.ScriptStorageAction(bucketUrl()) {
                    @Override
                    public void run() {
                        StorageJS.remove(scriptBucketUrl, path, futurePromise);
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
        bucketUrl = url;
        return this;
    }

    private String bucketUrl() {
        return bucketUrl != null ? bucketUrl : "";
    }
}
