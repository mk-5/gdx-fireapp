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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Arrays;
import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseOrderByTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final List<String> names = Arrays.asList("Fred", "Bob", "Andreas", "Adams");

        GdxFIRAuth.instance()
                .signInAnonymously()
                .then(GdxFIRDatabase.instance()
                        .inReference("/test-names")
                        .setValue(names))
                .then(GdxFIRDatabase.instance()
                        .inReference("/test-names")
                        .orderBy(OrderByMode.ORDER_BY_VALUE)
                        .readValue(List.class))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List list) {
                        if (list.size() == 4
                                && list.get(0).equals("Adams")
                                && list.get(1).equals("Andreas")
                                && list.get(2).equals("Bob")
                                && list.get(3).equals("Fred")) {
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
