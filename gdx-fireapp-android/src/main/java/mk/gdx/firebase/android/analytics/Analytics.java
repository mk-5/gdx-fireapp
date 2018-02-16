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
public class Analytics implements AnalyticsDistribution
{

    private static FirebaseAnalytics firebaseAnalytics;

    /**
     * Analytics class constructor.
     */
    public Analytics()
    {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logEvent(String name, Map<String, String> params)
    {
        Bundle bundle = null;
        if (params != null) {
            bundle = new Bundle();
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (Character.isDigit(value.charAt(0))) {
                    putNumberParam(bundle, key, value);
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
    public void setScreen(String name, Class<?> screenClass)
    {
        getFirebaseAnalytics().setCurrentScreen((AndroidApplication) Gdx.app, name, screenClass.getSimpleName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value)
    {
        getFirebaseAnalytics().setUserProperty(name, value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id)
    {
        getFirebaseAnalytics().setUserId(id);
    }

    /**
     * Get FirebaseAnalytics instance in lazy-loading way.
     *
     * @return Instance of FirebaseAnalytics class. This instance should be use later to do some analytics actions.
     */
    protected FirebaseAnalytics getFirebaseAnalytics()
    {
        if (firebaseAnalytics == null)
            firebaseAnalytics = FirebaseAnalytics.getInstance((AndroidApplication) Gdx.app);
        return firebaseAnalytics;
    }

    /**
     * Put numeric value from string inside given bundle.
     * If {@code number} do not contain numeric value - nothing will happen
     *
     * @param bundle Bundle in which you want to put String numeric value.
     * @param key    Bundle key where you want to put the new value.
     * @param number String which should contain numeric value.
     */
    private void putNumberParam(Bundle bundle, String key, String number)
    {
        String[] types = {"int", "long", "float", "double"};
        String type;
        for (int i = 0; i < types.length; i++) {
            try {
                type = types[i];
                switch (type) {
                    case "int":
                        int i1 = Integer.parseInt(number);
                        bundle.putInt(key, i1);
                        break;
                    case "long":
                        long i2 = Long.parseLong(number);
                        bundle.putLong(key, i2);
                        break;
                    case "float":
                        float i3 = Float.parseFloat(number);
                        bundle.putFloat(key, i3);
                        break;
                    case "double":
                        double i4 = Double.parseDouble(number);
                        bundle.putDouble(key, i4);
                        break;
                }
                break;
            } catch (NumberFormatException e) {
            }
        }
    }
}
