/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.android.config;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

class RemoteConfigPromiseConsumer implements Consumer<FuturePromise<Void>> {
    @Override
    public void accept(final FuturePromise<Void> voidFuturePromise) {
        FirebaseRemoteConfig.getInstance()
                .fetch()
                .addOnCompleteListener(completeListener(voidFuturePromise))
                .addOnFailureListener(failureListener(voidFuturePromise));
    }

    private OnCompleteListener<Void> completeListener(final FuturePromise<Void> voidFuturePromise) {
        return new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (FirebaseRemoteConfig.getInstance().activateFetched()) {
                        voidFuturePromise.doComplete(null);
                    } else {
                        voidFuturePromise.doFail(new Exception("Couldn't activate fetched config"));
                    }
                } else {
                    voidFuturePromise.doFail(new Exception("Couldn't fetch the config"));
                }
            }
        };
    }

    private OnFailureListener failureListener(final FuturePromise<Void> voidFuturePromise) {
        return new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                voidFuturePromise.doFail(e);
            }
        };
    }
}
