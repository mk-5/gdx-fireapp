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

package pl.mk5.gdx.fireapp.android.crash;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import pl.mk5.gdx.fireapp.distributions.CrashDistribution;

/**
 * Android Firebase crash API implementation.
 * <p>
 *
 * @see CrashDistribution
 */
public class Crash implements CrashDistribution {

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(String message) {
        FirebaseCrashlytics.getInstance().log(message);
    }

    @Override
    public void initialize() {
        // do nothing
    }

    @Override
    public void recordException(Throwable throwable) {
        FirebaseCrashlytics.getInstance().recordException(throwable);
    }

    @Override
    public void setUserId(String userIdentifier) {
        FirebaseCrashlytics.getInstance().setUserId(userIdentifier);
    }

    @Override
    public <T> void setCustomKey(String key, T value) {
        if (value instanceof Boolean) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (Boolean) value);
        } else if (value instanceof String) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (String) value);
        } else if (value instanceof Double) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (Double) value);
        } else if (value instanceof Long) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (Long) value);
        } else if (value instanceof Float) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (Float) value);
        } else if (value instanceof Integer) {
            FirebaseCrashlytics.getInstance().setCustomKey(key, (Integer) value);
        } else {
            throw new IllegalStateException("Wrong value type. Supported types are [boolean, int, string, float, double, long]");
        }
    }

}
