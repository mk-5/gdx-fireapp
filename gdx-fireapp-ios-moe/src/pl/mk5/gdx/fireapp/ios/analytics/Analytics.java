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

import java.util.Map;

import apple.foundation.NSDictionary;
import apple.foundation.NSMutableDictionary;
import apple.foundation.NSOperationQueue;
import apple.foundation.NSString;
import bindings.google.firebaseanalytics.FIRAnalytics;
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
    @SuppressWarnings("unchecked")
    public void logEvent(String name, Map<String, String> params) {
        NSDictionary<String, NSString> dictionaryParams;
        if (params != null) {
            dictionaryParams = (NSDictionary<String, NSString>) NSMutableDictionary.alloc().init();
            for (String key : params.keySet()) {
                String value = params.get(key);
                dictionaryParams.put(key, NSString.alloc().initWithString(value));
            }
        } else {
            dictionaryParams = null;
        }
        FIRAnalytics.logEventWithNameParameters(name, dictionaryParams);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setScreen(final String name, final Class<?> screenClass) {
        NSOperationQueue.mainQueue().addOperationWithBlock(new NSOperationQueue.Block_addOperationWithBlock() {
            @Override
            public void call_addOperationWithBlock() {
                FIRAnalytics.setScreenNameScreenClass(name, screenClass.getSimpleName());
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserProperty(String name, String value) {
        FIRAnalytics.setUserPropertyStringForName(value, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUserId(String id) {
        FIRAnalytics.setUserID(id);
    }

}
