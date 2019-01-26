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

package mk.gdx.firebase.storage;

import org.junit.Assert;
import org.junit.Test;

import mk.gdx.firebase.functional.Consumer;

public class FileMetadataTest {

    @Test
    public void test_builderReturnsValidInstance() {
        // Given
        FileMetadata.Builder builder = new FileMetadata.Builder();

        // When
        builder.setCreationTimeMillis(1);
        builder.setDownloadUrl(new DownloadUrl("url"));
        builder.setMd5Hash("md5");
        builder.setName("name");
        builder.setPath("path");
        builder.setSizeBytes(2);
        builder.setUpdatedTimeMillis(3);
        final FileMetadata fileMetadata = builder.build();

        // Then
        Assert.assertEquals(fileMetadata.getCreationTimeMillis(), 1);
        Assert.assertNotNull(fileMetadata.getDownloadUrl());
        fileMetadata.getDownloadUrl().getUrl(new Consumer<String>() {
            @Override
            public void accept(String s) {
                Assert.assertEquals(s, "url");
            }
        });
        Assert.assertEquals("md5", fileMetadata.getMd5Hash());
        Assert.assertEquals("name", fileMetadata.getName());
        Assert.assertEquals("path", fileMetadata.getPath());
        Assert.assertEquals(2, fileMetadata.getSizeBytes());
        Assert.assertEquals(3, fileMetadata.getUpdatedTimeMillis());
    }
}