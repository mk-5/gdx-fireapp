/*
 * Copyright 2017 mk
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
 *
 *
 */

package pl.mk5.gdx.fireapp.html.firebase;

import com.badlogic.gdx.Gdx;
import com.google.gwt.core.client.ScriptInjector;

import pl.mk5.gdx.fireapp.GdxFIRLogger;

/**
 * Loads firebase configuration from {@code android/assets/firebase-config.html}.
 */
public class FirebaseConfiguration {

    private String rawHtml;
    private FirebaseConfigParser configParser;

    /**
     * Returns itself with html from {@code firebase-config.html} file.
     *
     * @return self
     */
    public FirebaseConfiguration load() {
        Gdx.app.log(GdxFIRLogger.getLogTag(), "Loading firebase config...");
        if (!Gdx.files.internal("firebase-config.html").exists()) {
            Gdx.app.error(GdxFIRLogger.getLogTag(), "Can't find firebase-config.html file.");
            return this;
        }
        rawHtml = Gdx.files.internal("firebase-config.html").readString();
        configParser = new FirebaseConfigParser(rawHtml);
        return this;
    }

    /**
     * Injects lazy firebase loading inside GWT application.
     */
    public void init() {
        if (rawHtml == null) {
            Gdx.app.error(GdxFIRLogger.getLogTag(), "You forgot about calling FirebaseConfiguration#init() first.");
            return;
        }
        ScriptInjector.fromUrl(configParser.getFirebaseScriptSrc())
                .setRemoveTag(false)
                .setWindow(ScriptInjector.TOP_WINDOW)
                .setCallback(new ScriptLoadedCallback(configParser))
                .inject();
    }

    /**
     * @return Config parser, may be null.
     */
    public FirebaseConfigParser getConfigParser() {
        return configParser;
    }

}
