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
import pl.mk5.gdx.fireapp.GdxFIRAnalytics
import pl.mk5.gdx.fireapp.distributions.AnalyticsDistribution
import spock.lang.Specification

class GdxFIRAnalyticsTest extends Specification {

    def application = Mock(Application)
    def distribution = Mock(AnalyticsDistribution)

    void setup() {
        Gdx.app = application
    }

    def "should call platform object"() {
        given:
        def gdxFIRAnalytics = GdxFIRAnalytics.instance()
        gdxFIRAnalytics.platformObject = distribution

        when:
        gdxFIRAnalytics.logEvent(_ as String, _ as Map)
        gdxFIRAnalytics.setScreen(_ as String, String.class)
        gdxFIRAnalytics.setUserProperty(_ as String, _ as String)
        gdxFIRAnalytics.setUserId(_ as String)

        then:
        1 * distribution.logEvent(_ as String, _ as Map)
        1 * distribution.setScreen(_ as String, String.class)
        1 * distribution.setUserProperty(_ as String, _ as String)
        1 * distribution.setUserId(_ as String)
    }
}
