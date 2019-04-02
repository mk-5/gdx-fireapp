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

import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseReadPojoMapWithKeysTest extends E2ETest {

    @Override
    public void action() {
        final Employee employee1 = new Employee("Fred", (long) Math.floor(Math.random() * 100000000));
        final Employee employee2 = new Employee("Bob", (long) Math.floor(Math.random() * 100000000));
        final Employee employee3 = new Employee("John", (long) Math.floor(Math.random() * 100000000));
        GdxFIRDatabase.promise()
                .then(GdxFIRDatabase.inst().inReference("/employees").removeValue())
                .then(GdxFIRDatabase.inst().inReference("/employees").push().setValue(employee1))
                .then(GdxFIRDatabase.inst().inReference("/employees").push().setValue(employee2))
                .then(GdxFIRDatabase.inst().inReference("/employees").push().setValue(employee3))
                .then(GdxFIRDatabase.inst().inReference("/employees").filter(FilterType.LIMIT_FIRST, 2).readValue(Map.class))
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<Map>() {

                    @Override
                    public void accept(Map map) {
                        Gdx.app.log("App", "" + map);
                        success();
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

    private static class Employee {
        public String name;
        public Long salary;

        public Employee() {
        }

        public Employee(String name, Long salary) {
            this.name = name;
            this.salary = salary;
        }

    }
}
