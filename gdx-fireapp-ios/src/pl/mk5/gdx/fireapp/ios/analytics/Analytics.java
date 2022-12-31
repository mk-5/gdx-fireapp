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

package pl.mk5.gdx.fireapp.ios.analytics;

import org.robovm.apple.foundation.NSMutableDictionary;
import org.robovm.apple.foundation.NSNumber;
import org.robovm.apple.foundation.NSObject;
import org.robovm.apple.foundation.NSOperationQueue;
import org.robovm.apple.foundation.NSString;
import org.robovm.pods.firebase.analytics.FIRAnalytics;

import java.util.Map;

import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution;

/**
 * iOS Firebase analytics API implementation.
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
        NSMutableDictionary<NSString, NSObject> dictionaryParams = null;
        if (params != null) {
            dictionaryParams = new NSMutableDictionary<>();
            for (String key : params.keySet()) {
                String value = params.get(key);
                Long valueAsLong = asLongOrNull(value);
                Double valueAsDouble = asDoubleOrNull(value);
                if (valueAsLong != null) {
                    dictionaryParams.put(new NSString(key), NSNumber.valueOf(valueAsLong));
                } else if (valueAsDouble != null) {
                    dictionaryParams.put(new NSString(key), NSNumber.valueOf(valueAsDouble));
                } else {
                    dictionaryParams.put(new NSString(key), new NSString(value));
                }
            }
        }
        FIRAnalytics.logEvent(name, dictionaryParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreen(final String name, final Class<?> screenClass) {
        // do nothing / setScreen no longer available, after firebase > 8.x
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value) {
        FIRAnalytics.setUserPropertyString(value, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id) {
        FIRAnalytics.setUserID(id);
    }

    private Long asLongOrNull(String value) {
        try {
            if (value.endsWith("l") || value.endsWith("L")) {
                return Long.parseLong(value.substring(0, value.length() - 1));
            } else {
                return Long.parseLong(value);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double asDoubleOrNull(String value) {
        try {
            if (value.endsWith("f") || value.endsWith("F")) {
                return Double.parseDouble(value.substring(0, value.length() - 1));
            } else {
                return Double.parseDouble(value);
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
