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
import java.util.concurrent.ConcurrentHashMap;

import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution;
import pl.mk5.gdx.fireapp.distributions.AppDistribution;
import pl.mk5.gdx.fireapp.distributions.AuthDistribution;
import pl.mk5.gdx.fireapp.distributions.CrashDistribution;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.distributions.StorageDistribution;

class Api {
    private static final Map<Class, Object> apis = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> T instance(final Class<T> api) {
        if (api == AnalyticsDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRAnalytics());
            }
            return (T) apis.get(api);
        } else if (api == AuthDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRAuth());
            }
            return (T) apis.get(api);
        } else if (api == CrashDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRCrash());
            }
            return (T) apis.get(api);
        } else if (api == DatabaseDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRDatabase());
            }
            return (T) apis.get(api);
        } else if (api == StorageDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRStorage());
            }
            return (T) apis.get(api);
        } else if (api == AppDistribution.class) {
            if (!apis.containsKey(api)) {
                apis.put(api, new GdxFIRApp());
            }
            return (T) apis.get(api);
        } else {
            throw new IllegalArgumentException();
        }
    }
}
