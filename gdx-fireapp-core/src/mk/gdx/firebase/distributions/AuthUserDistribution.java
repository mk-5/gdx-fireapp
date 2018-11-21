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

import mk.gdx.firebase.callbacks.CompleteCallback;

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
     * Updates user email and calls {@code callback} when complete.
     *
     * @param newEmail The new email, not null
     * @param callback Callback, may be null
     * @throws IllegalStateException When current user is not present
     */
    void updateEmail(String newEmail, CompleteCallback callback);

    /**
     * Sends email with verification link and calls {@code callback} when complete.
     *
     * @param callback Callback, may be null
     * @throws IllegalStateException When current user is not present
     */
    void sendEmailVerification(CompleteCallback callback);

    /**
     * Updates user password and calls {@code callback} when complete.
     *
     * @param newPassword The new password, not null
     * @param callback    Callback, may be null
     * @throws IllegalStateException When current user is not present
     */
    void updatePassword(char[] newPassword, CompleteCallback callback);

    /**
     * Deletes current user and calls {@code callback} when complete.
     *
     * @param callback Callback, may be null
     * @throws IllegalStateException When current user is not present
     */
    void delete(CompleteCallback callback);

    /**
     * Reload current user and calls {@code callback} when complete.
     *
     * @param callback Callback, may be null
     * @throws IllegalStateException When current user is not present
     */
    void reload(CompleteCallback callback);
}
