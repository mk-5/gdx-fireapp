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

package pl.mk5.gdx.fireapp.promises

import pl.mk5.gdx.fireapp.functional.BiConsumer
import pl.mk5.gdx.fireapp.functional.Consumer
import spock.lang.Specification

class FuturePromiseGroovyTest extends Specification {

    def "should call then consumer"() {
        given:
        def arrResult = []
        def futurePromise = FuturePromise.when({
            arrResult.push(1)
        })
        def consumer1 = Mock(Consumer)
        def consumer2 = Mock(Consumer)
        def consumer3 = Mock(Consumer)
        def consumer4 = Mock(Consumer)
        def someResult = "abc"

        when:
        futurePromise
                .then({
            arrResult.push(2)
        })
                .then(consumer1)
                .then(consumer2)
                .then(consumer3)
                .then(consumer4)
                .doComplete(someResult)

        then:
        [1, 2] == arrResult
        1 * consumer1.accept(someResult)
        1 * consumer2.accept(someResult)
        1 * consumer3.accept(someResult)
        1 * consumer4.accept(someResult)
    }

    def "should call then promises"() {
        given:
        def arrResult = []
        def someResult = "abc"
        def futurePromise = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(300)
                arrResult.push(1)
                rFuturePromise.doComplete(arrResult)
            }
        })
        def promise2 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(1000)
                arrResult.push(3)
                rFuturePromise.doComplete(arrResult)
            }
        })
        def promise3 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(200)
                arrResult.push(4)
                rFuturePromise.doComplete(arrResult)
            }
        })
        def promise1 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(500)
                arrResult.push(2)
                rFuturePromise.doComplete(arrResult)
            }
        })
        def consumer0 = Spy(new Consumer() {
            @Override
            void accept(Object o) {
                arrResult.push(5)
            }
        })

        when:
        futurePromise
                .then(promise1)
                .then(promise2)
                .then(promise3)
                .then(consumer0)

        then:
        [1, 2, 3, 4, 5] == arrResult
    }

    def "should call fail consumer"() {
        given:
        def futurePromise = new FuturePromise()
        def consumer = Mock(BiConsumer)
        def throwable = Mock(Throwable)
        def reason = "abc"

        when:
        futurePromise.fail(consumer).doFail(reason, throwable)

        then:
        1 * consumer.accept(reason, throwable)
    }

    def "should call fail promises"() {
        given:
        def arrResult = []
        def someResult = "abc"
        def futurePromise = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(300)
                arrResult.push(1)
                rFuturePromise.doComplete(arrResult)
            }
        })
        def promise1 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(1000)
                arrResult.push(3)
                throw new IllegalArgumentException()
            }
        })
        def consumer = Mock(BiConsumer)

        when:
        futurePromise
                .then(promise1)
                .fail(consumer)

        then:
        1 * consumer.accept(_, _)
    }

    def "should call fail promises2"() {
        given:
        def futurePromise = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(300)
                rFuturePromise.doComplete()
            }
        })
        def promise1 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(1000)
                throw new IllegalArgumentException()
            }
        })
        def promise2 = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                rFuturePromise.doComplete()
            }
        })
        def consumer = Mock(BiConsumer)

        when:
        futurePromise
                .then(promise1)
                .then(promise2)
                .fail(consumer)

        then:
        1 * consumer.accept(_, _)
    }

    def "should call fail of bottom chained promise2"() {
        given:
        def futurePromise = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(300)
                rFuturePromise.doFail("abc", new RuntimeException())
            }
        })
        def promise1 = Spy(FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                rFuturePromise.doComplete()
            }
        }) as FuturePromise)
        def promise2 = Spy(FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                rFuturePromise.doComplete()
            }
        }) as FuturePromise)
        def promise3 = Spy(FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                rFuturePromise.doComplete()
            }
        }) as FuturePromise)
        def consumer = Mock(BiConsumer)

        when:
        futurePromise
                .then(promise1)
                .then(promise2)
                .then(promise3)
                .fail(consumer)

        then:
        0 * promise1.doComplete()
        0 * promise2.doComplete()
        0 * promise3.doComplete()
        0 * promise1.execution.run()
        0 * promise2.execution.run()
        0 * promise3.execution.run()
        1 * consumer.accept(_, _)
    }

    def "should call do 'after flow'"() {
        given:
        def result = []
        def consumer = new Consumer() {
            @Override
            void accept(Object o) {
                result.push(3)
            }
        }
        def promise = FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(1000)
                result.push(1)
                rFuturePromise.doComplete()
            }
        }).after(FuturePromise.when(new Consumer<FuturePromise>() {
            @Override
            void accept(FuturePromise rFuturePromise) {
                Thread.sleep(200)
                result.push(2)
                rFuturePromise.doComplete()
            }
        }))

        when:
        promise.then(consumer)

        then:
        [2, 1, 3] == result
    }
}
