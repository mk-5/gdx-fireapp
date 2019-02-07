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

package mk.gdx.firebase.promises;

import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;

/**
 * Public promise interface.
 */
public interface Promise<T> {

    /**
     * Sets then consumer.
     *
     * @param consumer "Then" consumer, will be call when promise had been competed.
     */
    Promise<T> then(Consumer<T> consumer);

    /**
     * Promise will throw exception on fail
     */
    Promise<T> throwFail();

    /**
     * Sets fail bi-consumer.
     *
     * @param consumer "Fail" bi-consumer, will be call when promise had been failed.
     */
    Promise<T> fail(BiConsumer<String, ? super Throwable> consumer);

    /**
     * Sets then consumer.
     *
     * @param promise The next promise. The promise will not be executed until parent promise complete
     */
    <R> Promise<R> then(Promise<R> promise);

    /**
     * Sets this given promise after this promise.
     *
     * @param promise The future promise, not null
     * @return self
     */
    Promise<T> after(Promise<?> promise);

    /**
     * Run promise execution, should be execute when you will not attach .then or .fail to the promise.
     * TODO - should name be 'exec'?
     * <p>
     * If you call {@link #then(Consumer)} or {@link #fail(BiConsumer)} it is not mandatory
     */
    Promise<T> exec();
}
