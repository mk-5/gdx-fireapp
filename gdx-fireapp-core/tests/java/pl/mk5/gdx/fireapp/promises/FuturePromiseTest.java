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

import pl.mk5.gdx.fireapp.functional.BiConsumer;
import pl.mk5.gdx.fireapp.functional.Consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class FuturePromiseTest {

    @Test
    public void shouldGetBottomPromise() {
        // Given
        FuturePromise rootPromise = spy(FuturePromise.class);
        FuturePromise promise1 = FuturePromise.empty();
        FuturePromise promise2 = FuturePromise.empty();
        FuturePromise promise3 = FuturePromise.empty();
        FuturePromise promise4 = FuturePromise.empty();
        FuturePromise promise5 = FuturePromise.empty();

        // When
        rootPromise
                .then(promise1)
                .then(promise2)
                .then(promise3)
                .then(promise4)
                .then(promise5);

        // Then
        Assert.assertEquals(promise5, rootPromise.getBottomThenPromise());
        Assert.assertEquals(promise5, promise1.getBottomThenPromise());
        Assert.assertEquals(promise5, promise2.getBottomThenPromise());
        Assert.assertEquals(promise5, promise3.getBottomThenPromise());
        Assert.assertEquals(promise5, promise4.getBottomThenPromise());
    }

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

    @Test
    public void shouldCallFailConsumerCorrectly() {
        // Given
        final List<Integer> result = new ArrayList<>();
        FuturePromise rootPromise = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doFail("", new RuntimeException());
            }
        });
        FuturePromise promise1 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(1);
                futurePromise.doComplete("");
            }
        });
        FuturePromise promise2 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(1);
                futurePromise.doComplete("");
            }
        });
        FuturePromise promise3 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(1);
                futurePromise.doComplete("");
            }
        });
        FuturePromise promise4 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                result.add(1);
                futurePromise.doComplete("");
            }
        });
        BiConsumer consumer = mock(BiConsumer.class);

        // When
        rootPromise
                .then(promise1)
                .then(promise2)
                .then(promise3)
                .then(promise4)
                .fail(consumer);

        // Then
        verify(consumer, times(1)).accept(anyString(), any(Throwable.class));
        Assert.assertEquals(0, result.size());
    }

    @Test
    public void shouldCallFailConsumerCorrectly2() {
        // Given
        FuturePromise rootPromise = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doComplete("");
            }
        });
        final FuturePromise promise1 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doFail("", new RuntimeException());
            }
        });
        final FuturePromise promise2 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doComplete("");
            }
        });
        final FuturePromise promise3 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doComplete("");
            }
        });
        final FuturePromise promise4 = FuturePromise.when(new Consumer<FuturePromise<Object>>() {
            @Override
            public void accept(FuturePromise futurePromise) {
                futurePromise.doComplete("");
            }
        });
        BiConsumer consumer = mock(BiConsumer.class);

        // When
        rootPromise
                .then(promise1)
                .then(promise2)
                .then(promise3)
                .then(promise4)
                .fail(consumer);

        // Then
        verify(consumer, times(1)).accept(anyString(), any(Throwable.class));
    }
}