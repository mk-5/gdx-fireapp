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

package mk.gdx.firebase.callbacks;

/**
 * Handles response when dealing with Firebase database data.
 * <p>
 * Remember to use type which is able to create new instance.
 * For ex. for List interface you should use ArrayList or others List implementation.
 *
 * @param <T> Type of data you expecting to get
 */
public interface DataCallback<T> {

    /**
     * Calls when everything was done without issues.
     * <p>
     *
     * @param data Fetched data from Firebase - data was processed and transformed to specified generic type {@code <T>}, not null.
     */
    void onData(T data);

    /**
     * Calls when something goes wrong.
     *
     * @param e Exception with description what was wrong.
     */
    void onError(Exception e);
}
