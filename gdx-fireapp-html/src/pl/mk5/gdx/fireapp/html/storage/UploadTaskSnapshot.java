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

import com.google.gwt.core.client.JavaScriptObject;

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * GWT implementation of <a href="https://firebase.google.com/docs/reference/js/firebase.storage.UploadTaskSnapshot">UploadTaskSnapshot</a>
 */
class UploadTaskSnapshot extends JavaScriptObject {
    protected UploadTaskSnapshot() {
    }

    /**
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.UploadTaskSnapshot#metadata">https://firebase.google.com/docs/reference/js/firebase.storage.UploadTaskSnapshot#metadata</a>
     */
    public final native FullMetaData getMetaData() /*-{ return this.metadata; }-*/;

    /**
     * Gets download url in async way.
     *
     * @param urlConsumer The url consumer, not null
     */
    public final native void downloadUrl(Consumer<String> urlConsumer) /*-{
        this.ref.getDownloadURL().then(function(downloadURL) {
          urlConsumer.@pl.mk5.gdx.fireapp.functional.Consumer::accept(Ljava/lang/Object;)(downloadURL);
        });
    }-*/;

}
