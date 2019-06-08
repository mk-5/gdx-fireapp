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

package pl.mk5.gdx.fireapp;

import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.Map;
import java.util.Set;

import pl.mk5.gdx.fireapp.distributions.RemoteConfigDistribution;
import pl.mk5.gdx.fireapp.promises.Promise;

public class GdxFIRConfig extends PlatformDistributor<RemoteConfigDistribution> implements RemoteConfigDistribution {

    private static final String GET_ILLEGAL_ARGUMENT = "Get method type should be assignable from one of: Double, Boolean, ByteArray, String, Long";
    private static final String WEB_GL_NOT_SUPPORTED = "WebGL platform is not supported right now";

    @Override
    public RemoteConfigDistribution enableDevMode() {
        return platformObject.enableDevMode();
    }

    @Override
    public RemoteConfigDistribution setDefaults(Map<String, Object> defaults) {
        return platformObject.setDefaults(defaults);
    }

    @Override
    public Promise<Void> fetchAndActivate() {
        return platformObject.fetchAndActivate();
    }

    @Override
    public <T> T get(Class<T> type, String key) {
        if (!ClassReflection.isAssignableFrom(Double.class, type) &&
                !ClassReflection.isAssignableFrom(byte[].class, type) &&
                !ClassReflection.isAssignableFrom(Long.class, type) &&
                !ClassReflection.isAssignableFrom(String.class, type) &&
                !ClassReflection.isAssignableFrom(Boolean.class, type)
        ) {
            throw new IllegalArgumentException(GET_ILLEGAL_ARGUMENT);
        }
        return platformObject.get(type, key);
    }

    @Override
    public Set<String> getKeysByPrefix(String prefix) {
        return platformObject.getKeysByPrefix(prefix);
    }

    @Override
    protected String getIOSClassName() {
        return "pl.mk5.gdx.fireapp.android.config.RemoteConfig";
    }

    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.ios.config.RemoteConfig";
    }

    @Override
    protected String getWebGLClassName() {
        throw new IllegalStateException(WEB_GL_NOT_SUPPORTED);
    }
}
