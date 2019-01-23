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

package mk.gdx.firebase;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;
import mk.gdx.firebase.promises.Promise;

/**
 * @see GoogleAuthDistribution
 * @see PlatformDistributor
 */
public class GdxFIRGoogleAuth extends PlatformDistributor<GoogleAuthDistribution> implements GoogleAuthDistribution {

    /**
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    GdxFIRGoogleAuth() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signIn() {
        return platformObject.signIn();
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
    public Promise<Void> revokeAccess() {
        return platformObject.revokeAccess();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "mk.gdx.firebase.ios.auth.GoogleAuth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "mk.gdx.firebase.android.auth.GoogleAuth";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "mk.gdx.firebase.html.auth.GoogleAuth";
    }
}
