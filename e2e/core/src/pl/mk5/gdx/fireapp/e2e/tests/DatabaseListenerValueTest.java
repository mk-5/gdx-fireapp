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
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseListenerValueTest extends E2ETest {

    private final AtomicInteger listenCounter = new AtomicInteger();
    private final AtomicInteger counter = new AtomicInteger();
    private Timer.Task scheduler;

    @Override
    public void action() {
        GdxFIRDatabase.instance()
                .inReference("/test-listen-value")
                .onDataChange(String.class)
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        Gdx.app.log("App", "new value: " + s);
                        listenCounter.incrementAndGet();
                    }
                });
        scheduler = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                Gdx.app.log("App", "set new value");
                GdxFIRDatabase.instance()
                        .inReference("/test-listen-value")
                        .setValue("abc" + Math.random())
                        .after(GdxFIRAuth.instance().signInAnonymously())
                        .subscribe();

                if (counter.incrementAndGet() == 4) {
                    if (listenCounter.get() == 4 || listenCounter.get() == 3) {
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
