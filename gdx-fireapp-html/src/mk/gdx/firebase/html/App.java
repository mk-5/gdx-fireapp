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

package mk.gdx.firebase.html;

import mk.gdx.firebase.distributions.AppDistribution;
import mk.gdx.firebase.html.firebase.FirebaseConfiguration;

/**
 * Javascript Firebase SDK app API.
 * <p>
 * <p>
 * TODO - Reflection NOTE
 * GWT can make many problems with reflection. At this point i can run code on GWT platform without reflection errors by following script:
 * <p>
 * ./gradlew html:dist
 * <p>
 * `html:dist` is also only way in LibGDX (as far as i know) to look at IReflectionCache errors.
 * For some reason SuperDev does not respect <extend-configuration-property name="gdx.reflect.include"> and throws errors like:
 * Can't find type xxx for name "mk.gdx.firebase.xxx"
 *
 * @see AppDistribution
 */
public class App implements AppDistribution
{

    /**
     * Loads configuration from {@code Gdx.files.internal("firebase-config.html")} file.
     * <p>
     * {@code firebase-config.html} should be just copy-paste configuration from Firebase console.
     * <p>
     * {@inheritDoc}
     */
    @Override
    public void configure()
    {
        FirebaseConfiguration configuration = new FirebaseConfiguration();
        configuration.load().init();
    }
}
