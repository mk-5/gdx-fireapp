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

package mk.gdx.firebase.ios.storage;

import com.badlogic.gdx.files.FileHandle;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ptr.BytePtr;
import org.moe.natj.general.ptr.impl.PtrFactory;
import org.moe.natj.objc.ObjCRuntime;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.io.File;

import apple.foundation.NSData;
import apple.foundation.NSDate;
import apple.foundation.NSError;
import apple.foundation.NSURL;
import apple.foundation.c.Foundation;
import bindings.google.firebasestorage.FIRStorage;
import bindings.google.firebasestorage.FIRStorageDownloadTask;
import bindings.google.firebasestorage.FIRStorageMetadata;
import bindings.google.firebasestorage.FIRStorageReference;
import bindings.google.firebasestorage.FIRStorageUploadTask;
import mk.gdx.firebase.ios.GdxIOSAppTest;

import static org.mockito.Mockito.when;

@PrepareForTest({
        NatJ.class, FIRStorageReference.class, FIRStorageMetadata.class, NSError.class,
        NSData.class, FIRStorageUploadTask.class, ObjCRuntime.class, FIRStorageDownloadTask.class,
        NSURL.class, FIRStorage.class, PtrFactory.class, Foundation.class, NSDate.class
})
public class StorageTest extends GdxIOSAppTest {

    private FIRStorage firStorage;
    private FIRStorageReference firStorageReference;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRStorageReference.class);
        PowerMockito.mockStatic(FIRStorageMetadata.class);
        PowerMockito.mockStatic(NSError.class);
        PowerMockito.mockStatic(NSData.class);
        PowerMockito.mockStatic(FIRStorageUploadTask.class);
        PowerMockito.mockStatic(ObjCRuntime.class);
        PowerMockito.mockStatic(FIRStorageDownloadTask.class);
        PowerMockito.mockStatic(NSURL.class);
        PowerMockito.mockStatic(PtrFactory.class);
        PowerMockito.mockStatic(Foundation.class);
        PowerMockito.mockStatic(FIRStorage.class);
        PowerMockito.mockStatic(NSDate.class);
        try {
            PowerMockito.doAnswer(new Answer() {
                @Override
                public Object answer(InvocationOnMock invocation) {
                    ((Runnable) invocation.getArgument(0)).run();
                    return null;
                }
            }).when(ObjCRuntime.class, "autoreleasepool", Mockito.any(Runnable.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        firStorage = Mockito.mock(FIRStorage.class);
        firStorageReference = Mockito.mock(FIRStorageReference.class);
        when(FIRStorage.storage()).thenReturn(firStorage);
        when(firStorage.referenceForURL(Mockito.anyString())).thenReturn(firStorageReference);
        when(firStorage.reference()).thenReturn(firStorageReference);
        when(firStorageReference.child(Mockito.anyString())).thenReturn(firStorageReference);
        when(PtrFactory.newByteArray(Mockito.any(byte[].class))).thenReturn(Mockito.mock(BytePtr.class));
    }

    @Test
    public void upload() {
        // Given
        NSData nsData = PowerMockito.mock(NSData.class);
        when(NSData.dataWithContentsOfFile(Mockito.anyString())).thenReturn(nsData);
        FileHandle fileHandle = Mockito.mock(FileHandle.class);
        when(fileHandle.file()).thenReturn(Mockito.mock(File.class));
        Storage storage = new Storage();
        final FIRStorageMetadata firStorageMetadata = Mockito.mock(FIRStorageMetadata.class);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRStorageReference.Block_putDataMetadataCompletion) invocation.getArgument(2)).call_putDataMetadataCompletion(firStorageMetadata, null);
                return null;
            }
        }).when(firStorageReference).putDataMetadataCompletion(Mockito.any(NSData.class), Mockito.any(FIRStorageMetadata.class), Mockito.any(FIRStorageReference.Block_putDataMetadataCompletion.class));
        when(FIRStorageMetadata.alloc()).thenReturn(firStorageMetadata);
        when(firStorageMetadata.init()).thenReturn(firStorageMetadata);
        when(firStorageMetadata.updated()).thenReturn(Mockito.mock(NSDate.class));
        when(firStorageMetadata.timeCreated()).thenReturn(Mockito.mock(NSDate.class));


        // When
        storage.upload("test", fileHandle).exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).putDataMetadataCompletion(Mockito.nullable(NSData.class), Mockito.nullable(FIRStorageMetadata.class), Mockito.any(FIRStorageReference.Block_putDataMetadataCompletion.class));
    }

    @Test
    public void upload1() {
        // Given
        NSData nsData = PowerMockito.mock(NSData.class);
        when(NSData.dataWithBytesNoCopyLength(Mockito.any(BytePtr.class), Mockito.anyLong())).thenReturn(nsData);
        byte[] data = new byte[]{0, 0, 0, 0, 1, 1, 1, 1};
        Storage storage = new Storage();


        // When
        storage.upload("test", data).exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).putDataMetadataCompletion(Mockito.nullable(NSData.class), Mockito.nullable(FIRStorageMetadata.class), Mockito.any(FIRStorageReference.Block_putDataMetadataCompletion.class));
    }

    @Test
    public void download_withTargetFile() {
        // Given
        NSURL nsData = PowerMockito.mock(NSURL.class);
        when(NSURL.fileURLWithPath(Mockito.anyString())).thenReturn(nsData);
        FileHandle file = Mockito.mock(FileHandle.class);
        when(file.file()).thenReturn(Mockito.mock(File.class));
        Storage storage = new Storage();

        // When
        storage.download("/test", file).exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).writeToFileCompletion(Mockito.nullable(NSURL.class), Mockito.any(FIRStorageReference.Block_writeToFileCompletion.class));
    }

    @Test
    public void download_withNullTargetFile() {
        // Given
        NSURL nsData = Mockito.mock(NSURL.class);
        when(NSURL.fileURLWithPathIsDirectory(Mockito.nullable(String.class), Mockito.anyBoolean())).thenReturn(nsData);
        FileHandle file = null;
        Storage storage = new Storage();

        // When
        storage.download("/test", file).exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).writeToFileCompletion(Mockito.nullable(NSURL.class), Mockito.any(FIRStorageReference.Block_writeToFileCompletion.class));
        PowerMockito.verifyStatic(Foundation.class, VerificationModeFactory.times(1));
        Foundation.NSTemporaryDirectory();
    }

    @Test
    public void download1() {
        // Given
        NSURL nsData = PowerMockito.mock(NSURL.class);
        long byteLimit = Long.MAX_VALUE;
        Storage storage = new Storage();

        // When
        storage.download("/test", byteLimit).exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).dataWithMaxSizeCompletion(Mockito.anyLong(), Mockito.any(FIRStorageReference.Block_dataWithMaxSizeCompletion.class));
    }

    @Test
    public void delete() {
        // Given
        NSURL nsData = PowerMockito.mock(NSURL.class);
        long byteLimit = Long.MAX_VALUE;
        Storage storage = new Storage();

        // When
        storage.delete("/test").exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).deleteWithCompletion(Mockito.any(FIRStorageReference.Block_deleteWithCompletion.class));
    }

    @Test
    public void delete_error() {
        // Given
        NSURL nsData = PowerMockito.mock(NSURL.class);
        long byteLimit = Long.MAX_VALUE;
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((FIRStorageReference.Block_deleteWithCompletion) invocation.getArgument(0)).call_deleteWithCompletion(Mockito.mock(NSError.class));
                return null;
            }
        }).when(firStorageReference).deleteWithCompletion(Mockito.any(FIRStorageReference.Block_deleteWithCompletion.class));
        Storage storage = new Storage();

        // When
        storage.delete("/test").exec();

        // Then
        Mockito.verify(firStorageReference, VerificationModeFactory.times(1)).deleteWithCompletion(Mockito.any(FIRStorageReference.Block_deleteWithCompletion.class));
    }

    @Test
    public void inBucket() {
        // Given
        String testPath = "/test";
        Storage storage = new Storage();

        // When
        storage.inBucket(testPath);

        // Then
        Mockito.verify(firStorage, VerificationModeFactory.times(1)).referenceForURL(testPath);
    }
}