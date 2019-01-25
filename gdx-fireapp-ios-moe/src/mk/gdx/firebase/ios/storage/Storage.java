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

package mk.gdx.firebase.ios.storage;

import com.badlogic.gdx.files.FileHandle;

import org.moe.natj.general.ptr.BytePtr;
import org.moe.natj.general.ptr.impl.PtrFactory;
import org.moe.natj.objc.ObjCRuntime;

import java.io.File;

import apple.foundation.NSData;
import apple.foundation.NSError;
import apple.foundation.NSURL;
import apple.foundation.c.Foundation;
import bindings.google.firebasestorage.FIRStorage;
import bindings.google.firebasestorage.FIRStorageDownloadTask;
import bindings.google.firebasestorage.FIRStorageMetadata;
import bindings.google.firebasestorage.FIRStorageReference;
import bindings.google.firebasestorage.FIRStorageUploadTask;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.DownloadUrl;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * iOS Firebase storage API implementation.
 * <p>
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution {

    private FIRStorageReference firStorage;

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(FileHandle file, String path, final UploadCallback callback) {
        NSData nsData = NSData.dataWithContentsOfFile(file.file().getAbsolutePath());
        FIRStorageUploadTask uploadTask = firStorage().child(path).putDataMetadataCompletion(nsData, null, new FIRStorageReference.Block_putDataMetadataCompletion() {
            @Override
            public void call_putDataMetadataCompletion(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleUploadError(arg1, callback)) return;
                FileMetadata fileMetadata = buildMetaData(arg0);
                callback.onSuccess(fileMetadata);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(byte[] data, String path, final UploadCallback callback) {
        final BytePtr bytePtr = PtrFactory.newByteArray(data);
//        NSData nsData = NSData.dataWithBytesLength(bytePtr, data.length);
        NSData nsData = NSData.dataWithBytesNoCopyLength(bytePtr, data.length);
        FIRStorageUploadTask uploadTask = firStorage().child(path).putDataMetadataCompletion(nsData, null, new FIRStorageReference.Block_putDataMetadataCompletion() {
            @Override
            public void call_putDataMetadataCompletion(FIRStorageMetadata arg0, NSError arg1) {
                if (ErrorHandler.handleUploadError(arg1, callback)) return;
                FileMetadata fileMetadata = buildMetaData(arg0);
                callback.onSuccess(fileMetadata);
                bytePtr.free();
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void download(String path, final long bytesLimit, final DownloadCallback<byte[]> callback) {
        firStorage().child(path).dataWithMaxSizeCompletion(bytesLimit, new FIRStorageReference.Block_dataWithMaxSizeCompletion() {
            @Override
            public void call_dataWithMaxSizeCompletion(final NSData arg0, NSError arg1) {
                if (ErrorHandler.handleDownloadError(arg1, callback)) return;
                ObjCRuntime.autoreleasepool(new Runnable() { // TODO - check this autorelease pool
                    @Override
                    public void run() {
                        int length = (int) arg0.length();
                        byte[] data = new byte[length];
                        arg0.bytes().getBytePtr().copyTo(data);
                        callback.onSuccess(data);
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void download(String path, File targetFile, final DownloadCallback<File> callback) {
        NSURL targetFileUrl;
        if (targetFile == null) {
            // Create temporary file
            targetFileUrl = NSURL.fileURLWithPathIsDirectory(Foundation.NSTemporaryDirectory(), true).URLByAppendingPathComponent("" + System.nanoTime() + "_file");
        } else {
            targetFileUrl = NSURL.fileURLWithPath(targetFile.getAbsolutePath());
        }
        FIRStorageDownloadTask downloadTask = firStorage().child(path).writeToFileCompletion(targetFileUrl, new FIRStorageReference.Block_writeToFileCompletion() {
            @Override
            public void call_writeToFileCompletion(NSURL arg0, NSError arg1) {
                if (ErrorHandler.handleDownloadError(arg1, callback)) return;
                File file = new File(arg0.path());
                callback.onSuccess(file);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> delete(final String path) {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                firStorage().child(path).deleteWithCompletion(new FIRStorageReference.Block_deleteWithCompletion() {
                    @Override
                    public void call_deleteWithCompletion(NSError arg0) {
                        if (ErrorHandler.handleDeleteError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StorageDistribution inBucket(String url) {
        firStorage = FIRStorage.storage().referenceForURL(url);
        return this;
    }

    /**
     * @return Lazy loaded instance of {@link FIRStorageReference}. It should be only one instance for this object, not null.
     */
    private FIRStorageReference firStorage() {
        if (firStorage == null)
            firStorage = FIRStorage.storage().reference();
        return firStorage;
    }

    /**
     * Transforms {@code FIRStorageMetadata} to {@code FileMetadata}.
     * <p>
     * Transformation is needed because of need of shared code between modules.
     *
     * @param firMetadata FIRStorageMetadata that you want to wrap by {@link FileMetadata}
     * @return FileMetadata created of base of given {@code firMetadata}, not null.
     */
    private FileMetadata buildMetaData(final FIRStorageMetadata firMetadata) {
        // UpdateTimeMillis is specified in seconds so have to multiply it by 1000.
        /** https://developer.apple.com/documentation/foundation/timeinterval */
        FIRStorageMetadata m = FIRStorageMetadata.alloc().init();
        //  TODO - missing md5Hash
        return new FileMetadata.Builder()
                .setName(firMetadata.name())
                .setUpdatedTimeMillis((long) (firMetadata.updated().timeIntervalSince1970() * 1000L))
                .setSizeBytes(firMetadata.size())
                .setPath(firMetadata.path())
                .setCreationTimeMillis((long) (firMetadata.timeCreated().timeIntervalSince1970() * 1000L))
                .setDownloadUrl(new DownloadUrl(new Consumer<Consumer<String>>() {
                    @Override
                    public void accept(final Consumer<String> urlConsumer) {
                        firStorage().child(firMetadata.path()).downloadURLWithCompletion(new FIRStorageReference.Block_downloadURLWithCompletion() {
                            @Override
                            public void call_downloadURLWithCompletion(NSURL arg0, NSError arg1) {
                                if (arg1 != null)
                                    throw new RuntimeException(arg1.localizedDescription());
                                urlConsumer.accept(arg0.absoluteURL().absoluteString());
                            }
                        });
                    }
                }))
                .setMd5Hash("")
                .build();
    }

}
