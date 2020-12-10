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

import org.robovm.apple.foundation.NSError;
import org.robovm.apple.foundation.NSURL;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.storage.FIRStorageMetadata;
import org.robovm.pods.firebase.storage.FIRStorageReference;

import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.storage.DownloadUrl;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

class FileMetadataBuilder {

    private final FIRStorageMetadata firMetadata;

    FileMetadataBuilder(FIRStorageMetadata firMetadata) {
        this.firMetadata = firMetadata;
    }

    FileMetadata build(final FIRStorageReference firStorage) {
        // UpdateTimeMillis is specified in seconds so have to multiply it by 1000.
        /** https://developer.apple.com/documentation/foundation/timeinterval */
        FIRStorageMetadata m = new FIRStorageMetadata();
        //  TODO - missing md5Hash
        return new FileMetadata.Builder()
                .setName(firMetadata.getName())
                .setUpdatedTimeMillis((long) (firMetadata.getUpdated().getTimeIntervalSince1970() * 1000L))
                .setSizeBytes(firMetadata.getSize())
                .setPath(firMetadata.getPath())
                .setCreationTimeMillis((long) (firMetadata.getTimeCreated().getTimeIntervalSince1970() * 1000L))
                .setDownloadUrl(new DownloadUrl(new Consumer<Consumer<String>>() {
                    @Override
                    public void accept(final Consumer<String> urlConsumer) {
                        firStorage.child(firMetadata.getPath()).downloadURL(new VoidBlock2<NSURL, NSError>() {
                            @Override
                            public void invoke(NSURL arg0, NSError arg1) {
                                if (arg1 != null)
                                    throw new RuntimeException(arg1.getLocalizedDescription());
                                urlConsumer.accept(arg0.getFragment());
                            }
                        });
                    }
                }))
                .setMd5Hash("")
                .build();
    }
}
