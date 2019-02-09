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

import java.util.Map;

import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution;

/**
 * Gets access to Firebase Analytics API in multi-modules.
 *
 * @see AnalyticsDistribution
 * @see PlatformDistributor
 */
public class GdxFIRAnalytics extends PlatformDistributor<AnalyticsDistribution> implements AnalyticsDistribution {
    private volatile static GdxFIRAnalytics instance;

    /**
     * GdxFIRAnalytics protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    private GdxFIRAnalytics(){
    }


    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRAnalytics instance() {
        GdxFIRAnalytics result = instance;
        if (result == null) {
            synchronized (GdxFIRAnalytics.class) {
                result = instance;
                if (result == null) {
                    instance = result = new GdxFIRAnalytics();
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logEvent(String name, Map<String, String> params) {
        platformObject.logEvent(name, params);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreen(String name, Class<?> screenClass) {
        platformObject.setScreen(name, screenClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value) {
        platformObject.setUserProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id) {
        platformObject.setUserId(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "pl.mk5.gdx.fireapp.ios.analytics.Analytics";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.android.analytics.Analytics";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "pl.mk5.gdx.fireapp.html.analytics.Analytics";
    }
}

