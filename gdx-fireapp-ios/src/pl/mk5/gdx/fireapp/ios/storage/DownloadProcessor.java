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

import org.robovm.apple.foundation.Foundation;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.foundation.NSData;
import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSFileManager;
import org.robovm.apple.foundation.NSURL;
import org.robovm.objc.ObjCRuntime;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.storage.FIRStorageDownloadTask;
import org.robovm.pods.firebase.storage.FIRStorageReference;

import java.io.File;

import pl.mk5.gdx.fireapp.promises.FuturePromise;

class DownloadProcessor {

    void processDownload(FIRStorageReference firStorageReference, final String path, long bytesLimit, final FuturePromise<byte[]> promise) {
        firStorageReference.child(path).data(bytesLimit, new VoidBlock2<NSData, NSError>() {
            @Override
            public void invoke(final NSData arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                NSAutoreleasePool pool = new NSAutoreleasePool();
                int length = (int) arg0.getLength();
                byte[] data = new byte[length];
                System.arraycopy(arg0.getBytes(), 0, data, 0, arg0.getBytes().length);
                promise.doComplete(data);
                pool.close();
            }
        });
    }

    void processDownload(FIRStorageReference firStorageReference, final String path, FileHandle targetFile, final FuturePromise<FileHandle> promise) {
        NSURL targetFileUrl;
        if (targetFile == null) {
            // Create temporary file
            targetFileUrl = NSFileManager.getDefaultManager().getTemporaryDirectory().newURLByAppendingPathComponent("" + System.nanoTime() + "_file");
        } else {
            targetFileUrl = new NSURL(targetFile.file().getAbsolutePath());
        }
        FIRStorageDownloadTask downloadTask = firStorageReference.child(path).writeToFile(targetFileUrl, new VoidBlock2<NSURL, NSError>() {
            @Override
            public void invoke(NSURL arg0, NSError arg1) {
                if (ErrorHandler.handleError(arg1, promise)) return;
                File file = new File(arg0.getPath());
                promise.doComplete(new FileHandle(file));
            }
        });
    }
}
