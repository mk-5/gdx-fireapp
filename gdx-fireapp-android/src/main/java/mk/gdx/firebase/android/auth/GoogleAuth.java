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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;
import mk.gdx.firebase.promises.Promise;

/**
 * Google authorization android API implementation.
 *
 * @see GoogleAuthDistribution
 */
public class GoogleAuth implements GoogleAuthDistribution {

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signIn() {
        return FuturePromise.of(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> gdxFirebaseUserFuturePromise) {
                GoogleSignInOptions gso = GoogleSignInOptionsFactory.factory();
                ((AndroidApplication) Gdx.app).addAndroidEventListener(new GoogleSignInListener(gdxFirebaseUserFuturePromise));
                ((AndroidApplication) Gdx.app).startActivityForResult(
                        GoogleSignIn.getClient((AndroidApplication) Gdx.app, gso).getSignInIntent(), Const.GOOGLE_SIGN_IN);
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
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                GoogleSignInClient client = GoogleSignIn.getClient((AndroidApplication) Gdx.app, GoogleSignInOptionsFactory.factory());
                client.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            voidFuturePromise.doComplete(null);
                        } else {
                            voidFuturePromise.doFail(task.getException());
                        }
                    }
                });
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> revokeAccess() {
        return FuturePromise.of(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(final FuturePromise<Void> voidFuturePromise) {
                GoogleSignInClient client = GoogleSignIn.getClient((AndroidApplication) Gdx.app, GoogleSignInOptionsFactory.factory());
                client.revokeAccess().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            voidFuturePromise.doComplete(null);
                        } else {
                            voidFuturePromise.doFail(task.getException());
                        }
                    }
                });
            }
        });
    }
}
