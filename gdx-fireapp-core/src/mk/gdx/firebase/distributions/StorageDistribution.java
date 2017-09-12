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

package mk.gdx.firebase.distributions;

import com.badlogic.gdx.files.FileHandle;

import java.io.File;

import mk.gdx.firebase.callbacks.DeleteCallback;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;

/**
 *
 */
public interface StorageDistribution {
    /**
     * @param file     File you want to upload
     * @param path     Target path at Firebase storage
     * @param callback Callback
     */
    void upload(FileHandle file, String path, UploadCallback callback);

    /**
     * @param data     Data to upload
     * @param path     Target pat at Firebase storage
     * @param callback Callback
     */
    void upload(byte[] data, String path, UploadCallback callback);

    /**
     * TODO - path type detection.
     *
     * @param path       Storage path
     * @param bytesLimit Bytes size
     * @param callback   Callback
     */
    void download(String path, long bytesLimit, DownloadCallback<byte[]> callback);

    /**
     * @param path       Path
     * @param targetFile Target file, if null the temporary file will be created.
     * @param callback   Callback
     */
    void download(String path, File targetFile, DownloadCallback<File> callback);


    /**
     * @param path     Path
     * @param callback Callback
     */
    void delete(String path, DeleteCallback callback);

    /**
     * @param url Bucket url 'gs://'
     * @return this
     */
    StorageDistribution inBucket(String url);
}
