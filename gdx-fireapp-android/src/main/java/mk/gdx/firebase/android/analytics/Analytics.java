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

package mk.gdx.firebase.android.analytics;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

import mk.gdx.firebase.distributions.AnalyticsDistribution;

/**
 * Android Firebase analytics API implementation.
 * <p>
 *
 * @see AnalyticsDistribution
 */
public class Analytics implements AnalyticsDistribution {

    private static FirebaseAnalytics firebaseAnalytics;

    /**
     * Analytics class constructor.
     */
    public Analytics() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logEvent(String name, Map<String, String> params) {
        Bundle bundle = null;
        if (params != null) {
            bundle = new Bundle();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (Character.isDigit(value.charAt(0))) {
                    BundleHelper.putNumberParam(bundle, key, value);
                } else {
                    bundle.putString(key, value);
                }
            }
        }
        getFirebaseAnalytics().logEvent(name, bundle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreen(String name, Class<?> screenClass) {
        getFirebaseAnalytics().setCurrentScreen((AndroidApplication) Gdx.app, name, screenClass.getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value) {
        getFirebaseAnalytics().setUserProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id) {
        getFirebaseAnalytics().setUserId(id);
    }

    /**
     * Get FirebaseAnalytics instance in lazy-loading way.
     *
     * @return Instance of FirebaseAnalytics class. This instance should be use later to do some analytics actions.
     */
    protected FirebaseAnalytics getFirebaseAnalytics() {
        if (firebaseAnalytics == null)
            firebaseAnalytics = FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app);
        return firebaseAnalytics;
    }

}
