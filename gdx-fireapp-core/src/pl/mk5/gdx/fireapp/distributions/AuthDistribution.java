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

package pl.mk5.gdx.fireapp.distributions;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.GdxFIRStorage;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides access to Firebase authorization methods.
 * <p>
 * You can read about possible Firebase authorization methods in Firebase docs:
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/auth/android/start">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/auth/ios/start">ios firebase docs</a>
 */
public interface AuthDistribution {

    /**
     * Gets currently logged in user or null if logged in user does not exists.
     *
     * @return Firebase user wrapped by {@link GdxFirebaseUser} class, may by null.
     */
    GdxFirebaseUser getCurrentUser();

    /**
     * Registers new user and gives response by {@code Promise}.
     *
     * @param email    New email address
     * @param password New password
     */
    Promise<GdxFirebaseUser> createUserWithEmailAndPassword(String email, char[] password);

    /**
     * Signs in into application and gives response by {@code Promise}
     *
     * @param email    Firebase user email
     * @param password Firebase user password
     */
    Promise<GdxFirebaseUser> signInWithEmailAndPassword(String email, char[] password);

    /**
     * Signs in into application by token and gives response by {@code Promise}.
     * <p>
     * More info you about custom tokens you can find <a href="https://firebase.google.com/docs/auth/admin/create-custom-tokens">here</a>.
     *
     * @param token    Custom token from your firebase console.
     */
    Promise<GdxFirebaseUser> signInWithToken(String token);

    /**
     * Signs in into application anonymously and gives response by {@code Promise}.
     * <p>
     * It may be very useful when you do not provide user authentication in your application and<p>
     * working with {@link GdxFIRDatabase} or with {@link GdxFIRStorage}<p>
     * because default Firebase requires authorization for database and storage actions.
     *
     */
    Promise<GdxFirebaseUser> signInAnonymously();

    /**
     * Sign-out current user and gives response by {@code SignOutCallback}.
     * <p>
     */
    Promise<Void> signOut();

    /**
     * Sends password reset email message to given email.
     *
     * @param email    The new email, not null
     */
    Promise<Void> sendPasswordResetEmail(String email);
}
