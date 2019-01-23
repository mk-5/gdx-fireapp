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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.utils.Array;

import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.RegisterOnStartup;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSError;
import apple.uikit.UITableViewController;
import bindings.google.firebaseauth.FIRAuth;
import bindings.google.firebaseauth.FIRAuthCredential;
import bindings.google.firebaseauth.FIRAuthDataResult;
import bindings.google.firebaseauth.FIRGoogleAuthProvider;
import bindings.google.firebasecore.FIRApp;
import bindings.google.googlesignin.GIDGoogleUser;
import bindings.google.googlesignin.GIDSignIn;
import bindings.google.googlesignin.protocol.GIDSignInDelegate;
import bindings.google.googlesignin.protocol.GIDSignInUIDelegate;
import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.promises.FuturePromise;

/**
 * Provides google authorization.
 */
class GoogleAuthProvider {

    private boolean initialized;
    private Array<FuturePromise<GdxFirebaseUser>> signInPromises = new Array<>();
    private Array<FuturePromise<Void>> disconnectPromises = new Array<>();

    synchronized boolean isInitialized() {
        return initialized;
    }

    synchronized void initializeOnce() {
        if (!initialized) {
            GIDSignIn.sharedInstance().setClientID(FIRApp.defaultApp().options().clientID());
            GIDSignIn.sharedInstance().setDelegate(new GIDSignDelegate());
            GIDViewController viewController = GIDViewController.alloc().init();
            GIDSignIn.sharedInstance().setUiDelegate(viewController);
            ((IOSApplication) Gdx.app).getUIViewController().addChildViewController(viewController);
        }
        initialized = true;
    }

    synchronized void addSignInPromise(FuturePromise<GdxFirebaseUser> promise) {
        signInPromises.add(promise);
    }

    synchronized void addDisconnectPromise(FuturePromise<Void> promise) {
        disconnectPromises.add(promise);
    }

    @Runtime(ObjCRuntime.class)
    @ObjCClassName("GIDSignDelegate")
    @RegisterOnStartup
    class GIDSignDelegate implements GIDSignInDelegate {

        @Override
        public void signInDidSignInForUserWithError(GIDSignIn signIn, GIDGoogleUser user, NSError error) {
            if (signInPromises.size == 0) return;
            final FuturePromise<GdxFirebaseUser> signInPromises = GoogleAuthProvider.this.signInPromises.get(0);
            GoogleAuthProvider.this.signInPromises.removeIndex(0);
            if (error == null) {
                FIRAuthCredential credential = FIRGoogleAuthProvider.credentialWithIDTokenAccessToken(
                        user.authentication().idToken(), user.authentication().accessToken()
                );
                FIRAuth.auth().signInAndRetrieveDataWithCredentialCompletion(credential, new FIRAuth.Block_signInAndRetrieveDataWithCredentialCompletion() {
                    @Override
                    public void call_signInAndRetrieveDataWithCredentialCompletion(FIRAuthDataResult arg0, NSError arg1) {
                        if (arg1 == null) {
                            signInPromises.doComplete(GdxFIRAuth.instance().getCurrentUser());
                        } else {
                            signInPromises.doFail(new Exception(arg1.localizedDescription()));
                        }
                    }
                });
            } else {
                signInPromises.doFail(new Exception(error.localizedDescription()));
            }
        }

        @Override
        public void signInDidDisconnectWithUserWithError(GIDSignIn signIn, GIDGoogleUser user, NSError error) {
            if (disconnectPromises.size == 0) return;
            final FuturePromise<Void> promise = disconnectPromises.get(0);
            disconnectPromises.removeIndex(0);
            if (error == null) {
                promise.doComplete(null);
            } else {
                promise.doFail(new Exception(error.localizedDescription()));
            }
        }
    }

    @Runtime(ObjCRuntime.class)
    @ObjCClassName("GIDViewController")
    @RegisterOnStartup
    static class GIDViewController extends UITableViewController implements GIDSignInUIDelegate {
        protected GIDViewController(Pointer peer) {
            super(peer);
        }

        @Owned
        @Selector("alloc")
        public static native GIDViewController alloc();

        @Selector("init")
        public native GIDViewController init();
    }
}
