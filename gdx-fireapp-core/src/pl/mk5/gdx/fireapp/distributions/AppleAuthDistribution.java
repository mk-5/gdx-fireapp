/*
 *
 *  * Copyright 2020 mk
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package pl.mk5.gdx.fireapp.distributions;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Provides authorization via Apple.
 *
 * @see <a href="https://firebase.google.com/docs/auth/android/apple">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/auth/ios/apple">ios firebase docs</a>
 */
public interface AppleAuthDistribution {

    Promise<GdxFirebaseUser> signIn();

    Promise<Void> signOut();

    Promise<Void> revokeAccess();
}
