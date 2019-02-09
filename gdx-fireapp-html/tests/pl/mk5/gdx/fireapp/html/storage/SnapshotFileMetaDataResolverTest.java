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

package pl.mk5.gdx.fireapp.html.storage;

import com.badlogic.gdx.math.MathUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import pl.mk5.gdx.fireapp.storage.FileMetadata;

@PrepareForTest({UploadTaskSnapshot.class, FullMetaData.class})
public class SnapshotFileMetaDataResolverTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(FullMetaData.class);
        PowerMockito.mockStatic(UploadTaskSnapshot.class);
    }

    @Test
    public void resolve_withoutMetadata() {
        // Given
        UploadTaskSnapshot taskSnapshot = PowerMockito.mock(UploadTaskSnapshot.class);
        Mockito.when(taskSnapshot.getMetaData()).thenReturn(null);

        // When
        FileMetadata fileMetadata = SnapshotFileMetaDataResolver.resolve(taskSnapshot);

        // Then
        Assert.assertNull(fileMetadata.getName());
        Assert.assertNull(fileMetadata.getPath());
    }

    @Test
    public void resolve_withMetadata() {
        // Given
        UploadTaskSnapshot taskSnapshot = PowerMockito.mock(UploadTaskSnapshot.class);
        FullMetaData fullMetaData = PowerMockito.mock(FullMetaData.class);
        Mockito.when(fullMetaData.getName()).thenReturn("name");
        Mockito.when(fullMetaData.getPath()).thenReturn("path");
        Mockito.when(fullMetaData.getMD5Hash()).thenReturn("md5");
        Mockito.when(fullMetaData.getSizeBytes()).thenReturn(10.0);
        Mockito.when(fullMetaData.getTimeCreatedMillis()).thenReturn(11.0);
        Mockito.when(fullMetaData.getTimeUpdatedMillis()).thenReturn(12.0);
        Mockito.when(taskSnapshot.getMetaData()).thenReturn(fullMetaData);

        // When
        FileMetadata fileMetadata = SnapshotFileMetaDataResolver.resolve(taskSnapshot);

        // Then
        Assert.assertEquals("name", fileMetadata.getName());
        Assert.assertEquals("path", fileMetadata.getPath());
        Assert.assertEquals("md5", fileMetadata.getMd5Hash());
        Assert.assertTrue(MathUtils.isEqual((float) 10.0, fileMetadata.getSizeBytes()));
        Assert.assertTrue(MathUtils.isEqual((float) 11.0, fileMetadata.getCreationTimeMillis()));
        Assert.assertTrue(MathUtils.isEqual((float) 12.0, fileMetadata.getUpdatedTimeMillis()));
    }
}