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

package pl.mk5.gdx.fireapp.reflection

import spock.lang.Specification
import spock.lang.Unroll

class DefaultTypeRecognizerTest extends Specification {

    @Unroll
    def "should recognize default values for '#type'"(Class type, Object expectedResult) {
        given:
        def param = type

        when:
        def result = DefaultTypeRecognizer.getDefaultValue(type)

        then:
        expectedResult == result

        where:
        type            || expectedResult
        Long.class      || 0
        Integer.class   || 0
        Double.class    || 0.doubleValue()
        Float.class     || 0.doubleValue()
        Character.class || '\u0000'
        long.class      || 0
        int.class       || 0
        double.class    || 0.doubleValue()
        float.class     || 0.doubleValue()
        Boolean.class   || false
        String.class    || ""
    }
}
