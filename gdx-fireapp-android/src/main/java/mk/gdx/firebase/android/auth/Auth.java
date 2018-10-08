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

package mk.gdx.firebase.android.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.auth.UserInfo;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.AuthDistribution;

/**
 * Android Firebase authorization API implementation.
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
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) return null;
        UserInfo.Builder builder = new UserInfo.Builder();
        builder.setDisplayName(user.getDisplayName())
                .setPhotoUrl(user.getPhotoUrl() != null ? user.getPhotoUrl().getPath() : null)
                .setProviderId(user.getProviderId())
                .setUid(user.getUid())
                .setIsEmailVerified(user.isEmailVerified())
                .setIsAnonymous(user.isAnonymous())
                .setEmail(user.getEmail());
        return GdxFirebaseUser.create(builder.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUserWithEmailAndPassword(String email, char[] password, final AuthCallback callback) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, new String(password))
                .addOnCompleteListener(new AuthListener(callback));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithEmailAndPassword(String email, char[] password, final AuthCallback callback) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, new String(password))
                .addOnCompleteListener(new AuthListener(callback));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithToken(String token, AuthCallback callback) {
        FirebaseAuth.getInstance().signInWithCustomToken(token)
                .addOnCompleteListener(new AuthListener(callback));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInAnonymously(AuthCallback callback) {
        FirebaseAuth.getInstance().signInAnonymously().addOnCompleteListener(new AuthListener(callback));
    }

    @Override
    public void signOut(SignOutCallback callback) {
        try {
            FirebaseAuth.getInstance().signOut();
            callback.onSuccess();
        } catch (Exception e) {
            callback.onFail(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendPasswordResetEmail(String email, final CompleteCallback callback) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
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

    /**
     * Listener for AuthenticationResult.
     * This class is a wrap for using of {@link AuthCallback}.
     * Result from android sdk authentication methods is wrapped by {@link Task<AuthResult>} so we need to deal with it.
     */
    private class AuthListener implements OnCompleteListener<AuthResult> {

        private AuthCallback callback;

        /**
         * @param callback AuthCallback which need to be call on authentication result.
         */
        public AuthListener(AuthCallback callback) {
            this.callback = callback;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                callback.onSuccess(getCurrentUser());
            } else {
                callback.onFail(task.getException());
            }
        }
    }
}
