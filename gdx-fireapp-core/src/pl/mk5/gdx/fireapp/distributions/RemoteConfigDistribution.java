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

package pl.mk5.gdx.fireapp.distributions;

import java.util.Map;
import java.util.Set;

import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Remote Config distribution.
 * <p>
 * Firebase SDK reference: <a href="https://firebase.google.com/docs/remote-config">https://firebase.google.com/docs/remote-config</a>
 */
public interface RemoteConfigDistribution {

    RemoteConfigDistribution enableDevMode();

    RemoteConfigDistribution setDefaults(Map<String, Object> defaults);

    Promise<Void> fetchAndActivate();

    <T> T get(Class<T> type, String key);

    Set<String> getKeysByPrefix(String prefix);
}
