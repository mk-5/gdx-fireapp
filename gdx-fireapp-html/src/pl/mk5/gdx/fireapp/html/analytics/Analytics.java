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

package pl.mk5.gdx.fireapp.html.analytics;

import java.util.Map;

import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution;

/**
 * @see AnalyticsDistribution
 */
public class Analytics implements AnalyticsDistribution {

    /**
     * Do nothing, firebase javascript api do not support analytics
     *
     * @param name   Event name, default events are defined here {@link pl.mk5.gdx.fireapp.analytics.AnalyticsEvent}
     * @param params Event params, default params are defined here {@link pl.mk5.gdx.fireapp.analytics.AnalyticsParam}
     */
    @Override
    public void logEvent(String name, Map<String, String> params) {
        // Do nothing
    }

    /**
     * Do nothing, firebase javascript api do not support analytics
     *
     * @param name        Screen name, can be any String name you want
     * @param screenClass Screen class, here you can give your scene/screen/state class. For ex. {@code MenuScreen.class}
     */
    @Override
    public void setScreen(String name, Class<?> screenClass) {
        // Do nothing
    }

    /**
     * Do nothing, firebase javascript api do not support analytics
     *
     * @param name  Property name, you can read more about it <a href="https://firebase.google.com/docs/analytics/android/properties">here</a>
     * @param value Property value
     */
    @Override
    public void setUserProperty(String name, String value) {
        // Do nothing
    }

    /**
     * Do nothing, firebase javascript api do not support analytics
     *
     * @param id Current user id
     */
    @Override
    public void setUserId(String id) {
        // Do nothing
    }
}
