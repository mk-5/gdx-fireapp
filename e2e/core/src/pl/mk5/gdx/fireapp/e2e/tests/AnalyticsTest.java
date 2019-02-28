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

import java.util.Collections;
import java.util.UUID;

import pl.mk5.gdx.fireapp.GdxFIRAnalytics;
import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETestRunner;

public class AnalyticsTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        GdxFIRApp.instance().configure();
        GdxFIRAnalytics.instance()
                .setScreen("E2E Screen", E2ETestRunner.class);
        GdxFIRAnalytics.instance()
                .logEvent("e2etest", Collections.singletonMap("param1", "value1"));
        GdxFIRAnalytics.instance()
                .setUserId(UUID.randomUUID().toString());
        GdxFIRAnalytics.instance()
                .setUserProperty("user_prop", "test");
        success();
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
