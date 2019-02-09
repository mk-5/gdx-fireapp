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

package pl.mk5.gdx.fireapp.distributions;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides authorization via Google.
 *
 * @see <a href="https://firebase.google.com/docs/auth/android/google-signin">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/auth/ios/google-signin">ios firebase docs</a>
 */
public interface GoogleAuthDistribution {

    /**
     * Signs in into application using google account.
     */
    Promise<GdxFirebaseUser> signIn();

    /**
     * Sign-out current google user.
     */
    Promise<Void> signOut();

    /**
     * Disconnects user from the app.
     */
    Promise<Void> revokeAccess();
}
