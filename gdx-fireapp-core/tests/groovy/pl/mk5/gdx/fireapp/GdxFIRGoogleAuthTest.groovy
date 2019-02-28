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
import pl.mk5.gdx.fireapp.GdxFIRAuth
import pl.mk5.gdx.fireapp.distributions.GoogleAuthDistribution
import spock.lang.Specification

class GdxFIRGoogleAuthTest extends Specification {

    def application = Mock(Application)
    def distribution = Mock(GoogleAuthDistribution)

    void setup() {
        Gdx.app = application
    }

    def "should call platform object"() {
        given:
        def gdxFIRGoogleAuth = GdxFIRAuth.instance().google()
        gdxFIRGoogleAuth.platformObject = distribution

        when:
        gdxFIRGoogleAuth.signOut()
        gdxFIRGoogleAuth.revokeAccess()
        gdxFIRGoogleAuth.signIn()

        then:
        1 * distribution.signOut()
        1 * distribution.revokeAccess()
        1 * distribution.signIn()
    }
}
