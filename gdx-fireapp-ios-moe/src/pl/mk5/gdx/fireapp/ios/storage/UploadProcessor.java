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

package pl.mk5.gdx.fireapp.ios.storage;

import com.badlogic.gdx.files.FileHandle;

import org.moe.natj.general.ptr.BytePtr;
import org.moe.natj.general.ptr.impl.PtrFactory;

import apple.foundation.NSData;
import apple.foundation.NSError;
import bindings.google.firebasestorage.FIRStorageMetadata;
import bindings.google.firebasestorage.FIRStorageReference;
import bindings.google.firebasestorage.FIRStorageUploadTask;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

class UploadProcessor {

    void upload(final FIRStorageReference firStorageReference, String path, FileHandle file, final FuturePromise<FileMetadata> promise) {
        NSData nsData = NSData.dataWithContentsOfFile(file.file().getAbsolutePath());
        FIRStorageUploadTask uploadTask = firStorageReference.child(path).putDataMetadataCompletion(nsData, null, new FIRStorageReference.Block_putDataMetadataCompletion() {
            @Override
            public void call_putDataMetadataCompletion(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                FileMetadata fileMetadata = new FileMetadataBuilder(arg0).build(firStorageReference);
                promise.doComplete(fileMetadata);
            }
        });
    }

    void upload(final FIRStorageReference firStorageReference, String path, byte[] data, final FuturePromise<FileMetadata> promise) {
        final BytePtr bytePtr = PtrFactory.newByteArray(data);
        NSData nsData = NSData.dataWithBytesNoCopyLength(bytePtr, data.length);
        FIRStorageUploadTask uploadTask = firStorageReference.child(path).putDataMetadataCompletion(nsData, null, new FIRStorageReference.Block_putDataMetadataCompletion() {
            @Override
            public void call_putDataMetadataCompletion(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                FileMetadata fileMetadata = new FileMetadataBuilder(arg0).build(firStorageReference);
                promise.doComplete(fileMetadata);
                bytePtr.free();
            }
        });
    }
}
