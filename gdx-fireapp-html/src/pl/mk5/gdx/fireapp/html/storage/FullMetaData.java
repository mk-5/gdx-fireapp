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

/**
 * GWT implementation of <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata">FulMetaData</a>
 */
class FullMetaData extends JavaScriptObject {

    protected FullMetaData() {
    }

    /**
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#md5Hash">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#md5Hash</a>
     */
    public final native String getMD5Hash() /*-{  return this.md5Hash; }-*/;

    /**
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#name">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#name</a>
     */
    public final native String getName() /*-{  return this.name; }-*/;

    /**
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#fullPath">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#fullPath</a>
     */
    public final native String getPath() /*-{  return this.fullPath; }-*/;

    /**
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#size">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#size</a>
     */
    public final native double getSizeBytes() /*-{  return this.size; }-*/;

    /**
     * Get&Transforms {@code timeCreated} to millis.
     * <p>
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#timeCreated">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#timeCreated</a>
     */
    public final native double getTimeCreatedMillis() /*-{  return new Date(this.timeCreated).getTime(); }-*/;

    /**
     * Get&Transforms {@code updated} to millis.
     * <p>
     * GWT doc: <a href="https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#updated">https://firebase.google.com/docs/reference/js/firebase.storage.FullMetadata#updated</a>
     */
    public final native double getTimeUpdatedMillis() /*-{  return new Date(this.updated).getTime(); }-*/;
}
