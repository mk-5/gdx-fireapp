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

package mk.gdx.firebase.html.storage;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.html.GdxHtmlAppTest;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.storage.FileMetadata;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, UploadTaskSnapshot.class, SnapshotFileMetaDataResolver.class})
public class StorageJSTest extends GdxHtmlAppTest {

    @Override
    public void setup() throws Exception {
        super.setup();
        PowerMockito.mockStatic(UploadTaskSnapshot.class);
        PowerMockito.mockStatic(SnapshotFileMetaDataResolver.class);
    }

    @Test
    public void callUploadCallback() {
        // Given
        FuturePromise promise = Mockito.mock(FuturePromise.class);
        UploadTaskSnapshot snapshot = Mockito.mock(UploadTaskSnapshot.class);
        FileMetadata fileMetadata = Mockito.mock(FileMetadata.class);
        Mockito.when(SnapshotFileMetaDataResolver.resolve(Mockito.refEq(snapshot))).thenReturn(fileMetadata);

        // When
        StorageJS.callUploadPromise(snapshot, promise);

        // Then
        Mockito.verify(promise, VerificationModeFactory.times(1)).doComplete(Mockito.refEq(fileMetadata));
    }
}