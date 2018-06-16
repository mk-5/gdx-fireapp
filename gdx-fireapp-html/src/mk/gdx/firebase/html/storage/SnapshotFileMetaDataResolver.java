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

import mk.gdx.firebase.storage.FileMetadata;
import mk.gdx.firebase.storage.functional.DownloadUrl;

/**
 * Resolves transformation from UploadTaskSnapshot to FileMetadata
 */
public class SnapshotFileMetaDataResolver
{

    /**
     * Resolves conversion from UploadTaskSnapshot to FileMetadata resolve.
     *
     * @param snapshot UploadTaskSnapshot from javascript, not null
     * @return FileMetadata object filled with data
     */
    public static FileMetadata resolve(UploadTaskSnapshot snapshot)
    {
        FileMetadata.Builder builder = new FileMetadata.Builder()
                .setDownloadUrl(new DownloadUrl(snapshot.getDownloadURL()));
        FullMetaData metaData = snapshot.getMetaData();
        if (metaData != null) {
            builder.setMd5Hash(metaData.getMD5Hash())
                    .setName(metaData.getName())
                    .setPath(metaData.getPath())
                    .setSizeBytes((long) metaData.getSizeBytes());
            builder.setUpdatedTimeMillis((long) metaData.getTimeUpdatedMillis());
            builder.setCreationTimeMillis((long) metaData.getTimeCreatedMillis());
        }
        return builder.build();
    }
}
