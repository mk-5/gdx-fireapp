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

/**
 * Javascript calls to firebase storage api.
 */
public class StorageJS
{

    /**
     * Downloads file at given path and convert it to base64 string.
     *
     * @param bucketUrl        Bucket url
     * @param refPath          Storage reference path
     * @param downloadCallback Callback
     */
    public static native void download(String bucketUrl, String refPath, Base64DownloadCallback downloadCallback) /*-{
        var storage = scriptBucketUrl != null ? ($wnd.firebase.app().storage(scriptBucketUrl)) : ($wnd.firebase.storage());
        storage.getDownloadUrl().then(function(url){
            var xhr = new XMLHttpRequest();
            xhr.responseType = 'blob';
            xhr.onload = function(){
                var blob = xhr.response;
                var reader = new FileReader();
                reader.onloadend = function(){
                    downloadCallback.@mk.gdx.firebase.html.storage.Base64DownloadCallback::onSuccess(Ljava/lang/String;)(reader.result);
                };
                reader.readAsDataUrl(blob);
            };
        })['catch'](function(error){
            downloadCallback.@mk.gdx.firebase.html.storage.Base64DownloadCallback::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.code));
        });
    }-*/;
}
