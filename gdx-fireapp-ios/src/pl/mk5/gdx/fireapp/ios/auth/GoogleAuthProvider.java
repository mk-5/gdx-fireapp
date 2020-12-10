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
package pl.mk5.gdx.fireapp.ios.auth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.badlogic.gdx.utils.Array;

import org.robovm.apple.foundation.NSError;
import org.robovm.apple.uikit.UIViewController;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.auth.FIRAuth;
import org.robovm.pods.firebase.auth.FIRAuthCredential;
import org.robovm.pods.firebase.auth.FIRAuthDataResult;
import org.robovm.pods.firebase.auth.FIRGoogleAuthProvider;
import org.robovm.pods.firebase.core.FIRApp;
import org.robovm.pods.firebase.googlesignin.GIDGoogleUser;
import org.robovm.pods.firebase.googlesignin.GIDSignIn;
import org.robovm.pods.firebase.googlesignin.GIDSignInDelegate;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class GoogleAuthProvider {

    private boolean initialized;
    private Array<FuturePromise<GdxFirebaseUser>> signInPromises = new Array<>();
    private Array<FuturePromise<Void>> disconnectPromises = new Array<>();

    synchronized boolean isInitialized() {
        return initialized;
    }

    synchronized void initializeOnce() {
        if (!initialized) {
            GIDSignIn.sharedInstance().setClientID(FIRApp.defaultApp().getOptions().getClientID());
            GIDSignDelegate delegate = new GIDSignDelegate();
            GIDSignIn.sharedInstance().setDelegate(delegate);
            UIViewController viewController = GIDSignIn.sharedInstance().getPresentingViewController();
//            GIDSignIn.sharedInstance().setDelegate(viewController);
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

    class GIDSignDelegate implements GIDSignInDelegate {

        @Override
        public void didSignIn(GIDSignIn signIn, GIDGoogleUser user, NSError error) {
            if (signInPromises.size == 0) return;
            final FuturePromise<GdxFirebaseUser> signInPromises = GoogleAuthProvider.this.signInPromises.get(0);
            GoogleAuthProvider.this.signInPromises.removeIndex(0);
            if (error == null) {
                FIRAuthCredential credential = FIRGoogleAuthProvider.createCredentialUsingIDToken(
                        user.getAuthentication().getIdToken(), user.getAuthentication().getAccessToken()
                );
                FIRAuth.auth().signInUsingCredential(credential, new VoidBlock2<FIRAuthDataResult, NSError>() {
                    @Override
                    public void invoke(FIRAuthDataResult arg0, NSError arg1) {
                        if (arg1 == null) {
                            signInPromises.doComplete(GdxFIRAuth.instance().getCurrentUser());
                        } else {
                            signInPromises.doFail(new Exception(arg1.getLocalizedDescription()));
                        }
                    }
                });
            } else {
                signInPromises.doFail(new Exception(error.getLocalizedDescription()));
            }
        }

        @Override
        public void didDisconnect(GIDSignIn signIn, GIDGoogleUser user, NSError error) {
            if (disconnectPromises.size == 0) return;
            final FuturePromise<Void> promise = disconnectPromises.get(0);
            disconnectPromises.removeIndex(0);
            if (error == null) {
                promise.doComplete(null);
            } else {
                promise.doFail(new Exception(error.getLocalizedDescription()));
            }
        }
    }
}
