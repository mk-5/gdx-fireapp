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

import com.badlogic.gdx.Gdx;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.typedarrays.client.Uint8ArrayNative;
import com.google.gwt.typedarrays.shared.Uint8Array;

import mk.gdx.firebase.callbacks.DeleteCallback;
import mk.gdx.firebase.callbacks.UploadCallback;

/**
 * Javascript calls to firebase storage api.
 */
public class StorageJS
{

    /**
     * Downloads file given at {@code refPath} and convert it to base64 string.
     *
     * @param bucketUrl        Bucket url, may be null
     * @param refPath          Storage reference path, not null
     * @param downloadCallback Callback, not null
     */
    public static native void download(String bucketUrl, String refPath, UrlDownloadCallback downloadCallback) /*-{
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
     * @param bucketUrl      Bucket url, may be null
     * @param refPath        Storage reference path, not null
     * @param deleteCallback Callback, not null
     */
    public static native void remove(String bucketUrl, String refPath, DeleteCallback deleteCallback) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        storage.ref(refPath)['delete']().then(function(){
             deleteCallback.@mk.gdx.firebase.callbacks.DeleteCallback::onSuccess()();
        })['catch'](function(error){
            deleteCallback.@mk.gdx.firebase.callbacks.DeleteCallback::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.message));
        });
    }-*/;

    /**
     * FIXME - does not work. Error from browser: 'Firebase Storage: Invalid argument in `put` at index 0: Expected Blob or File.'
     * @param bucketUrl      Bucket url, may be null
     * @param refPath        Storage reference path, not null
     * @param byteArray      Uint8Array byte[] representation, not null
     * @param uploadCallback Callback, not null
     */
    public static native void upload(String bucketUrl, String refPath, Uint8ArrayNative byteArray, UploadCallback uploadCallback) /*-{
        var storage = $wnd.firebase.app().storage((bucketUrl != "" ? bucketUrl : null));
        //var blob = new Blob([byteArray]);
        console.log(byteArray);
        storage.ref(refPath).put(byteArray).then(function(snapshot){
            @mk.gdx.firebase.html.storage.StorageJS::callUploadCallback(Lcom/google/gwt/core/client/JavaScriptObject;Lmk/gdx/firebase/callbacks/UploadCallback;)(snapshot, uploadCallback);
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
    static void callUploadCallback(JavaScriptObject snapshot, UploadCallback callback)
    {
        Gdx.app.log("StorageJS", "snapshot: " + snapshot.toSource());
    }
}
