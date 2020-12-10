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

import org.robovm.apple.foundation.NSError;
import org.robovm.objc.block.VoidBlock1;
import org.robovm.pods.firebase.auth.FIRAuth;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.distributions.AuthUserDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class User implements AuthUserDistribution {

    @Override
    public Promise<GdxFirebaseUser> updateEmail(final String newEmail) {
        if (FIRAuth.auth().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> voidFuturePromise) {
                FIRAuth.auth().getCurrentUser().updateEmail(newEmail, new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError nsError) {
                        if (handleError(nsError, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(GdxFIRAuth.instance().getCurrentUser());
                    }
                });
            }
        });
    }

    @Override
    public Promise<GdxFirebaseUser> sendEmailVerification() {
        if (FIRAuth.auth().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> voidFuturePromise) {
                FIRAuth.auth().getCurrentUser().sendEmailVerification(new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError nsError) {
                        if (handleError(nsError, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(GdxFIRAuth.instance().getCurrentUser());
                    }
                });
            }
        });
    }

    @Override
    public Promise<GdxFirebaseUser> updatePassword(final char[] newPassword) {
        if (FIRAuth.auth().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> voidFuturePromise) {
                FIRAuth.auth().getCurrentUser().updatePassword(new String(newPassword), new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError nsError) {
                        if (handleError(nsError, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(GdxFIRAuth.instance().getCurrentUser());
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> delete() {
        if (FIRAuth.auth().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().getCurrentUser().delete(new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError nsError) {
                        if (handleError(nsError, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    @Override
    public Promise<GdxFirebaseUser> reload() {
        if (FIRAuth.auth().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(final FuturePromise<GdxFirebaseUser> voidFuturePromise) {
                FIRAuth.auth().getCurrentUser().reload(new VoidBlock1<NSError>() {
                    @Override
                    public void invoke(NSError nsError) {
                        if (handleError(nsError, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(GdxFIRAuth.instance().getCurrentUser());
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
