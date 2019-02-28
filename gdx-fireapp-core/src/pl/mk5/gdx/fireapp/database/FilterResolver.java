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

package pl.mk5.gdx.fireapp.database;

/**
 * Provides filtering to object {@code T} received in methods.
 * <p>
 *
 * @param <T> The type of target object
 * @param <R> The type of return object
 */
public interface FilterResolver<T, R> {
    <V> R resolve(FilterType filterType, T target, V[] filterArguments);
}
