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

package pl.mk5.gdx.fireapp;

import pl.mk5.gdx.fireapp.distributions.CrashDistribution;

/**
 * Gets access to Firebase Crash API in multi-modules.
 *
 * @see CrashDistribution
 * @see PlatformDistributor
 */
public class GdxFIRCrash extends PlatformDistributor<CrashDistribution> implements CrashDistribution {

    private static volatile GdxFIRCrash instance;

    /**
     * GdxFIRCrash protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    GdxFIRCrash() {
    }

    public static GdxFIRCrash instance() {
        return (GdxFIRCrash) Api.instance(CrashDistribution.class);
    }

    /**
     * Alias of {@link #instance()}
     */
    public static GdxFIRCrash inst() {
        return instance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        platformObject.log(message);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initialize() {
        platformObject.initialize();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "pl.mk5.gdx.fireapp.ios.crash.Crash";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.android.crash.Crash";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "pl.mk5.gdx.fireapp.html.crash.Crash";
    }
}
