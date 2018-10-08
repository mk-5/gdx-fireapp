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

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;

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

    static native void signInAnonymously(final AuthCallback callback) /*-{
        var removeAuthListener = $wnd.firebase.auth().onAuthStateChanged(function(user){
            if( user ){
                callback.@mk.gdx.firebase.callbacks.AuthCallback::onSuccess(Lmk/gdx/firebase/auth/GdxFirebaseUser;)(
                    @mk.gdx.firebase.html.auth.AuthJS::getUserBridge()()
                );
            }
            removeAuthListener();
        });
        $wnd.firebase.auth().signInAnonymously()['catch'](function(error) {
          callback.@mk.gdx.firebase.callbacks.AuthCallback::onFail(Ljava/lang/Exception;)(
            @java.lang.Exception::new(Ljava/lang/String;)(error.message)
          );
        });
    }-*/;

    static native void signInWithEmailAndPassword(final String email, final String password, final AuthCallback callback) /*-{
        var removeAuthListener = $wnd.firebase.auth().onAuthStateChanged(function(user){
            if( user ){
                callback.@mk.gdx.firebase.callbacks.AuthCallback::onSuccess(Lmk/gdx/firebase/auth/GdxFirebaseUser;)(
                    @mk.gdx.firebase.html.auth.AuthJS::getUserBridge()()
                );
            }
            removeAuthListener();
        });
        $wnd.firebase.auth().signInWithEmailAndPassword(email, password)['catch'](function(error) {
            callback.@mk.gdx.firebase.callbacks.AuthCallback::onFail(Ljava/lang/Exception;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void createUserWithEmailAndPassword(final String email, final String password, final AuthCallback callback) /*-{
        $wnd.firebase.auth().createUserWithEmailAndPassword(email, password).then(function(){
            callback.@mk.gdx.firebase.callbacks.AuthCallback::onSuccess(Lmk/gdx/firebase/auth/GdxFirebaseUser;)(
                @mk.gdx.firebase.html.auth.AuthJS::getUserBridge()()
            );
        })['catch'](function(error) {
            callback.@mk.gdx.firebase.callbacks.AuthCallback::onFail(Ljava/lang/Exception;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void signInWithToken(final String token, final AuthCallback callback) /*-{
        var removeAuthListener = $wnd.firebase.auth().onAuthStateChanged(function(user){
            if( user ){
                callback.@mk.gdx.firebase.callbacks.AuthCallback::onSuccess(Lmk/gdx/firebase/auth/GdxFirebaseUser;)(
                    @mk.gdx.firebase.html.auth.AuthJS::getUserBridge()()
                );
            }
            removeAuthListener();
        });
        $wnd.firebase.auth().signInWithCustomToken(token)['catch'](function(error) {
            callback.@mk.gdx.firebase.callbacks.AuthCallback::onFail(Ljava/lang/Exception;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void signOut(final SignOutCallback callback) /*-{
        if( $wnd.firebase.auth().currentUser == null ){
            callback.@mk.gdx.firebase.callbacks.SignOutCallback::onSuccess()();
            return;
        }
        $wnd.firebase.auth().signOut().then(function(){
            callback.@mk.gdx.firebase.callbacks.SignOutCallback::onSuccess()();
        })['catch'](function(error) {
            callback.@mk.gdx.firebase.callbacks.SignOutCallback::onFail(Ljava/lang/Exception;)(
                @java.lang.Exception::new(Ljava/lang/String;)(error.message)
            );
        });
    }-*/;

    static native void sendPasswordResetEmail(String email, final CompleteCallback callback) /*-{
        $wnd.firebase.auth().sendPasswordResetEmail(email).then(function(){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onSuccess()();
        })["catch"](function(error){
            callback.@mk.gdx.firebase.callbacks.CompleteCallback::onError(Ljava/lang/Exception;)(@java.lang.Exception::new(Ljava/lang/String;)(error.message));
        });
    }-*/;

    /**
     * @return Simplest way for javascript to get GdxFirebaseUser.
     */
    static GdxFirebaseUser getUserBridge() {
        return GdxFIRAuth.instance().getCurrentUser();
    }
}
