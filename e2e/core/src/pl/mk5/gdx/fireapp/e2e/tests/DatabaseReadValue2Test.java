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

import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseReadValue2Test extends E2ETest {

    @Override
    public void action() {
        final Long longValue = 40L;
        GdxFIRDatabase.instance()
                .inReference("/test-value-long")
                .setValue(longValue)
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void v) {
                        GdxFIRDatabase.instance()
                                .inReference("/test-value-long")
                                .readValue(Long.class)
                                .then(new Consumer<Long>() {
                                    @Override
                                    public void accept(Long result) {
                                        if (result.equals(longValue)) {
                                            success();
                                        }
                                    }
                                });
                    }
                });
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
