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

package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

import java.util.Collections;
import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.ChildEventType;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseChildEventTest extends E2ETest {

    private Timer.Task checkTask;

    @Override
    public void action() {
        final Employee employee1 = new Employee("Fred", (long) Math.floor(Math.random() * 100000000));
        final Array<String> result = new Array<>();

        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/employees").onChildChange(List.class, ChildEventType.ADDED))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List list) {
                        Gdx.app.log("ChildEventTest", "added");
                        result.add("added");
                    }
                });

        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/employees").onChildChange(List.class, ChildEventType.CHANGED))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List list) {
                        Gdx.app.log("ChildEventTest", "changed");
                        result.add("changed");
                    }
                });

        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/employees").onChildChange(List.class, ChildEventType.REMOVED))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List list) {
                        Gdx.app.log("ChildEventTest", "removed");
                        result.add("removed");
                    }
                });

        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/employees").push().setValue(employee1))
                .then(GdxFIRDatabase.inst().inReference("/employees/1").setValue(employee1))
                .then(GdxFIRDatabase.inst().inReference("/employees/1").updateChildren(Collections.<String, Object>singletonMap("name", "Johnny")))
                .then(GdxFIRDatabase.inst().inReference("/employees").removeValue());


        checkTask = Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (result.contains("changed", true)
                        && result.contains("removed", true)
                        && result.contains("added", true)) {
                    checkTask.cancel();
                    success();
                }
            }
        }, 1f, 1f);
    }

    @Override
    public void draw(Batch batch) {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        if (checkTask != null) {
            checkTask.cancel();
        }
    }
}
