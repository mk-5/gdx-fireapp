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

package mk.gdx.firebase.android.auth;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.promises.FuturePromise;

class AuthPromiseConsumer<T> implements Consumer<FuturePromise<GdxFirebaseUser>> {

    private Task<T> task;

    public AuthPromiseConsumer(Task<T> task) {
        this.task = task;
    }

    @Override
    public void accept(final FuturePromise<GdxFirebaseUser> promise) {
        if (task != null) {
            task.addOnCompleteListener(new OnCompleteListener<T>() {
                @Override
                public void onComplete(@NonNull Task<T> task) {
                    if (task.isSuccessful()) {
                        promise.doComplete(GdxFIRAuth.instance().getCurrentUser());
                    } else {
                        promise.doFail(task.getException() != null ? task.getException().getLocalizedMessage() : "Authorization fail", task.getException());
                    }
                }
            });
        }
    }
}
