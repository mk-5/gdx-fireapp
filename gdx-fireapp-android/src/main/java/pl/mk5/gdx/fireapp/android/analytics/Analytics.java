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

package pl.mk5.gdx.fireapp.android.analytics;

import android.os.Bundle;
import android.text.TextUtils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Map;

import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution;

/**
 * Android Firebase analytics API implementation.
 * <p>
 *
 * @see AnalyticsDistribution
 */
public class Analytics implements AnalyticsDistribution {

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
                if (TextUtils.isDigitsOnly(value)) {
                    BundleHelper.putNumberParam(bundle, key, value);
                } else {
                    bundle.putString(key, value);
                }
            }
        }
        FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app).logEvent(name, bundle);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreen(final String name, final Class<?> screenClass) {
        ((AndroidApplication) Gdx.app).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app).setCurrentScreen((AndroidApplication) Gdx.app, name, screenClass.getSimpleName());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value) {
        FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app).setUserProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id) {
        FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app).setUserId(id);
    }

}
