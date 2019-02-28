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

public class DatabaseRemoveValueTest extends E2ETest {

    @Override
    public void action() {
        final String stringValue = "abcd";

        GdxFIRDatabase.instance()
                .inReference("/test-value")
                .setValue(stringValue)
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(GdxFIRDatabase.instance().inReference("/test-value").removeValue())
                .then(GdxFIRDatabase.instance().inReference("/test-value").readValue(String.class))
                .then(new Consumer<String>() {
                    @Override
                    public void accept(String v) {
                        if (v == null) {
                            success();
                        }
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
