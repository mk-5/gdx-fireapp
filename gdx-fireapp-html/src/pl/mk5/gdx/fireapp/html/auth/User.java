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

package pl.mk5.gdx.fireapp.html.auth;

import pl.mk5.gdx.fireapp.distributions.AuthUserDistribution;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * @see AuthUserDistribution
 */
public class User implements AuthUserDistribution {
    @Override
    public Promise<Void> updateEmail(final String newEmail) {
        if (AuthJS.firebaseUser().isNULL()) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                AuthJS.firebaseUser().updateEmail(newEmail, voidFuturePromise);
            }
        });
    }

    @Override
    public Promise<Void> sendEmailVerification() {
        if (AuthJS.firebaseUser().isNULL()) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                AuthJS.firebaseUser().sendEmailVerification(voidFuturePromise);
            }
        });
    }

    @Override
    public Promise<Void> updatePassword(final char[] newPassword) {
        if (AuthJS.firebaseUser().isNULL()) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                AuthJS.firebaseUser().updatePassword(new String(newPassword), voidFuturePromise);
            }
        });
    }

    @Override
    public Promise<Void> delete() {
        if (AuthJS.firebaseUser().isNULL()) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                AuthJS.firebaseUser().delete(voidFuturePromise);
            }
        });
    }

    @Override
    public Promise<Void> reload() {
        if (AuthJS.firebaseUser().isNULL()) {
            throw new IllegalStateException();
        }
        return FuturePromise.when(new Consumer<FuturePromise<Void>>() {
            @Override
            public void accept(FuturePromise<Void> voidFuturePromise) {
                AuthJS.firebaseUser().reload(voidFuturePromise);
            }
        });
    }
}
