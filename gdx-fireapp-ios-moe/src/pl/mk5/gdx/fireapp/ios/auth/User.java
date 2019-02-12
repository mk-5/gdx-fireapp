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

import apple.foundation.NSError;
import bindings.google.firebaseauth.FIRAuth;
import bindings.google.firebaseauth.FIRUser;
import pl.mk5.gdx.fireapp.distributions.AuthUserDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class User implements AuthUserDistribution {

    @Override
    public Promise<Void> updateEmail(final String newEmail) {
        if (FIRAuth.auth().currentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().currentUser().updateEmailCompletion(newEmail, new FIRUser.Block_updateEmailCompletion() {
                    @Override
                    public void call_updateEmailCompletion(NSError arg0) {
                        if (handleError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> sendEmailVerification() {
        if (FIRAuth.auth().currentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().currentUser().sendEmailVerificationWithCompletion(new FIRUser.Block_sendEmailVerificationWithCompletion() {
                    @Override
                    public void call_sendEmailVerificationWithCompletion(NSError arg0) {
                        if (handleError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> updatePassword(final char[] newPassword) {
        if (FIRAuth.auth().currentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().currentUser().updatePasswordCompletion(new String(newPassword), new FIRUser.Block_updatePasswordCompletion() {
                    @Override
                    public void call_updatePasswordCompletion(NSError arg0) {
                        if (handleError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> delete() {
        if (FIRAuth.auth().currentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().currentUser().deleteWithCompletion(new FIRUser.Block_deleteWithCompletion() {
                    @Override
                    public void call_deleteWithCompletion(NSError arg0) {
                        if (handleError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
                    }
                });
            }
        });
    }

    @Override
    public Promise<Void> reload() {
        if (FIRAuth.auth().currentUser() == null) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                FIRAuth.auth().currentUser().reloadWithCompletion(new FIRUser.Block_reloadWithCompletion() {
                    @Override
                    public void call_reloadWithCompletion(NSError arg0) {
                        if (handleError(arg0, voidFuturePromise)) return;
                        voidFuturePromise.doComplete(null);
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
