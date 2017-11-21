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

package mk.gdx.firebase.html.auth;

import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.auth.UserInfo;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.AuthDistribution;
import mk.gdx.firebase.html.firebase.ScriptRunner;

/**
 * @see AuthDistribution
 */
public class Auth implements AuthDistribution
{

    /**
     * {@inheritDoc}
     */
    @Override
    public GdxFirebaseUser getCurrentUser()
    {
        FirebaseUserJSON userFromApi = AuthJS.firebaseUser();
        if (userFromApi.isNULL()) return null;
        UserInfo.Builder builder = new UserInfo.Builder();
        builder.setDisplayName(userFromApi.getDisplayName())
                .setPhotoUrl(userFromApi.getPhotoURL())
                .setProviderId(userFromApi.getProviderId())
                .setUid(userFromApi.getUID())
                .setIsEmailVerified(userFromApi.isEmailVerified())
                .setIsAnonymous(userFromApi.isAnonymous())
                .setEmail(userFromApi.getEmail());
        return GdxFirebaseUser.create(builder.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createUserWithEmailAndPassword(final String email, final char[] password, final AuthCallback callback)
    {
        ScriptRunner.firebaseScript(new Runnable()
        {
            @Override
            public void run()
            {
                AuthJS.createUserWithEmailAndPassword(email, new String(password), callback);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithEmailAndPassword(final String email, final char[] password, final AuthCallback callback)
    {
        ScriptRunner.firebaseScript(new Runnable()
        {
            @Override
            public void run()
            {
                AuthJS.signInWithEmailAndPassword(email, new String(password), callback);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInWithToken(final String token, final AuthCallback callback)
    {
        ScriptRunner.firebaseScript(new Runnable()
        {
            @Override
            public void run()
            {
                AuthJS.signInWithToken(token, callback);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signInAnonymously(final AuthCallback callback)
    {
        ScriptRunner.firebaseScript(new Runnable()
        {
            @Override
            public void run()
            {
                AuthJS.singInAnonymously(callback);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(final SignOutCallback callback)
    {
        ScriptRunner.firebaseScript(new Runnable()
        {
            @Override
            public void run()
            {
                AuthJS.signOut(callback);
            }
        });
    }

}
