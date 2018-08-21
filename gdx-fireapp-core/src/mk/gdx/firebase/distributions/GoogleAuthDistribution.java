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

package mk.gdx.firebase.distributions;

import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;

/**
 * Provides authorization via Google.
 * <p>
 * You can read about possible Firebase authorization methods in Firebase docs:
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/auth/android/google-signin">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/auth/ios/google-signin">ios firebase docs</a>
 */
public interface GoogleAuthDistribution {

    /**
     * Signs in into application using google account.
     *
     * @param callback Authorization callback, not null
     */
    void signIn(AuthCallback callback);

    /**
     * Sign-out current google user and gives response by {@code SignOutCallback}.
     *
     * @param callback Sign-out callback, not null
     */
    void signOut(SignOutCallback callback);

    /**
     * Disconnects user from the app and gives response by {@code CompleteCallback}.
     *
     * @param callback Sign-out callback, not null
     */
    void revokeAccess(CompleteCallback callback);
}
