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

import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Public promise interface.
 */
public interface Promise<T> {

    /**
     * Sets then consumer.
     *
     * @param consumer Consumer will be call when this promise will be complete.
     */
    Promise<T> then(Consumer<T> consumer);

    /**
     * Promise will not throw exception on fail.
     */
    Promise<T> silentFail();

    /**
     * Sets fail bi-consumer.
     * <p>
     * If set - {@link #silentFail()} will be executed so promise will not cause exception on fail.
     *
     * @param consumer "Fail" bi-consumer, will be call when promise had been failed.
     */
    Promise<T> fail(BiConsumer<String, ? super Throwable> consumer);

    /**
     * Sets next promise.
     * <p>
     * Given promise will start execution when this promise will be complete.
     *
     * @param promise The next promise. The promise will not be executed until parent promise complete
     */
    <R> Promise<R> then(Promise<R> promise);

    /**
     * Sets this promise after given promise.
     * <p>
     * This promise will start execution after given {@code promise}
     *
     * @param promise The future promise, not null
     * @return self
     */
    Promise<T> after(Promise<?> promise);

    /**
     * Run promise flow execution.
     * <p>
     * Not necessary if {@link GdxFIRApp#isAutoSubscribePromises()} == true
     */
    Promise<T> subscribe();

    /**
     * Attach consumer by {@link #then(Consumer)} and then run {@link #subscribe()}
     *
     * @see #subscribe()
     */
    Promise<T> subscribe(Consumer<T> thenConsumer);
}
