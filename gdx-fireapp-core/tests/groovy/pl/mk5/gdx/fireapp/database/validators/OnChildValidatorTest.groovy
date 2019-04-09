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

package pl.mk5.gdx.fireapp.database.validators

import com.badlogic.gdx.utils.Array
import pl.mk5.gdx.fireapp.database.ChildEventType
import spock.lang.Specification

class OnChildValidatorTest extends Specification {

    def "should pass valid arguments"() {
        given:
        def validator = new OnChildValidator()
        def eventType = new ChildEventType[1]
        eventType[0] = ChildEventType.CHANGED

        when:
        validator.validate(new Array<Object>(Arrays.asList(String.class, eventType).toArray()))

        then:
        notThrown(IllegalArgumentException)
    }

    def "should not allow wrong arguments"(Array<Object> arguments) {
        given:
        def validator = new OnChildValidator()

        when:
        validator.validate(arguments)

        then:
        thrown(IllegalArgumentException)

        where:
        arguments << [new Array<Object>(Arrays.asList("abc", "absds").toArray())]
    }
}
