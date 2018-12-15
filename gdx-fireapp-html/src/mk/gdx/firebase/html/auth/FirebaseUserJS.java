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

package mk.gdx.firebase.html.auth;

import com.google.gwt.core.client.JavaScriptObject;

import mk.gdx.firebase.promises.FuturePromise;

/**
 * JSON representation of Firebase user.
 */
class FirebaseUserJS extends JavaScriptObject {

    protected FirebaseUserJS() {
    }

    public final native String getDisplayName() /*-{ return this.displayName; }-*/;

    public final native String getEmail() /*-{ return this.email; }-*/;

    public final native boolean isEmailVerified() /*-{ return this.emailVerified; }-*/;

    public final native boolean isAnonymous() /*-{ return this.isAnonymous; }-*/;

    public final native String getPhoneNumber() /*-{ return this.phoneNumber; }-*/;

    public final native String getPhotoURL() /*-{ return this.photoURL; }-*/;

    public final native String getProviderId() /*-{ return (typeof this.providerData['providerId'] != 'undefined') ? this.providerData['providerId'] : ""; }-*/;

    public final native String getUID() /*-{ return this.uid; }-*/;

    public final native boolean isNULL() /*-{ return this['isNULL'] != 'undefined' && this['isNULL'] == true; }-*/;

    public final native void updateEmail(String newEmail, FuturePromise promise) /*-{
        this.updateEmail(newEmail).then(function(){
            promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })["catch"](function(error){
            promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    public final native void sendEmailVerification(FuturePromise promise) /*-{
        this.sendEmailVerification().then(function(){
            promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })["catch"](function(error){
             promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    public final native void updatePassword(String newPassword, FuturePromise promise) /*-{
        this.updatePassword(newPassword).then(function(){
           promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })["catch"](function(error){
           promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    public final native void delete(FuturePromise promise) /*-{
        this["delete"]().then(function(){
            promise.@mk.gdx.firebase.promises.FuturePromise::doComplete(Ljava/lang/Void;)(null);
        })["catch"](function(error){
             promise.@mk.gdx.firebase.promises.FuturePromise::doFail(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    public final native void reload(CompleteCallback callback) /*-{
        this["reload"]().then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })["catch"](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;
}
