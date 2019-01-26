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

package mk.gdx.firebase.promises

import mk.gdx.firebase.functional.BiConsumer
import mk.gdx.firebase.functional.Consumer
import spock.lang.Specification

class FuturePromiseTest extends Specification {

    def "should call then consumer"() {
        given:
        def futurePromise = new FuturePromise()
        def consumer = Mock(Consumer)
        def someResult = "abc"

        when:
        futurePromise.then(consumer).doComplete(someResult)

        then:
        1 * consumer.accept(someResult)
    }

    def "should call then promises"() {
        given:
        def futurePromise = new FuturePromise()
        def promise1 = Spy(FuturePromise)
        def promise2 = Spy(FuturePromise)
        def consumer1 = Mock(Consumer)
        def consumer2 = Mock(Consumer)
        def someResult = "abc"
        promise1.then(consumer1)
        promise2.then(consumer2)

        when:
        futurePromise.then(promise1).then(promise2)
                .doComplete(someResult)

        then:
        1 * consumer1.accept(someResult)
        1 * consumer2.accept(someResult)
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
}
