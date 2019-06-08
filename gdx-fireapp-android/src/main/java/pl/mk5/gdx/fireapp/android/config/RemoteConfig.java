/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.android.config;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.Map;
import java.util.Set;

import pl.mk5.gdx.fireapp.distributions.RemoteConfigDistribution;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

public class RemoteConfig implements RemoteConfigDistribution {

    @Override
    public RemoteConfigDistribution enableDevMode() {
        FirebaseRemoteConfig.getInstance()
                .setConfigSettings(new FirebaseRemoteConfigSettings.Builder()
                        .setDeveloperModeEnabled(true)
                        .build());
        return this;
    }

    @Override
    public RemoteConfigDistribution setDefaults(Map<String, Object> defaults) {
        FirebaseRemoteConfig.getInstance()
                .setDefaults(defaults);
        return this;
    }

    @Override
    public Promise<Void> fetchAndActivate() {
        return FuturePromise.when(new RemoteConfigPromiseConsumer());
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> type, String key) {
        if (ClassReflection.isAssignableFrom(Long.class, type)) {
            return (T) Long.valueOf(FirebaseRemoteConfig.getInstance().getLong(key));
        } else if (ClassReflection.isAssignableFrom(Boolean.class, type)) {
            return (T) Boolean.valueOf(FirebaseRemoteConfig.getInstance().getBoolean(key));
        } else if (ClassReflection.isAssignableFrom(Double.class, type)) {
            return (T) Double.valueOf(FirebaseRemoteConfig.getInstance().getDouble(key));
        } else if (ClassReflection.isAssignableFrom(String.class, type)) {
            return (T) String.valueOf(FirebaseRemoteConfig.getInstance().getString(key));
        } else if (ClassReflection.isAssignableFrom(byte[].class, type)) {
            return (T) FirebaseRemoteConfig.getInstance().getByteArray(key);
        } else {
            return null;
        }
    }

    @Override
    public Set<String> getKeysByPrefix(String prefix) {
        return FirebaseRemoteConfig.getInstance()
                .getKeysByPrefix(prefix);
    }
}
