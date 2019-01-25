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

package mk.gdx.firebase.html.storage;

import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.storage.FileMetadata;

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
     * @param downloadCallback Callback, not null
     */
    static native void download(String bucketUrl, String refPath, UrlDownloadCallback downloadCallback) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath).getDownloadURL().then(function(url){
             downloadCallback.@mk.gdx.firebase.html.storage.UrlDownloadCallback::onSuccess(Ljava/lang/String;)(url);
        })['catch'](function(error){
            downloadCallback.@mk.gdx.firebase.html.storage.UrlDownloadCallback::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.message));
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
             promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })['catch'](function(error){
            promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
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
     * @param uploadCallback   Callback, not null
     */
    static native void upload(String bucketUrl, String refPath, String base64DataString, UploadCallback uploadCallback) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath).putString(base64DataString,'base64').then(function(snapshot){
            @mk.gdx.firebase.html.storage.StorageJS::callUploadCallback(Lmk/gdx/firebase/html/storage/UploadTaskSnapshot;Lmk/gdx/firebase/callbacks/UploadCallback;)(snapshot, uploadCallback);
        })['catch'](function(error){
            uploadCallback.@mk.gdx.firebase.callbacks.UploadCallback::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.message));
        });
    }-*/;

    /**
     * Creates FileMetaData from {@code snapshot} and calls callback.
     *
     * @param snapshot Snapshot from firebase, not null
     * @param callback Upload callback to cal, not null
     */
    static void callUploadCallback(UploadTaskSnapshot snapshot, UploadCallback callback) {
        FileMetadata fileMetadata = SnapshotFileMetaDataResolver.resolve(snapshot);
        callback.onSuccess(fileMetadata);
    }
}
