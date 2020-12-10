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

package pl.mk5.gdx.fireapp.ios.auth;

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.objc.block.VoidBlock2;
import org.robovm.pods.firebase.auth.FIRAuth;
import org.robovm.pods.firebase.auth.FIRAuthDataResult;
import org.robovm.pods.firebase.auth.FIRUser;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.auth.UserInfo;
import pl.mk5.gdx.fireapp.distributions.AuthDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

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
        FIRUser firUser = FIRAuth.auth().getCurrentUser();
        if (firUser == null) return null;
        UserInfo.Builder builder = new UserInfo.Builder();
        builder.setDisplayName(firUser.getDisplayName())
                .setPhotoUrl(firUser.getPhotoURL() != null ? firUser.getPhotoURL().getPath() : null)
                .setProviderId(firUser.getProviderID())
                .setUid(firUser.getUid())
                .setIsEmailVerified(firUser.isEmailVerified())
                .setIsAnonymous(firUser.isAnonymous())
                .setEmail(firUser.getEmail());
        return GdxFirebaseUser.create(builder.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> createUserWithEmailAndPassword(final String email, final char[] password) {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().createUser(email, new String(password), new VoidBlock2<FIRAuthDataResult, NSError>() {
                    @Override
                    public void invoke(FIRAuthDataResult arg0, NSError arg1) {
                        if (handleError(arg1, promise)) return;
                        // TODO - arg0 was changed from FIRUser to FIRAuthDataResult - need to adopt it
                        promise.doComplete(getCurrentUser());
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithEmailAndPassword(final String email, final char[] password) {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInUsingEmailPassword(email, new String(password), new VoidBlock2<FIRAuthDataResult, NSError>() {
                    @Override
                    public void invoke(FIRAuthDataResult arg0, NSError arg1) {
                        if (handleError(arg1, promise)) return;
                        promise.doComplete(getCurrentUser());
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithToken(final String token) {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInUsingCustomToken(token, new VoidBlock2<FIRAuthDataResult, NSError>() {
                    @Override
                    public void invoke(FIRAuthDataResult arg0, NSError arg1) {
                        if (handleError(arg1, promise)) return;
                        promise.doComplete(getCurrentUser());
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInAnonymously() {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInAnonymously(new VoidBlock2<FIRAuthDataResult, NSError>() {
                    @Override
                    public void invoke(FIRAuthDataResult arg0, NSError arg1) {
                        if (handleError(arg1, promise)) return;
                        promise.doComplete(getCurrentUser());
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> signOut() {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> promise) {
                try {
                    NSError.NSErrorPtr errorPtr = new NSError.NSErrorPtr();
                    FIRAuth.auth().signOut(errorPtr);
                    if (errorPtr.get() != null) {
                        promise.doFail(new Exception(errorPtr.get().getLocalizedDescription()));
                    } else {
                        promise.doComplete(null);
                    }
                } catch (Exception e) {
                    promise.doFail(e);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public FuturePromise<Void> sendPasswordResetEmail(final String email) {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> promise) {
                FIRAuth.auth().sendPasswordReset(email, new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError arg0) {
                        if (handleError(arg0, promise)) return;
                        promise.doComplete(null);
                    }
                });
            }
        });
    }

    private boolean handleError(NSError error, FuturePromise promise) {
        if (error != null) {
            promise.doFail(error.getLocalizedFailureReason(), new Exception(error.getLocalizedDescription()));
            return true;
        }
        return false;
    }
}
