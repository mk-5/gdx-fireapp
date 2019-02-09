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
 */

package pl.mk5.gdx.fireapp.html;

import pl.mk5.gdx.fireapp.distributions.AppDistribution;
import pl.mk5.gdx.fireapp.html.firebase.FirebaseConfiguration;

/**
 * Javascript Firebase SDK app API.
 *
 * @see AppDistribution
 */
public class App implements AppDistribution {

    /**
     * Loads configuration from {@code Gdx.files.internal("firebase-config.html")} file.
     * <p>
     * {@code firebase-config.html} should be just copy-paste configuration from Firebase console.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void configure() {
        FirebaseConfiguration configuration = new FirebaseConfiguration();
        configuration.load().init();
    }
}
