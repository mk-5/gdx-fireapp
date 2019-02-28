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

package pl.mk5.gdx.fireapp.html.storage;

import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.storage.DownloadUrl;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

/**
 * Resolves transformation from UploadTaskSnapshot to FileMetadata
 */
class SnapshotFileMetaDataResolver {

    private SnapshotFileMetaDataResolver() {
        //
    }


    /**
     * Resolves conversion from UploadTaskSnapshot to FileMetadata resolve.
     *
     * @param snapshot UploadTaskSnapshot from javascript, not null
     * @return FileMetadata object filled with data
     */
    static FileMetadata resolve(final UploadTaskSnapshot snapshot) {
        FileMetadata.Builder builder = new FileMetadata.Builder();
        FullMetaData metaData = snapshot.getMetaData();
        if (metaData != null) {
            builder.setMd5Hash(metaData.getMD5Hash())
                    .setName(metaData.getName())
                    .setPath(metaData.getPath())
                    .setSizeBytes((long) metaData.getSizeBytes());
            builder.setUpdatedTimeMillis((long) metaData.getTimeUpdatedMillis());
            builder.setCreationTimeMillis((long) metaData.getTimeCreatedMillis());
            builder.setDownloadUrl(new DownloadUrl(new Consumer<Consumer<String>>() {
                @Override
                public void accept(Consumer<String> urlConsumer) {
                    snapshot.downloadUrl(urlConsumer);
                }
            }));
        }
        return builder.build();
    }
}
