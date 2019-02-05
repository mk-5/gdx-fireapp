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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.auth.UserInfo;
import mk.gdx.firebase.distributions.AuthDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

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
    public Promise<GdxFirebaseUser> createUserWithEmailAndPassword(String email, char[] password) {
        return FuturePromise.when(new AuthPromiseConsumer<>(FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, new String(password))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithEmailAndPassword(String email, char[] password) {
        return FuturePromise.when(new AuthPromiseConsumer<>(FirebaseAuth.getInstance().signInWithEmailAndPassword(email, new String(password))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithToken(String token) {
        return FuturePromise.when(new AuthPromiseConsumer<>(FirebaseAuth.getInstance().signInWithCustomToken(token)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInAnonymously() {
        return FuturePromise.when(new AuthPromiseConsumer<>(FirebaseAuth.getInstance().signInAnonymously()));
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
                    FirebaseAuth.getInstance().signOut();
                    promise.doComplete(null);
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
    public Promise<Void> sendPasswordResetEmail(final String email) {
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> promise) {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    promise.doComplete(null);
                                } else {
                                    promise.doFail(task.getException());
                                }
                            }
                        });
            }
        });
    }
}
