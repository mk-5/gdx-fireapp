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

package mk.gdx.firebase.html.auth;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.GoogleAuthDistribution;
import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.html.firebase.ScriptRunner;

/**
 * @see GoogleAuthDistribution
 */
public class GoogleAuth implements GoogleAuthDistribution {

    /**
     * {@inheritDoc}
     */
    @Override
    public void signIn(final AuthCallback callback) {
        ScriptRunner.firebaseScript(new Runnable() {
            @Override
            public void run() {
                GoogleAuthJS.signIn(callback);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void signOut(final SignOutCallback callback) {
        GdxFIRAuth.instance().signOut().then(new Consumer<Void>() {
            @Override
            public void accept(Void aVoid) {
                callback.onSuccess();
            }
        }).fail(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                callback.onFail((Exception) throwable);
            }
        });
    }

    /**
     * {@inheritDoc}
     * <p>
     * For gwt is the same as {@link #signOut(SignOutCallback)}.
     */
    @Override
    public void revokeAccess(final CompleteCallback callback) {
        signOut(new SignOutCallback() {
            @Override
            public void onSuccess() {
                callback.onSuccess();
            }

            @Override
            public void onFail(Exception e) {
                callback.onError(e);
            }
        });
    }
}
