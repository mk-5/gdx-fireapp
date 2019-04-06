/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.android.database

import com.google.firebase.database.DataSnapshot
import pl.mk5.gdx.fireapp.promises.ConverterPromise
import spock.lang.Specification

class DataSnapshotResolverTest extends Specification {

    def "should resolve empty list"() {
        given:
        def promise = Spy(ConverterPromise)
        def resolver = new DataSnapshotResolver(List.class, promise)
        def dataSnapshot = Mock(DataSnapshot)

        when:
        resolver.resolve(dataSnapshot)

        then:
        1 * promise.doComplete(Collections.emptyList())
    }

    def "should resolve data"() {
        given:
        def promise = Spy(ConverterPromise)
        def resolver = new DataSnapshotResolver(Map.class, promise)
        def dataSnapshot = Mock(DataSnapshot)
        dataSnapshot.value >> "abc"

        when:
        resolver.resolve(dataSnapshot)

        then:
        1 * promise.doComplete("abc")
    }

}
