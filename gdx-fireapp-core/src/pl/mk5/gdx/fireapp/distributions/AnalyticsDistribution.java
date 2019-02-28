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

package pl.mk5.gdx.fireapp.distributions;

import java.util.Map;

/**
 * Provides access to Firebase Analytics methods.
 * <p>
 *
 * @see <a href="https://firebase.google.com/docs/analytics/android/start">android firebase docs</a>
 * @see <a href="https://firebase.google.com/docs/analytics/ios/start">ios firebase docs</a>
 */
public interface AnalyticsDistribution {

    /**
     * Sends analytics event.
     *
     * @param name   Event name, default events are defined here {@link pl.mk5.gdx.fireapp.analytics.AnalyticsEvent}
     * @param params Event params, default params are defined here {@link pl.mk5.gdx.fireapp.analytics.AnalyticsParam}
     */
    void logEvent(String name, Map<String, String> params);

    /**
     * Sends information about current screen.
     *
     * @param name        Screen name, can be any String name you want
     * @param screenClass Screen class, here you can give your scene/screen/state class. For ex. {@code MenuScreen.class}
     */
    void setScreen(String name, Class<?> screenClass);

    /**
     * Sends user property.
     *
     * @param name  Property name, you can read more about it <a href="https://firebase.google.com/docs/analytics/android/properties">here</a>
     * @param value Property value
     */
    void setUserProperty(String name, String value);

    /**
     * Sends user id.
     *
     * @param id Current user id
     */
    void setUserId(String id);
}
