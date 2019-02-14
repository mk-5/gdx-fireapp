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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseLimitTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final Map<String, String> employee = new HashMap<>();
        final Map<String, String> employee2 = new HashMap<>();
        employee.put("name", "bob");
        employee.put("salary", "1000");
        employee.put("name", "john");
        employee.put("salary", "5000");

        GdxFIRAuth.instance()
                .signInAnonymously()
                .then(GdxFIRDatabase.instance()
                        .inReference("/employee")
                        .push()
                        .setValue(employee))
                .then(GdxFIRDatabase.instance()
                        .inReference("/employee")
                        .push()
                        .setValue(employee2))
                .then(GdxFIRDatabase.instance()
                        .inReference("/employee")
                        .filter(FilterType.LIMIT_FIRST, 1)
                        .readValue(List.class))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List list) {
                        if (list.size() == 1) {
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
