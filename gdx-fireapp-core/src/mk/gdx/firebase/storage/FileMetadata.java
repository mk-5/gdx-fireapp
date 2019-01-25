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

package mk.gdx.firebase.storage;

/**
 * POJO class that holds information about the file from Firebase storage.
 */
public class FileMetadata {
    private DownloadUrl downloadUrl;
    private String name;
    private String path;
    private String md5Hash;
    private long sizeBytes;
    private long creationTimeMillis;
    private long updatedTimeMillis;

    /**
     * Gets Firebase storage url.
     *
     * @return Url with which you can download file
     */
    public DownloadUrl getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * Gets Firebase file name.
     *
     * @return Firebase file name, may be null
     */
    public String getName() {
        return name;
    }

    /**
     * Gets firebase file path.
     *
     * @return Firebase file path, may be null
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets firebase file md5 hash.
     *
     * @return Firebase file md5 hash, may be null
     */
    public String getMd5Hash() {
        return md5Hash;
    }

    /**
     * Gets firebase file sizes in bytes.
     *
     * @return Firebase file size in bytes
     */
    public long getSizeBytes() {
        return sizeBytes;
    }

    /**
     * Gets creation time in unix timestamp milliseconds.
     *
     * @return Firebase file creation time in unix timestamp milliseconds
     */
    public long getCreationTimeMillis() {
        return creationTimeMillis;
    }

    /**
     * Gets updated time in unix timestamp milliseconds.
     *
     * @return Firebase file update time in unix timestamp milliseconds
     */
    public long getUpdatedTimeMillis() {
        return updatedTimeMillis;
    }

    /**
     * Builder pattern for {@code FileMetadata}.
     */
    public static class Builder {
        private FileMetadata inst = new FileMetadata();

        /**
         * Sets firebase file download url.
         *
         * @param downloadUrl Firebase download url
         * @return this {@code Builder} instance
         */
        public Builder setDownloadUrl(DownloadUrl downloadUrl) {
            inst.downloadUrl = downloadUrl;
            return this;
        }

        /**
         * Sets firebase file name.
         *
         * @param name Firebase file name
         * @return this {@code Builder} instance
         */
        public Builder setName(String name) {
            inst.name = name;
            return this;
        }

        /**
         * Sets firebase file path.
         *
         * @param path Firebase file path
         * @return this {@code Builder} instance
         */
        public Builder setPath(String path) {
            inst.path = path;
            return this;
        }

        /**
         * Sets firebase file md5 hash.
         *
         * @param md5Hash Firebase file md5 hash
         * @return this {@code Builder} instance
         */
        public Builder setMd5Hash(String md5Hash) {
            inst.md5Hash = md5Hash;
            return this;
        }

        /**
         * Sets firebase file size in bytes.
         *
         * @param sizeBytes Firebase file size in bytes
         * @return this {@code Builder} instance
         */
        public Builder setSizeBytes(long sizeBytes) {
            inst.sizeBytes = sizeBytes;
            return this;
        }

        /**
         * Sets firebase file creation time in unix timestamp.
         *
         * @param creationTimeMillis Creation time in unix timestamp milliseconds
         * @return this {@code Builder} instance
         */
        public Builder setCreationTimeMillis(long creationTimeMillis) {
            inst.creationTimeMillis = creationTimeMillis;
            return this;
        }

        /**
         * Sets firebase file update time in unix timestamp.
         *
         * @param updatedTimeMillis Update time in unix timestamp milliseconds
         * @return this {@code Builder} instance
         */
        public Builder setUpdatedTimeMillis(long updatedTimeMillis) {
            inst.updatedTimeMillis = updatedTimeMillis;
            return this;
        }

        /**
         * Gets {@code FileMetadata} instance.
         *
         * @return {@code FileMetadata} instance created by given properties
         */
        public FileMetadata build() {
            return inst;
        }
    }
}
