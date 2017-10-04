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

package mk.gdx.firebase;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.distributions.AuthDistribution;
import mk.gdx.firebase.exceptions.PlatformDistributorException;

/**
 * @see AuthDistribution
 * @see PlatformDistributor
 */
public class GdxFIRAuth extends PlatformDistributor<AuthDistribution> implements AuthDistribution
{

    private static GdxFIRAuth instance;

    /**
     * GdxFIRAuth protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    protected GdxFIRAuth() throws PlatformDistributorException
    {
    }

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRAuth instance()
    {
        if (instance == null) {
            synchronized (GdxFIRAnalytics.class) {
                if (instance == null) {
                    try {
                        instance = new GdxFIRAuth();
                    } catch (PlatformDistributorException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GdxFirebaseUser getCurrentUser()
    {
        return platformObject.getCurrentUser();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUserWithEmailAndPassword(String email, char[] password, AuthCallback callback)
    {
        platformObject.createUserWithEmailAndPassword(email, password, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithEmailAndPassword(String email, char[] password, AuthCallback callback)
    {
        platformObject.signInWithEmailAndPassword(email, password, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithToken(String token, AuthCallback callback)
    {
        platformObject.signInWithToken(token, callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInAnonymously(AuthCallback callback)
    {
        platformObject.signInAnonymously(callback);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName()
    {
        return "mk.gdx.firebase.ios.auth.Auth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName()
    {
        return "mk.gdx.firebase.android.auth.Auth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName()
    {
        return "mk.gdx.firebase.html.auth.Auth";
    }
}
