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

package mk.gdx.firebase.ios.auth;

import com.google.firebaseauth.FIRAuth;
import com.google.firebaseauth.FIRAuthDataResult;
import com.google.firebaseauth.FIRUser;

import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.general.ptr.impl.PtrFactory;

import apple.foundation.NSError;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.auth.UserInfo;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.AuthDistribution;

/**
 * iOS Firebase authorization API implementation.
 * <p>
 *
 * @see AuthDistribution
 */
public class Auth implements AuthDistribution {

    /**
     * {@inheritDoc}
     */
    @Override
    public GdxFirebaseUser getCurrentUser() {
        FIRUser firUser = FIRAuth.auth().currentUser();
        if (firUser == null) return null;
        UserInfo.Builder builder = new UserInfo.Builder();
        builder.setDisplayName(firUser.displayName())
                .setPhotoUrl(firUser.photoURL() != null ? firUser.photoURL().path() : null)
                .setProviderId(firUser.providerID())
                .setUid(firUser.uid())
                .setIsEmailVerified(firUser.isEmailVerified())
                .setIsAnonymous(firUser.isAnonymous())
                .setEmail(firUser.email());
        return GdxFirebaseUser.create(builder.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUserWithEmailAndPassword(String email, char[] password, final AuthCallback callback) {
        FIRAuth.auth().createUserWithEmailPasswordCompletion(email, new String(password), new FIRAuth.Block_createUserWithEmailPasswordCompletion() {
            @Override
            public void call_createUserWithEmailPasswordCompletion(FIRAuthDataResult arg0, NSError arg1) {
                if (handleError(arg1, callback)) return;
                // TODO - arg0 was chacnged from FIRUser to FIRAuthDataResult - need to adopt it
                callback.onSuccess(getCurrentUser());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithEmailAndPassword(String email, char[] password, final AuthCallback callback) {
        FIRAuth.auth().signInWithEmailPasswordCompletion(email, new String(password), new FIRAuth.Block_signInWithEmailPasswordCompletion() {
            @Override
            public void call_signInWithEmailPasswordCompletion(FIRAuthDataResult arg0, NSError arg1) {
                if (handleError(arg1, callback)) return;
                // TODO
                callback.onSuccess(getCurrentUser());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithToken(String token, final AuthCallback callback) {
        FIRAuth.auth().signInWithCustomTokenCompletion(token, new FIRAuth.Block_signInWithCustomTokenCompletion() {
            @Override
            public void call_signInWithCustomTokenCompletion(FIRAuthDataResult arg0, NSError arg1) {
                if (handleError(arg1, callback)) return;
                // TODO
                callback.onSuccess(getCurrentUser());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInAnonymously(final AuthCallback callback) {
        FIRAuth.auth().signInAnonymouslyWithCompletion(new FIRAuth.Block_signInAnonymouslyWithCompletion() {
            @Override
            public void call_signInAnonymouslyWithCompletion(FIRAuthDataResult arg0, NSError arg1) {
                if (handleError(arg1, callback)) return;
                callback.onSuccess(getCurrentUser());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(SignOutCallback callback) {
        try {
            Ptr<NSError> ptr = PtrFactory.newObjectReference(NSError.class);
            FIRAuth.auth().signOut(ptr);
            if (ptr.get() != null) {
                callback.onFail(new Exception(ptr.get().localizedDescription()));
            } else {
                callback.onSuccess();
            }
        } catch (Exception e) {
            callback.onFail(e);
        }
    }

    /**
     * @param error    NSError occurred during the authorization process - can be null.
     * @param callback {@link AuthCallback#onFail(Exception)} will be use here if error exsits.
     * @return True if any error occurred or false if don't
     */
    private boolean handleError(NSError error, AuthCallback callback) {
        if (error != null) {
            callback.onFail(new Exception(error.localizedDescription()));
            return true;
        }
        return false;
    }
}
