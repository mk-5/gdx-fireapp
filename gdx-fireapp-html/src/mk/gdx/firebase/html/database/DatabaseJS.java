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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.listeners.ConnectedListener;

/**
 * Javascript calls to firebase database api.
 */
public class DatabaseJS
{
    /**
     * Parse stringValue by JSON.parse javascript function.
     * <p>
     * JSON.parse get object or primitive (for primitives types ex. "1", "true") and returns string representation.
     * Is here one exception when stringValue is actually a string like "abc", JSON.parse will be throw error.
     *
     * @param reference   Reference to deal with
     * @param stringValue String value representation
     */
    public static native void set(String reference, String stringValue) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).set(val);
    }-*/;

    public static native void onValue(String reference, DataCallback dataCallback) /*-{

    }-*/;

    public static native void once(String reference, DataCallback dataCallback) /*-{

    }-*/;

    public static native void keepSynced(boolean synced) /*-{
    }-*/;

    public static native void setPersistanceEnabled(boolean enabled) /*-{
    }-*/;

    public static native void push(String reference) /*-{
        $wnd.firebase.database().ref(reference).push();
    }-*/;

    public static native void onConnect(ConnectedListener listener) /*-{
        $wnd.firebase.database().ref(".info/connected").on("value", function(snap){
            if (snap.val() === true) {
                .listener@mk.gdx.firebase.listeners.ConnectedListener::onConnect()();
            } else {
                .listener@mk.gdx.firebase.listeners.ConnectedListener::onDisconnect()();
            }
        });
    }-*/;
}
