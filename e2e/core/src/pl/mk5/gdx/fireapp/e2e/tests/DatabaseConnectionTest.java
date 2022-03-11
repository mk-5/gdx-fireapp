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
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.auth.GdxFirebaseUser;
import pl.mk5.gdx.fireapp.database.ConnectionStatus;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseConnectionTest extends E2ETest {

    @Override
    public void action() {
        GdxFIRAuth.inst()
                .signInAnonymously()
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        Gdx.app.log("Test", "authorization done");
                        GdxFIRDatabase.inst()
                                .onConnect()
                                .thenListener(new Consumer<ConnectionStatus>() {
                                    @Override
                                    public void accept(ConnectionStatus connectionStatus) {
                                        Gdx.app.log("Test", "database connection done. Connection status: " + connectionStatus);
                                        if (connectionStatus == ConnectionStatus.CONNECTED) {
                                            GdxFIRDatabase.inst()
                                                    .inReference("/abc")
                                                    .onDisconnect()
                                                    .setValue("ON_DISCONNET")
                                                    .then(new Consumer<Void>() {
                                                        @Override
                                                        public void accept(Void unused) {
                                                            success();
                                                        }
                                                    });
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
