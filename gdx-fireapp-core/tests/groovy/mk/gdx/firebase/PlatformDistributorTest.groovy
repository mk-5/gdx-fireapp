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
import spock.lang.Specification

class PlatformDistributorTest extends Specification {

    def application = null as Application

    void setup() {
        application = Mock(Application)
        Gdx.app = application
    }

    def "should create android platform"() {
        given:
        application.type >> Application.ApplicationType.Android

        when:
        def platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null
            }

            @Override
            protected String getAndroidClassName() {
                return PlatformDistributorTest.getName()
            }

            @Override
            protected String getWebGLClassName() {
                return null
            }
        }

        then:
        PlatformDistributorTest == platformDistributor.platformObject.class
    }

    def "should create ios platform"() {
        given:
        application.type >> Application.ApplicationType.iOS

        when:
        def platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return PlatformDistributorTest.getName()
            }

            @Override
            protected String getAndroidClassName() {
                return null
            }

            @Override
            protected String getWebGLClassName() {
                return null
            }
        }

        then:
        PlatformDistributorTest == platformDistributor.platformObject.class
    }

    def "should create webgl platform"() {
        given:
        application.type >> Application.ApplicationType.WebGL

        when:
        def platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null
            }

            @Override
            protected String getAndroidClassName() {
                return null
            }

            @Override
            protected String getWebGLClassName() {
                return PlatformDistributorTest.getName()
            }
        }

        then:
        PlatformDistributorTest == platformDistributor.platformObject.class
    }
}
