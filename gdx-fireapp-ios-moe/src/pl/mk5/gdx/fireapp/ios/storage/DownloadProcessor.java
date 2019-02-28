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

import org.moe.natj.objc.ObjCRuntime;

import java.io.File;

import apple.foundation.NSData;
import apple.foundation.NSError;
import apple.foundation.NSURL;
import apple.foundation.c.Foundation;
import bindings.google.firebasestorage.FIRStorageDownloadTask;
import bindings.google.firebasestorage.FIRStorageReference;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class DownloadProcessor {

    void processDownload(FIRStorageReference firStorageReference, final String path, long bytesLimit, final FuturePromise<byte[]> promise) {
        firStorageReference.child(path).dataWithMaxSizeCompletion(bytesLimit, new FIRStorageReference.Block_dataWithMaxSizeCompletion() {
            @Override
            public void call_dataWithMaxSizeCompletion(final NSData arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                ObjCRuntime.autoreleasepool(new Runnable() { // TODO - check this autorelease pool
                    @Override
                    public void run() {
                        int length = (int) arg0.length();
                        byte[] data = new byte[length];
                        arg0.bytes().getBytePtr().copyTo(data);
                        promise.doComplete(data);
                    }
                });
            }
        });
    }

    void processDownload(FIRStorageReference firStorageReference, final String path, FileHandle targetFile, final FuturePromise<FileHandle> promise) {
        NSURL targetFileUrl;
        if (targetFile == null) {
            // Create temporary file
            targetFileUrl = NSURL.fileURLWithPathIsDirectory(Foundation.NSTemporaryDirectory(), true).URLByAppendingPathComponent("" + System.nanoTime() + "_file");
        } else {
            targetFileUrl = NSURL.fileURLWithPath(targetFile.file().getAbsolutePath());
        }
        FIRStorageDownloadTask downloadTask = firStorageReference.child(path).writeToFileCompletion(targetFileUrl, new FIRStorageReference.Block_writeToFileCompletion() {
            @Override
            public void call_writeToFileCompletion(NSURL arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                File file = new File(arg0.path());
                promise.doComplete(new FileHandle(file));
            }
        });
    }
}
