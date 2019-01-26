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

package mk.gdx.firebase

import com.badlogic.gdx.Application
import com.badlogic.gdx.Gdx
import mk.gdx.firebase.database.FilterType
import mk.gdx.firebase.database.OrderByMode
import mk.gdx.firebase.distributions.DatabaseDistribution
import mk.gdx.firebase.functional.Function
import spock.lang.Specification

class GdxFIRDatabaseTest extends Specification {

    def application = Mock(Application)
    def distribution = Mock(DatabaseDistribution)

    void setup() {
        Gdx.app = application
    }

    def "should call platform object"() {
        given:
        def gdxFIRDatabase = GdxFIRDatabase.instance()
        gdxFIRDatabase.platformObject = distribution

        when:
        gdxFIRDatabase.filter(FilterType.END_AT, _ as Object[])
        gdxFIRDatabase.inReference(_ as String)
        gdxFIRDatabase.onConnect()
        gdxFIRDatabase.keepSynced(_ as Boolean)
        gdxFIRDatabase.push()
        gdxFIRDatabase.onDataChange(String.class)
        gdxFIRDatabase.readValue(String.class)
        gdxFIRDatabase.orderBy(OrderByMode.ORDER_BY_CHILD, _ as String)
        gdxFIRDatabase.removeValue()
        gdxFIRDatabase.transaction(String.class, _ as Function)
        gdxFIRDatabase.updateChildren(_ as Map)

        then:
        1 * distribution.filter(FilterType.END_AT, _ as Object[])
        1 * distribution.inReference(_ as String)
        1 * distribution.onConnect()
        1 * distribution.keepSynced(_ as Boolean)
        1 * distribution.push()
        1 * distribution.onDataChange(String.class)
        1 * distribution.readValue(String.class)
        1 * distribution.orderBy(OrderByMode.ORDER_BY_CHILD, _ as String)
        1 * distribution.removeValue()
        1 * distribution.transaction(String.class, _ as Function)
        1 * distribution.updateChildren(_ as Map)
    }
}
