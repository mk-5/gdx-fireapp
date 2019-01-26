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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import mk.gdx.firebase.html.GdxHtmlAppTest;
import mk.gdx.firebase.html.firebase.ScriptRunner;
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({
        ClassReflection.class, Constructor.class, ScriptInjector.class,
        ScriptRunner.class, StorageJS.class, Base64Coder.class
})
public class StorageTest extends GdxHtmlAppTest {

    @Before
    public void setUp() throws Exception {
        super.setup();
        PowerMockito.mockStatic(StorageJS.class);
        PowerMockito.mockStatic(Base64Coder.class);
        PowerMockito.mockStatic(ScriptRunner.class);
        PowerMockito.when(ScriptRunner.class, "firebaseScript", Mockito.any(Runnable.class)).then(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((Runnable) invocation.getArgument(0)).run();
                return null;
            }
        });
    }

    @Test
    public void upload() {
        // Given
        Storage storage = new Storage();

        // When
        storage.upload(Mockito.mock(FileHandle.class), "test");

        // Then
        Mockito.verify(Gdx.app, VerificationModeFactory.times(1)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void upload1() {
        // Given
        byte[] data = {0, 0, 0, 0, 1, 1, 1, 1, 0};
        String path = "/test";
        String encodedData = "base64_encoded_data";
        Mockito.when(Base64Coder.encode(Mockito.eq(data))).thenReturn(encodedData.toCharArray());
        Storage storage = new Storage();


        // When
        storage.upload(data, path);

        // Then
        PowerMockito.verifyStatic(StorageJS.class, VerificationModeFactory.times(1));
        StorageJS.upload(Mockito.anyString(), Mockito.eq(path), Mockito.eq(encodedData), Mockito.any(FuturePromise.class));
    }

    @Test
    public void download() {
        // Given
        Storage storage = new Storage();

        // When
        storage.download("test", Mockito.mock(FileHandle.class));

        // Then
        Mockito.verify(Gdx.app, VerificationModeFactory.times(1)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void download1() {
        // Given
        String path = "/test";
        long bytesLimit = 1000;
        Storage storage = new Storage();


        // When
        storage.download(path, bytesLimit);

        // Then
        PowerMockito.verifyStatic(StorageJS.class, VerificationModeFactory.times(1));
        StorageJS.download(Mockito.anyString(), Mockito.eq(path), Mockito.any(UrlDownloader.class));
    }

    @Test
    public void delete() {
        // Given
        String path = "/test";
        Storage storage = new Storage();


        // When
        storage.delete(path);

        // Then
        PowerMockito.verifyStatic(StorageJS.class, VerificationModeFactory.times(1));
        StorageJS.remove(Mockito.anyString(), Mockito.eq(path), Mockito.any(FuturePromise.class));
    }

    @Test
    public void inBucket() {
        // Given
        String path = "path";
        Storage storage = new Storage();

        // When
        storage.inBucket(path);

        // Then
        Assert.assertEquals(path, Whitebox.getInternalState(storage, "bucketUrl"));
    }
}