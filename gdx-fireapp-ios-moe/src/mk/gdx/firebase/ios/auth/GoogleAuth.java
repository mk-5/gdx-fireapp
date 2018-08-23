/*
 * Copyright 2018 mk
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
package mk.gdx.firebase.ios.auth;

import bindings.google.googlesignin.GIDSignIn;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;

/**
 * Google authorization ios API
 *
 * @see GoogleAuthDistribution
 */
public class GoogleAuth implements GoogleAuthDistribution {

    private GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();

    @Override
    public void signIn(AuthCallback callback) {
        googleAuthProvider.initializeOnce();
        googleAuthProvider.addSignInCallback(callback);
        GIDSignIn.sharedInstance().signIn();
    }

    @Override
    public void signOut(SignOutCallback callback) {
        googleAuthProvider.initializeOnce();
        try {
            GIDSignIn.sharedInstance().signOut();
            callback.onSuccess();
        } catch (Exception e) {
            callback.onFail(e);
        }
    }

    @Override
    public void revokeAccess(CompleteCallback callback) {
        googleAuthProvider.initializeOnce();
        googleAuthProvider.addDisconnectCallback(callback);
        GIDSignIn.sharedInstance().disconnect();
    }
}
