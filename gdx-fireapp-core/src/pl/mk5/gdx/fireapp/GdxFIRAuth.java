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

package pl.mk5.gdx.fireapp;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.distributions.AuthDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * @see AuthDistribution
 * @see PlatformDistributor
 */
public class GdxFIRAuth extends PlatformDistributor<AuthDistribution> implements AuthDistribution {

    private static final String USER_NOT_LOGGED_IN = "User is not logged in.";
    private static volatile GdxFIRAuth instance;
    private GdxFIRGoogleAuth gdxFIRGoogleAuth;

    /**
     * GdxFIRAuth protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    GdxFIRAuth() {
        gdxFIRGoogleAuth = new GdxFIRGoogleAuth();
    }

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRAuth instance() {
        return (GdxFIRAuth) Api.instance(AuthDistribution.class);
    }

    /**
     * Alias of {@link #instance()}
     */
    public static GdxFIRAuth inst() {
        return instance();
    }

    /**
     * @return GoogleAuth platform distribution, not null
     */
    public GdxFIRGoogleAuth google() {
        return gdxFIRGoogleAuth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GdxFirebaseUser getCurrentUser() {
        return platformObject.getCurrentUser();
    }

    public Promise<GdxFirebaseUser> getCurrentUserPromise() {
        return FuturePromise.when(new Consumer<FuturePromise<GdxFirebaseUser>>() {
            @Override
            public void accept(FuturePromise<GdxFirebaseUser> promise) {
                GdxFirebaseUser user = getCurrentUser();
                if (user != null) {
                    promise.doComplete(user);
                } else {
                    promise.doFail(USER_NOT_LOGGED_IN, null);
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> createUserWithEmailAndPassword(String email, char[] password) {
        return platformObject.createUserWithEmailAndPassword(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithEmailAndPassword(String email, char[] password) {
        return platformObject.signInWithEmailAndPassword(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInWithToken(String token) {
        return platformObject.signInWithToken(token);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signInAnonymously() {
        return platformObject.signInAnonymously();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> signOut() {
        return platformObject.signOut();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> sendPasswordResetEmail(String email) {
        return platformObject.sendPasswordResetEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "pl.mk5.gdx.fireapp.ios.auth.Auth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.android.auth.Auth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "pl.mk5.gdx.fireapp.html.auth.Auth";
    }
}
