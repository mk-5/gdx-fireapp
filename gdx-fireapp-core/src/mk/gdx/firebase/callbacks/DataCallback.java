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
 * If you need to convert Firebase database Maps (each object is represented by Map in DB structure) you should
 * use {@link mk.gdx.firebase.annotations.MapConversion} annotation right before {@link #onData(Object)} method.
 *
 * @param <T> Type of data you expecting to get
 */
public interface DataCallback<T>
{

    /**
     * Calls when everything was done without issues.
     * <p>
     * If you need to convert Firebase database Maps (each object is represented by Map in DB structure) you should
     * use {@link mk.gdx.firebase.annotations.MapConversion} annotation here.
     *
     * @param data Received data from Firebase - data was cast specified generic type {@code <T>}, may be null.
     */
    void onData(T data);

    /**
     * Calls when something goes wrong.
     *
     * @param e Exception with description what was wrong.
     */
    void onError(Exception e);
}
