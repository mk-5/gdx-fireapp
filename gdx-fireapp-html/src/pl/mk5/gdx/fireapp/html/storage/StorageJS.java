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

package pl.mk5.gdx.fireapp.html.storage;

import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.storage.FileMetadata;

/**
 * Javascript calls to firebase storage api.
 */
class StorageJS {

    private StorageJS() {
        //
    }

    /**
     * Downloads file given at {@code refPath} and convert it to base64 string.
     *
     * @param bucketUrl        Bucket url, may be null
     * @param refPath          Storage reference path, not null
     * @param urlDownloader Callback, not null
     */
    static native void download(String bucketUrl, String refPath, UrlDownloader urlDownloader) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath).getDownloadURL().then(function(url){
             urlDownloader.@pl.mk5.gdx.fireapp.html.storage.UrlDownloader::onSuccess(Ljava/lang/String;)(url);
        })['catch'](function(error){
            urlDownloader.@pl.mk5.gdx.fireapp.html.storage.UrlDownloader::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.message));
        });
    }-*/;

    /**
     * Removes file given at {@code refPath}.
     *
     * @param bucketUrl     Bucket url, may be null
     * @param refPath       Storage reference path, not null
     * @param futurePromise FuturePromise, not null
     */
    static native void remove(String bucketUrl, String refPath, FuturePromise<Void> futurePromise) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath)['delete']().then(function(){
             promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })['catch'](function(error){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    /**
     * Upload base64 encoded data to storage.
     *
     * @param bucketUrl        Bucket url, may be null
     * @param refPath          Storage reference path, not null
     * @param base64DataString Base64 data representation, not null
     * @param promise          Promise, not null
     */
    static native void upload(String bucketUrl, String refPath, String base64DataString, FuturePromise<FileMetadata> promise) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath).putString(base64DataString,'base64').then(function(snapshot){
            @pl.mk5.gdx.fireapp.html.storage.StorageJS::callUploadPromise(Lmk/gdx/firebase/html/storage/UploadTaskSnapshot;Lmk/gdx/firebase/promises/FuturePromise;)(snapshot, promise);
        })['catch'](function(error){
             promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    /**
     * Creates FileMetaData from {@code snapshot} and calls callback.
     *
     * @param snapshot Snapshot from firebase, not null
     * @param promise  Upload promise, not null
     */
    static void callUploadPromise(UploadTaskSnapshot snapshot, FuturePromise<FileMetadata> promise) {
        FileMetadata fileMetadata = SnapshotFileMetaDataResolver.resolve(snapshot);
        promise.doComplete(fileMetadata);
    }
}
