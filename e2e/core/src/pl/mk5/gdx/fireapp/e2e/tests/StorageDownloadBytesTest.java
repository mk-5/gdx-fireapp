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

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRStorage;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;

public class StorageDownloadBytesTest extends E2ETest {

    @Override
    public void action() {
        GdxFIRStorage.instance()
                .download(StorageUploadBytesTest.FILE_PATH, Long.MAX_VALUE)
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<byte[]>() {
                    @Override
                    public void accept(byte[] bytes) {
                        if (new String(bytes).equals(StorageUploadBytesTest.FILE_VALUE)) {
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
