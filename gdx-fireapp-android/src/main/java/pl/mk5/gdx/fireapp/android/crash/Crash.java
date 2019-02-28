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

package pl.mk5.gdx.fireapp.android.crash;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import pl.mk5.gdx.fireapp.distributions.CrashDistribution;

/**
 * Android Firebase crash API implementation.
 * <p>
 *
 * @see CrashDistribution
 */
public class Crash implements CrashDistribution {

    private boolean initialized;

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        initializeOnce();
        Crashlytics.log(message);
    }

    @Override
    public void initialize() {
        initializeOnce();
    }

    private synchronized void initializeOnce() {
        if (!initialized) {
            initialized = true;
            Fabric.with((AndroidApplication) Gdx.app, new Crashlytics());
        }
    }
}
