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

import com.android.org.bouncycastle.util.encoders.Hex;

import org.robovm.apple.authservices.ASAuthorizationAppleIDProvider;
import org.robovm.apple.authservices.ASAuthorizationAppleIDRequest;
import org.robovm.apple.authservices.ASAuthorizationController;
import org.robovm.apple.authservices.ASAuthorizationRequest;
import org.robovm.apple.foundation.NSArray;
import org.robovm.apple.foundation.NSError;
import org.robovm.pods.firebase.auth.FIRAuth;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.distributions.AppleAuthDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Apple authorization ios API
 *
 * @see AppleAuthDistribution
 */
public class AppleAuth implements AppleAuthDistribution {
    private static final String[] SCOPES = {"email", "full_name"};

    @Override
    public Promise<GdxFirebaseUser> signIn() {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> gdxFirebaseUserFuturePromise) {
                ASAuthorizationAppleIDProvider appleIDProvider = new ASAuthorizationAppleIDProvider();
                ASAuthorizationAppleIDRequest request = appleIDProvider.createRequest();
                String rawNonce = UUID.randomUUID().toString()
                request.setRequestedScopes(NSArray.fromStrings(SCOPES));
                request.setNonce(hash256(rawNonce));
                ASAuthorizationController asAuthorizationController = new ASAuthorizationController(new NSArray<ASAuthorizationRequest>(request));
                asAuthorizationController.setDelegate(new AppleAuthProvider(rawNonce, gdxFirebaseUserFuturePromise));
                asAuthorizationController.setPresentationContextProvider(new ApplePresentationContextProvider());
                asAuthorizationController.performRequests();
            }
        });
    }

    @Override
    public Promise<Void> signOut() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                NSError.NSErrorPtr ptr = new NSError.NSErrorPtr();
                FIRAuth.auth().signOut(ptr);
                if (ptr.get() != null) {
                    voidFuturePromise.doFail(new RuntimeException(ptr.get().getLocalizedDescription()));
                } else {
                    voidFuturePromise.doComplete(null);
                }
            }
        });
    }

    @Override
    public Promise<Void> revokeAccess() {
        return signOut();
    }

    private static String hash256(String data) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return new String(Hex.encode(hash));
    }
}
