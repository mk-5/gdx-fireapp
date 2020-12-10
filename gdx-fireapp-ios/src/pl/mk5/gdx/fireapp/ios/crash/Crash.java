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

package pl.mk5.gdx.fireapp.ios.crash;

import com.badlogic.gdx.Gdx;

import org.robovm.pods.firebase.crashlytics.FIRCrashlytics;

import pl.mk5.gdx.fireapp.distributions.CrashDistribution;

/**
 * iOS Firebase crash API implementation.
 * <p>
 *
 * @see CrashDistribution
 */
public class Crash implements CrashDistribution {
    private static final String LOG = "CrashIOS";
    private static final String NOT_SUPPORTED = "Method not supported for iOS backend";

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        Gdx.app.error(LOG, NOT_SUPPORTED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        FIRCrashlytics.registerDefaultJavaUncaughtExceptionHandler();
    }

    @Override
    public void recordException(Throwable throwable) {
        Gdx.app.error(LOG, NOT_SUPPORTED);
    }

    @Override
    public void setUserId(String userId) {
        Gdx.app.error(LOG, NOT_SUPPORTED);
    }

    @Override
    public <T> void setCustomKey(String key, T value) {
        Gdx.app.error(LOG, NOT_SUPPORTED);
    }
}
