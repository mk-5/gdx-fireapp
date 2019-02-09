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

import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides access to Firebase user methods.
 * <p>
 * You can read more about it in Firebase docs:
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/auth/android/manage-users">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/auth/ios/manage-users">ios firebase docs</a>
 */
public interface AuthUserDistribution {


    /**
     * Updates user email
     *
     * @param newEmail The new email, not null
     * @throws IllegalStateException When current user is not present
     */
    Promise<Void> updateEmail(String newEmail);

    /**
     * Sends email with verification link
     *
     * @throws IllegalStateException When current user is not present
     */
    Promise<Void> sendEmailVerification();

    /**
     * Updates user password
     *
     * @param newPassword The new password, not null
     * @throws IllegalStateException When current user is not present
     */
    Promise<Void> updatePassword(char[] newPassword);

    /**
     * Deletes current user
     *
     * @throws IllegalStateException When current user is not present
     */
    Promise<Void> delete();
}
