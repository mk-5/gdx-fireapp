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

import apple.foundation.NSError;
import apple.foundation.NSURL;
import bindings.google.firebasestorage.FIRStorageMetadata;
import bindings.google.firebasestorage.FIRStorageReference;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.storage.DownloadUrl;
import mk.gdx.firebase.storage.FileMetadata;

class FileMetadataBuilder {

    private final FIRStorageMetadata firMetadata;

    FileMetadataBuilder(FIRStorageMetadata firMetadata) {
        this.firMetadata = firMetadata;
    }

    /**
     * Transforms {@code FIRStorageMetadata} to {@code FileMetadata}.
     * <p>
     * Transformation is needed because of need of shared code between modules.
     *
     * @return FileMetadata created of base of given {@code firMetadata}, not null.
     */
    FileMetadata build(final FIRStorageReference firStorage) {
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
                        firStorage.child(firMetadata.path()).downloadURLWithCompletion(new FIRStorageReference.Block_downloadURLWithCompletion() {
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
