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
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

/**
 * Google authorization ios API
 *
 * @see GoogleAuthDistribution
 */
public class GoogleAuth implements GoogleAuthDistribution {

    private final GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();

    @Override
    public Promise<GdxFirebaseUser> signIn() {
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> gdxFirebaseUserFuturePromise) {
                googleAuthProvider.initializeOnce();
                googleAuthProvider.addSignInPromise(gdxFirebaseUserFuturePromise);
                GIDSignIn.sharedInstance().signIn();
            }
        });
    }

    @Override
    public Promise<Void> signOut() {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                googleAuthProvider.initializeOnce();
                try {
                    GIDSignIn.sharedInstance().signOut();
                    voidFuturePromise.doComplete(null);
                } catch (Exception e) {
                    voidFuturePromise.doFail(e);
                }
            }
        });
    }

    @Override
    public Promise<Void> revokeAccess() {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                googleAuthProvider.initializeOnce();
                googleAuthProvider.addDisconnectPromise(voidFuturePromise);
                GIDSignIn.sharedInstance().disconnect();
            }
        });
    }
}
