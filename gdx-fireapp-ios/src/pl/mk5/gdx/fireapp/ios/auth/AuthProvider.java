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

package pl.mk5.gdx.fireapp.ios.auth;

import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

public abstract class AuthProvider {
    protected final Array<FuturePromise<GdxFirebaseUser>> signInPromises = new Array<>();
    protected final Array<FuturePromise<Void>> disconnectPromises = new Array<>();

    synchronized void addSignInPromise(FuturePromise<GdxFirebaseUser> promise) {
        signInPromises.add(promise);
    }

    synchronized void addDisconnectPromise(FuturePromise<Void> promise) {
        disconnectPromises.add(promise);
    }
}
