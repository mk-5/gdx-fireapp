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

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import pl.mk5.gdx.fireapp.functional.Consumer;

import static org.mockito.Mockito.spy;

public class FuturePromiseTest {

    @Test
    public void shouldDoPromiseFlowCorrectly() {
        // Given
        final List<Integer> result = new ArrayList<>();
        FuturePromise promise = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(1);
                futurePromise.doComplete(null);
            }
        });
        FuturePromise promise1 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(2);
                futurePromise.doComplete(null);
            }
        });
        FuturePromise promise2 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(3);
                futurePromise.doComplete(null);
            }
        });
        Consumer consumer = spy(new Consumer() {
            @Override
            public void accept(Object o) {
                result.add(4);
            }
        });

        // When
        promise.then(promise1).then(promise2)
                .then(consumer);

        // Then
        Assert.assertArrayEquals("" + result, new Integer[]{1, 2, 3, 4}, result.toArray());
    }
}