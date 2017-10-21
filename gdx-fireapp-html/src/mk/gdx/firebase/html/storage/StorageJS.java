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
    public static native void download(String bucketUrl, String refPath, UrlDownloadCallback downloadCallback) /*-{
        var storage = (bucketUrl != null && bucketUrl != "") ? ($wnd.firebase.app().storage(bucketUrl)) : ($wnd.firebase.storage());
        storage.ref(refPath).getDownloadURL().then(function(url){
             downloadCallback.@mk.gdx.firebase.html.storage.UrlDownloadCallback::onSuccess(Ljava/lang/String;)(url);
        })['catch'](function(error){
            downloadCallback.@mk.gdx.firebase.html.storage.UrlDownloadCallback::onFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)("Error: " + error.message));
        });
    }-*/;
}
