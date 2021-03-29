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

import org.robovm.apple.authservices.ASAuthorization;
import org.robovm.apple.authservices.ASAuthorizationAppleIDCredential;
import org.robovm.apple.authservices.ASAuthorizationController;
import org.robovm.apple.authservices.ASAuthorizationControllerDelegate;
import org.robovm.apple.authservices.ASAuthorizationControllerDelegateAdapter;
import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.auth.FIRAuth;
import org.robovm.pods.firebase.auth.FIRAuthDataResult;
import org.robovm.pods.firebase.auth.FIROAuthCredential;
import org.robovm.pods.firebase.auth.FIROAuthProvider;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class AppleAuthProvider extends ASAuthorizationControllerDelegateAdapter {

    private final String nonce;
    private final FuturePromise<GdxFirebaseUser> delegatePromise;

    AppleAuthProvider(String nonce, FuturePromise<GdxFirebaseUser> delegatePromise) {
        this.nonce = new String(nonce);
        this.delegatePromise = delegatePromise;
    }

    @Override
    public void didComplete(ASAuthorizationController asAuthorizationController, ASAuthorization asAuthorization) {
        if (asAuthorization.getCredential() instanceof ASAuthorizationAppleIDCredential) {
            ASAuthorizationAppleIDCredential credential = (ASAuthorizationAppleIDCredential) asAuthorization.getCredential();
            FIROAuthCredential firoAuthCredential = FIROAuthProvider.createUsingIDToken("apple.com", credential.getIdentityToken().toString(), nonce);
            FIRAuth.auth().signInUsingCredential(firoAuthCredential, new VoidBlock2<FIRAuthDataResult, NSError>() {
                @Override
                public void invoke(FIRAuthDataResult firAuthDataResult, NSError nsError) {
                    delegatePromise.doComplete(GdxFIRAuth.instance().getCurrentUser());
                }
            });
        }
    }

    @Override
    public void didComplete(ASAuthorizationController asAuthorizationController, NSError nsError) {
        delegatePromise.doFail(new RuntimeException(nsError.getLocalizedDescription()));
    }
}
