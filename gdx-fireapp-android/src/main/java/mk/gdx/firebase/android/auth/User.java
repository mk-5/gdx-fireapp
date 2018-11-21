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

package mk.gdx.firebase.android.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.distributions.AuthUserDistribution;

/**
 * @see AuthUserDistribution
 */
public class User implements AuthUserDistribution {

    @Override
    public void updateEmail(String newEmail, final CompleteCallback callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        FirebaseAuth.getInstance().getCurrentUser()
                .updateEmail(newEmail)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            if (task.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    }
                });
    }

    @Override
    public void sendEmailVerification(final CompleteCallback callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        FirebaseAuth.getInstance().getCurrentUser()
                .sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            if (task.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    }
                });
    }

    @Override
    public void updatePassword(char[] newPassword, final CompleteCallback callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        FirebaseAuth.getInstance().getCurrentUser()
                .updatePassword(new String(newPassword))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            if (task.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    }
                });
    }

    @Override
    public void delete(final CompleteCallback callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        FirebaseAuth.getInstance().getCurrentUser()
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            if (task.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    }
                });
    }

    @Override
    public void reload(final CompleteCallback callback) {
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            throw new IllegalStateException();
        }
        FirebaseAuth.getInstance().getCurrentUser()
                .reload()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (callback != null) {
                            if (task.isSuccessful()) {
                                callback.onSuccess();
                            } else {
                                callback.onError(task.getException());
                            }
                        }
                    }
                });
    }
}
