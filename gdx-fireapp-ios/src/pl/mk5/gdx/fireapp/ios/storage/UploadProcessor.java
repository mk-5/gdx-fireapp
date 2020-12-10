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

import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.storage.FIRStorageMetadata;
import org.robovm.pods.firebase.storage.FIRStorageReference;
import org.robovm.pods.firebase.storage.FIRStorageUploadTask;
import org.robovm.rt.bro.ptr.BytePtr;

import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

class UploadProcessor {

    void upload(final FIRStorageReference firStorageReference, String path, FileHandle file, final FuturePromise<FileMetadata> promise) {
        NSData nsData = NSData.read(file.file());
        FIRStorageUploadTask uploadTask = firStorageReference.child(path).putData(nsData, null, new VoidBlock2<FIRStorageMetadata, NSError>() {
            @Override
            public void invoke(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                FileMetadata fileMetadata = new FileMetadataBuilder(arg0).build(firStorageReference);
                promise.doComplete(fileMetadata);
            }
        });
    }

    void upload(final FIRStorageReference firStorageReference, String path, byte[] data, final FuturePromise<FileMetadata> promise) {
        NSData nsData = new NSData(data);
        FIRStorageUploadTask uploadTask = firStorageReference.child(path).putData(nsData, null, new VoidBlock2<FIRStorageMetadata, NSError>() {
            @Override
            public void invoke(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                FileMetadata fileMetadata = new FileMetadataBuilder(arg0).build(firStorageReference);
                promise.doComplete(fileMetadata);
            }
        });
    }
}
