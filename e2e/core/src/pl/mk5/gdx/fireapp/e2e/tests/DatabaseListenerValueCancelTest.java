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

package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Timer;

import java.util.concurrent.atomic.AtomicInteger;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;
import pl.mk5.gdx.fireapp.promises.ListenerPromise;

public class DatabaseListenerValueCancelTest extends E2ETest {

    private final AtomicInteger listenCounter = new AtomicInteger();
    private final AtomicInteger counter = new AtomicInteger();
    private Timer.Task scheduler;
    private ListenerPromise<String> listener;

    @Override
    public void action() {
        GdxFIRAuth.inst().signInAnonymously()
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        listener = GdxFIRDatabase.instance()
                                .inReference("/test-listen-value2")
                                .onDataChange(String.class)
                                .thenListener(new Consumer<String>() {
                                    @Override
                                    public void accept(String s) {
                                        Gdx.app.log("App", "new value: " + s);
                                        listenCounter.incrementAndGet();
                                    }
                                });
                    }
                });
        scheduler = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (listener == null) {
                    return;
                }
                Gdx.app.log("App", "set new value");
                GdxFIRDatabase.instance()
                        .inReference("/test-listen-value2")
                        .setValue("abc" + Math.random())
                        .after(GdxFIRAuth.instance().signInAnonymously())
                        .subscribe();
                if (counter.get() == 1) {
                    listener.cancel();
                }
                if (counter.incrementAndGet() >= 4) {
                    if (listenCounter.get() == 1) {
                        scheduler.cancel();
                        success();
                    }
                }
            }
        }, 0.3f, 0.6f);
    }

    @Override
    public void draw(Batch batch) {
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
    }
}
