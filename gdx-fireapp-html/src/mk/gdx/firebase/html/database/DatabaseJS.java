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

import com.badlogic.gdx.utils.ObjectMap;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.DataCallback;
import mk.gdx.firebase.listeners.ConnectedListener;
import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Javascript calls to firebase database api.
 */
public class DatabaseJS
{
    private static DataCallback nextDataCallback;
    private static ObjectMap<String, DataChangeListener<String>> dataChangeListeners = new ObjectMap<String, DataChangeListener<String>>();

    /**
     * Set value to database.
     * <p>
     * JSON.parse is using here. It's getting object or primitive (for primitives types it is primitive wrapped by string ex. "1", "true") and returns object representation.
     * Is one exception here - when stringValue is actually a string like "abc", JSON.parse will be throw error.
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

    /**
     * Set value to database.
     * <p>
     * For more explanation look at {@link #set(String, String)}
     *
     * @param reference   Reference to deal with
     * @param stringValue String value representation
     * @param callback    Callback
     */
    public static native void setWithCallback(String reference, String stringValue, CompleteCallback callback) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).set(val).then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })['catch'](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;


    // TODO -
    public static native void onValue(String reference) /*-{
           var ref = reference;
           $wnd.firebase.database().ref(ref).on("value", function(snap){
             var val;
             try{
                val = JSON.stringify(snapshot.val());
             }catch(err){
                val = stringValue;
             }
            @mk.gdx.firebase.html.database.DatabaseJS::callListener(Ljava/lang/String;Ljava/lang/String;)(ref,val);
        });
    }-*/;

    /**
     * Gets some data from firebase and returns value parsed by JSON.stringify
     * <p>
     * Calls {@link #callCallback(String)} when done.
     *
     * @param reference Reference to deal with
     */
    public static native void once(String reference) /*-{
        $wnd.firebase.database().ref(reference).once("value").then(function(snapshot){
            var val;
            try{
                val = JSON.stringify(snapshot.val());
            }catch(err){
                val = stringValue;
            }
            @mk.gdx.firebase.html.database.DatabaseJS::callCallback(Ljava/lang/String;)(val);
        });
    }-*/;

    /**
     * Calls firebase.database().push method.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#push">https://firebase.google.com/docs/reference/js/firebase.database.Reference#push</a>
     *
     * @param reference Database reference path
     * @return A new reference path
     */
    public static native String push(String reference) /*-{
        return $wnd.firebase.database().ref(reference).push().path["Q"].join("/");
    }-*/;

    /**
     * Set connected/disconnected listener.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state">https://firebase.google.com/docs/database/web/offline-capabilities#section-connection-state</a>
     *
     * @param listener
     */
    public static native void onConnect(ConnectedListener listener) /*-{
        $wnd.firebase.database().ref(".info/connected").on("value", function(snap){
            if (snap.val() === true) {
                listener.@mk.gdx.firebase.listeners.ConnectedListener::onConnect()();
            } else {
                listener.@mk.gdx.firebase.listeners.ConnectedListener::onDisconnect()();
            }
        });
    }-*/;

    /**
     * Calls firebase.database().remove method.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Database reference path
     */
    public static native void remove(String reference) /*-{
        $wnd.firebase.database().ref(reference).remove();
    }-*/;

    /**
     * Calls firebase.database().remove method with callback.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Database reference path
     * @param callback  Callback
     */
    public static native void removeWithCallback(String reference, CompleteCallback callback) /*-{
        $wnd.firebase.database().ref(reference).remove().then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })['catch'](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    /**
     * Writes multiple values to database at once.
     * <p>
     * JSON.parse is using here. It get object or primitive (for primitives types it is primitive wrapped by string ex. "1", "true") and returns object representation.
     * Is one exception here - when stringValue is actually a string like "abc", JSON.parse will be throw error.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#update">https://firebase.google.com/docs/reference/js/firebase.database.Reference#update</a>
     *
     * @param reference   Reference to deal with
     * @param stringValue String value representation
     */
    public static native void update(String reference, String stringValue) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).update(val);
    }-*/;

    /**
     * Update multiple values in database with callback.
     * <p>
     * For more explanation look at {@link #update(String, String)}
     *
     * @param reference   Reference to deal with
     * @param stringValue String value representation
     * @param callback    Callback
     */
    public static native void updateWithCallback(String reference, String stringValue, CompleteCallback callback) /*-{
        var val;
        try{
            val = JSON.parse(stringValue);
        }catch(err){
            val = stringValue;
        }
        $wnd.firebase.database().ref(reference).update(val).then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })['catch'](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
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
     * @param refPath  Database reference path
     * @param listener Listener, may be null
     */
    static void addDataListener(String refPath, DataChangeListener listener)
    {
        boolean isPresent = dataChangeListeners.containsKey(refPath);
        if (isPresent) {
            if (listener == null) {
                dataChangeListeners.remove(refPath);
                // TODO - remove from firebase.js?
            }
        } else if (listener != null) {
            dataChangeListeners.put(refPath, listener);
        }
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

    /**
     * Calls listener added by {@code addDataListener}.
     *
     * @param refPath  Database ref path
     * @param newValue New value as json string
     */
    static void callListener(String refPath, String newValue)
    {
        if (!dataChangeListeners.containsKey(refPath))
//            throw new IllegalStateException();
            return;
        dataChangeListeners.get(refPath).onChange(newValue);
    }

    /**
     * @param refPath Database reference path
     */
    static void removeDataListener(String refPath)
    {
        if (dataChangeListeners.containsKey(refPath))
            dataChangeListeners.remove(refPath);
    }
}
