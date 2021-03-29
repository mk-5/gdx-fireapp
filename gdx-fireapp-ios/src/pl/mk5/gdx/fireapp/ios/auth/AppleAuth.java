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

import com.android.org.conscrypt.OpenSSLMessageDigestJDK;

import org.robovm.apple.authservices.ASAuthorizationAppleIDProvider;
import org.robovm.apple.authservices.ASAuthorizationAppleIDRequest;
import org.robovm.apple.authservices.ASAuthorizationController;
import org.robovm.apple.authservices.ASAuthorizationRequest;
import org.robovm.apple.foundation.NSArray;

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

    @Override
    public Promise<GdxFirebaseUser> signIn() {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> gdxFirebaseUserFuturePromise) {
                ASAuthorizationAppleIDProvider appleIDProvider = new ASAuthorizationAppleIDProvider();
                ASAuthorizationAppleIDRequest request = appleIDProvider.createRequest();
                final String nonce = hash256(UUID.randomUUID().toString());
                request.setRequestedScopes(NSArray.fromStrings("email", "full_name"));
                request.setNonce(nonce);
                ASAuthorizationController asAuthorizationController = new ASAuthorizationController(new NSArray<ASAuthorizationRequest>(request));
                asAuthorizationController.setDelegate(new AppleAuthProvider(nonce, gdxFirebaseUserFuturePromise));
                asAuthorizationController.performRequests();
            }
        });
    }

    @Override
    public Promise<Void> signOut() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
            }
        });
    }

    @Override
    public Promise<Void> revokeAccess() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {

            }
        });
    }

    private static String hash256(String data)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        md.update(data.getBytes());
        byte[] bytes = md.digest();
        StringBuilder result = new StringBuilder();
        for (byte byt : bytes)
            result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }
}
