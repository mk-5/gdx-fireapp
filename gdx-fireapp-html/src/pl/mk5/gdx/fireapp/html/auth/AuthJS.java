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

package pl.mk5.gdx.fireapp.html.auth;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Provides calls to firebase javascript api.
 */
class AuthJS {

    private AuthJS() {
        //
    }

    static native FirebaseUserJS firebaseUser() /*-{
        if( typeof $wnd.firebase == 'undefined') return { isNULL : true };
        var user = $wnd.firebase.auth().currentUser;
        return (typeof user != 'undefined' && user != null) ? user : { isNULL : true };
    }-*/;

    static native void signInAnonymously(final FuturePromise promise) /*-{
        $wnd.firebase.auth().signInAnonymously().then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(
                @pl.mk5.gdx.fireapp.html.auth.AuthJS::getUserBridge()()
            );
        })['catch'](function(error) {
           promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
            @java.lang.Exception::new(Ljava/lang/String;)(error.message)
          );
        });
    }-*/;

    static native void signInWithEmailAndPassword(final String email, final String password, final FuturePromise promise) /*-{
        $wnd.firebase.auth().signInWithEmailAndPassword(email, password).then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(
                @pl.mk5.gdx.fireapp.html.auth.AuthJS::getUserBridge()()
            );
        })['catch'](function(error) {
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void createUserWithEmailAndPassword(final String email, final String password, final FuturePromise promise) /*-{
        $wnd.firebase.auth().createUserWithEmailAndPassword(email, password).then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(
                @pl.mk5.gdx.fireapp.html.auth.AuthJS::getUserBridge()()
            );
        })['catch'](function(error) {
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void signInWithToken(final String token, final FuturePromise promise) /*-{
        $wnd.firebase.auth().signInWithCustomToken(token).then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(
                @pl.mk5.gdx.fireapp.html.auth.AuthJS::getUserBridge()()
            );
        })['catch'](function(error) {
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void signOut(final FuturePromise promise) /*-{
        if( $wnd.firebase.auth().currentUser == null ){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(null);
            return;
        }
        $wnd.firebase.auth().signOut().then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(null);
        })['catch'](function(error) {
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void sendPasswordResetEmail(String email, final FuturePromise promise) /*-{
        $wnd.firebase.auth().sendPasswordResetEmail(email).then(function(){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doComplete(Ljava/lang/Object;)(null);
        })["catch"](function(error){
            promise.@pl.mk5.gdx.fireapp.promises.FuturePromise::doFail(Ljava/lang/Throwable;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    static GdxFirebaseUser getUserBridge() {
        return GdxFIRAuth.instance().getCurrentUser();
    }
}
