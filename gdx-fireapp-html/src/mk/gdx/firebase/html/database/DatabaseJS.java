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

/**
 * Javascript calls to firebase database api.
 */
public class DatabaseJS
{
    private static DataCallback nextDataCallback;

    /**
     * Gets some data from firebase and returns value parsed by JSON.stringify
     * <p>
     * Calls {@link #callCallback(String)} when done.
     *
     * @param reference Reference path, not null
     */
    public static native void once(String reference) /*-{
        $wnd.firebase.database().ref(reference).once("value").then(function(snapshot){
            var val = JSON.stringify(snapshot.val());
            @mk.gdx.firebase.html.database.DatabaseJS::callCallback(Ljava/lang/String;)(val);
        });
    }-*/;

    /**
     * Set callback which is use in next javascript call with DataCallback.
     * <p>
     * Data callback has String generic type because of GWT - it does not support GenericTypes and we can't just
     * call DataCallback::onData(Ltype_description)()
     *
     * @param callback Callback, not null
     */
    static void setNextDataCallback(DataCallback callback)
    {
        DatabaseJS.nextDataCallback = callback;
    }


    /**
     * Calls callback set by {@code setNextDataCallback}.
     *
     * @param value Value passed to original callback, not null
     */
    @SuppressWarnings("unchecked")
    static void callCallback(String value)
    {
        if (DatabaseJS.nextDataCallback == null)
            throw new IllegalStateException();
        nextDataCallback.onData(value);
        DatabaseJS.nextDataCallback = null;
    }

}
