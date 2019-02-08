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

package pl.mk5.gdxfireapp.e2e.tests;

import com.badlogic.gdx.graphics.g2d.Batch;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRStorage;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.storage.FileMetadata;
import pl.mk5.gdxfireapp.e2e.runner.E2ETest;

public class StorageUploadBytesTest extends E2ETest {

    final static String FILE_PATH = "some" + (Math.floor(Math.random() * 100000)) + ".txt";
    final static String FILE_VALUE = "123456";

    @Override
    public void action() {
        GdxFIRStorage.instance()
                .upload(FILE_PATH, FILE_VALUE.getBytes())
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<FileMetadata>() {
                    @Override
                    public void accept(FileMetadata fileMetadata) {
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
}
