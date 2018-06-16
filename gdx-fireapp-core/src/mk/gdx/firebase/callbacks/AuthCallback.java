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

package mk.gdx.firebase.callbacks;

import mk.gdx.firebase.auth.GdxFirebaseUser;

/**
 * Handles response from authorization actions.
 */
public interface AuthCallback {
    /**
     * Calls when everything was done without issues.
     *
     * @param user User instance of currently logged in user, not null.
     */
    void onSuccess(GdxFirebaseUser user);

    /**
     * Calls when something goes wrong.
     *
     * @param e Exception describes what was wrong during authorization.
     */
    void onFail(Exception e);
}
