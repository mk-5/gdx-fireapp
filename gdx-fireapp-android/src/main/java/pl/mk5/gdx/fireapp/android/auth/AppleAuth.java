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
package pl.mk5.gdx.fireapp.android.auth;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.distributions.AppleAuthDistribution;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Apple authorization android API implementation.
 *
 * @see AppleAuthDistribution
 */
public class AppleAuth implements AppleAuthDistribution {
    private static final String PROVIDER = "apple.com";

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<GdxFirebaseUser> signIn() {
        throw new UnsupportedOperationException("not yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> signOut() {
        throw new UnsupportedOperationException("not yet.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> revokeAccess() {
        throw new UnsupportedOperationException("not yet.");
    }
}
