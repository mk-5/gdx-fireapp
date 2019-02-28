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

package pl.mk5.gdx.fireapp

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import pl.mk5.gdx.fireapp.GdxFIRLogger
import spock.lang.Specification

class GdxFIRLoggerTest extends Specification {

    def application = Mock(Application)

    void setup() {
        Gdx.app = application
    }

    def "should log when logger is enabled and shouldn't when disabled"() {
        when:
        GdxFIRLogger.setEnabled(enabled)
        GdxFIRLogger.log(msg1)
        GdxFIRLogger.log(msg1, throwable1)
        GdxFIRLogger.error(msg2)
        GdxFIRLogger.error(msg2, throwable2)

        then:
        (enabled ? 1 : 0) * application.log(_ as String, msg1)
        (enabled ? 1 : 0) * application.log(_ as String, msg1, throwable1)
        (enabled ? 1 : 0) * application.error(_ as String, msg2)
        (enabled ? 1 : 0) * application.error(_ as String, msg2, throwable2)

        where:
        enabled | msg1    | msg2      | throwable1                     | throwable2
        true    | "xxx"   | "yyy"     | new IllegalArgumentException() | new FileNotFoundException()
        true    | "cxzxc" | "cvbcvbr" | new IOException()              | new IllegalStateException()
        false   | "xxx"   | "yyy"     | new IllegalArgumentException() | new FileNotFoundException()
        false   | "cxzxc" | "cvbcvbr" | new IOException()              | new IllegalStateException()
    }

}
