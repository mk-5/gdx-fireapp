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

import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.functional.Function;

/**
 * @param <T> Modified promise type
 * @param <R> Output promise type
 */
public class ModifierPromise<T, R> extends FuturePromise<R> {

    private Function<T, R> modifier;

    public synchronized void doCompleteModify(T modifiedResult) {
        if (modifier == null)
            throw new IllegalStateException();
        R result = modifier.apply(modifiedResult);
        super.doComplete(result);
    }

    public static <T, R> ModifierPromise<T, R> ofModifier(Consumer<ModifierPromise<T, R>> consumer) {
        ModifierPromise<T, R> promise = new ModifierPromise<>();
        consumer.accept(promise);
        return promise;
    }
}
