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

package pl.mk5.gdx.fireapp.promises;

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Listener promise
 */
public interface ListenerPromise<T> extends Promise<T> {

    /**
     * Attach listener to this promise
     *
     * @param listener The listener consumer, not null
     * @see Promise#then(Consumer)
     */
    ListenerPromise<T> thenListener(Consumer<T> listener);

    /**
     * Cancel this promise
     */
    ListenerPromise<T> cancel();

    /**
     * Start listening, do same logic as {@link Promise#exec()}
     */
    ListenerPromise<T> listen();
}
