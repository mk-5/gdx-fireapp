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

import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.general.ptr.impl.PtrFactory;

import apple.foundation.NSError;
import bindings.google.firebaseauth.FIRAuth;
import bindings.google.firebaseauth.FIRAuthDataResult;
import bindings.google.firebaseauth.FIRUser;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.auth.UserInfo;
import mk.gdx.firebase.distributions.AuthDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

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
    public Promise<GdxFirebaseUser> createUserWithEmailAndPassword(String email, char[] password) {
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().createUserWithEmailPasswordCompletion(email, new String(password), new FIRAuth.Block_createUserWithEmailPasswordCompletion() {
                    @Override
                    public void call_createUserWithEmailPasswordCompletion(FIRAuthDataResult arg0, NSError arg1) {
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
    public Promise<GdxFirebaseUser> signInWithEmailAndPassword(String email, char[] password) {
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInWithEmailPasswordCompletion(email, new String(password), new FIRAuth.Block_signInWithEmailPasswordCompletion() {
                    @Override
                    public void call_signInWithEmailPasswordCompletion(FIRAuthDataResult arg0, NSError arg1) {
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
    public Promise<GdxFirebaseUser> signInWithToken(String token) {
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInWithCustomTokenCompletion(token, new FIRAuth.Block_signInWithCustomTokenCompletion() {
                    @Override
                    public void call_signInWithCustomTokenCompletion(FIRAuthDataResult arg0, NSError arg1) {
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
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> promise) {
                FIRAuth.auth().signInAnonymouslyWithCompletion(new FIRAuth.Block_signInAnonymouslyWithCompletion() {
                    @Override
                    public void call_signInAnonymouslyWithCompletion(FIRAuthDataResult arg0, NSError arg1) {
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
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> promise) {
                try {
                    Ptr<NSError> ptr = PtrFactory.newObjectReference(NSError.class);
                    FIRAuth.auth().signOut(ptr);
                    if (ptr.get() != null) {
                        promise.doFail(new Exception(ptr.get().localizedDescription()));
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
    public FuturePromise<Void> sendPasswordResetEmail(String email) {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> promise) {
                FIRAuth.auth().sendPasswordResetWithEmailCompletion(email, new FIRAuth.Block_sendPasswordResetWithEmailCompletion() {
                    @Override
                    public void call_sendPasswordResetWithEmailCompletion(NSError arg0) {
                        if (arg0 != null) {
                            promise.doFail(new Exception(arg0.localizedDescription()));
                        } else {
                            promise.doComplete(null);
                        }
                    }
                });
            }
        });
    }

    private boolean handleError(NSError error, FuturePromise promise) {
        if (error != null) {
            promise.doFail(error.localizedDescription(), new Exception(error.localizedDescription()));
            return true;
        }
        return false;
    }
}
