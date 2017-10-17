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
     * @param reference   Reference path, not null
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
     * @param reference   Reference path, not null
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


    /**
     * Attaches listener from {@link #dataChangeListeners} to given reference.
     *
     * @param reference Reference path, not null
     */
    public static native void onValue(String reference) /*-{
           var ref = reference;
           $wnd.valueListeners = $wnd.valueListeners || {};
           $wnd.valueListeners[reference] = $wnd.firebase.database().ref(ref).on("value", function(snap){
            var val = JSON.stringify(snap.val());
            @mk.gdx.firebase.html.database.DatabaseJS::callListener(Ljava/lang/String;Ljava/lang/String;)(ref,val);
          });
    }-*/;

    /**
     * Remove value listeners for given path.
     * <p>
     * If listener was not declared before - all value listeners for given path will be cleared.
     *
     * @param reference Reference path, not null
     */
    public static native void offValue(String reference) /*-{
        $wnd.valueListeners = $wnd.valueListeners || {};
        var listener = $wnd.valueListeners[reference] || null;
        $wnd.firebase.database().ref(reference).off('value', listener);
        @mk.gdx.firebase.html.database.DatabaseJS::removeDataListener(Ljava/lang/String;)(reference);
    }-*/;

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
     * Calls firebase.database().push method.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#push">https://firebase.google.com/docs/reference/js/firebase.database.Reference#push</a>
     *
     * @param reference Reference path, not null
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
     * @param reference Reference path, not null
     */
    public static native void remove(String reference) /*-{
        $wnd.firebase.database().ref(reference).remove();
    }-*/;

    /**
     * Calls firebase.database().remove method with callback.
     * <p>
     * You can read more here: <a href="https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove">https://firebase.google.com/docs/reference/js/firebase.database.Reference#remove</a>
     *
     * @param reference Reference path, not null
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
     * @param reference   Reference path, not null
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
     * @param reference   Reference path, not null
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
     * Modify database data in single transaction.
     *
     * @param reference Reference path, not null
     * @param dataModifier
     * @param completeCallback
     */
    public static native void transaction(String reference, JsonDataModifier dataModifier, CompleteCallback completeCallback) /*-{
        $wnd.firebase.transaction().ref(reference).transaction(function(currentData){
            var newData = dataModifier.@mk.gdx.firebase.html.database.JsonDataModifier::modify(Ljava/lang/String;)(JSON.stringify(currentData));
            var val;
            try{
                val = JSON.parse(newData);
            }catch(err){
                val = newData;
            }
            return val;
        }, function(error, committed, snapshot){
            if( error || !committed ){
                var message = "";
                if( error ) message = error.message;
                else if( !committed ) message = "Transaction was aborted.";
                callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(message));
            }else{
                callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
            }
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
     * @param refPath  Reference path, not null
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
     * Gets true if listener for given path is already attached.
     *
     * @param referencePath Reference path, not null
     * @return True if listener is already attached.
     */
    public static boolean hasListener(String referencePath)
    {
        return dataChangeListeners.containsKey(referencePath) && dataChangeListeners.get(referencePath) != null;
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
     * Remove listener.
     *
     * @param refPath Database reference path
     */
    static void removeDataListener(String refPath)
    {
        if (dataChangeListeners.containsKey(refPath))
            dataChangeListeners.remove(refPath);
    }
}
