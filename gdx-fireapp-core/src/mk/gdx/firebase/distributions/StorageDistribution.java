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

import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * Provides access to Firebase Storage methods.
 */
public interface StorageDistribution {

    /**
     * Uploads file data to storage.
     *
     * @param path Target path at Firebase storage
     * @param file File you want to upload
     */
    Promise<FileMetadata> upload(String path, FileHandle file);

    /**
     * Uploads byte array data to storage.
     *
     * @param path Target pat at Firebase storage
     * @param data Data to upload
     */
    Promise<FileMetadata> upload(String path, byte[] data);

    /**
     * Downloads data as byte array.
     *
     * @param path       Storage path
     * @param bytesLimit Bytes size
     */
    Promise<byte[]> download(String path, long bytesLimit);

    /**
     * Downloads data to file given by {@code targetFile}.
     *
     * @param path       Path
     * @param targetFile Target file, if null the temporary file will be create
     */
    Promise<FileHandle> download(String path, FileHandle targetFile);


    /**
     * Deletes data from storage.
     *
     * @param path The reference path
     */
    Promise<Void> delete(String path);

    /**
     * Sets bucket to deal with, url will be used for all future actions.
     *
     * @param url Bucket url 'gs://'
     * @return this
     */
    StorageDistribution inBucket(String url);
}
