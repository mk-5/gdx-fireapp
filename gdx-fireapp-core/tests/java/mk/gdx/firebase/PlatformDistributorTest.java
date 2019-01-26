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

package mk.gdx.firebase;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import mk.gdx.firebase.exceptions.PlatformDistributorException;

public class PlatformDistributorTest extends GdxAppTest {

    @Test
    public void setMockObject() {
        // Given
        PlatformDistributor platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null;
            }

            @Override
            protected String getAndroidClassName() {
                return null;
            }

            @Override
            protected String getWebGLClassName() {
                return null;
            }
        };
        Object mockObject = new Object();

        // When
        platformDistributor.setMockObject(mockObject);

        // Then
        Assert.assertEquals(mockObject, platformDistributor.platformObject);
    }

    @Test
    public void constructor_android() {
        // Given
        Mockito.when(Gdx.app.getType()).thenReturn(Application.ApplicationType.Android);

        // When
        PlatformDistributor platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null;
            }

            @Override
            protected String getAndroidClassName() {
                return "mk.gdx.firebase.PlatformDistributorTest";
            }

            @Override
            protected String getWebGLClassName() {
                return null;
            }
        };

        // Then
        Assert.assertTrue(platformDistributor.platformObject instanceof PlatformDistributorTest);
    }

    @Test
    public void constructor_ios() {
        // Given
        Mockito.when(Gdx.app.getType()).thenReturn(Application.ApplicationType.iOS);

        // When
        PlatformDistributor platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return "mk.gdx.firebase.PlatformDistributorTest";
            }

            @Override
            protected String getAndroidClassName() {
                return null;
            }

            @Override
            protected String getWebGLClassName() {
                return null;
            }
        };

        // Then
        Assert.assertTrue(platformDistributor.platformObject instanceof PlatformDistributorTest);
    }

    @Test
    public void constructor_gwt() {
        // Given
        Mockito.when(Gdx.app.getType()).thenReturn(Application.ApplicationType.WebGL);

        // When
        PlatformDistributor platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null;
            }

            @Override
            protected String getAndroidClassName() {
                return null;
            }

            @Override
            protected String getWebGLClassName() {
                return "mk.gdx.firebase.PlatformDistributorTest";
            }
        };

        // Then
        Assert.assertTrue(platformDistributor.platformObject instanceof PlatformDistributorTest);
    }

    @Test(expected = PlatformDistributorException.class)
    public void constructor_exception() {
        // Given
        Mockito.when(Gdx.app.getType()).thenReturn(Application.ApplicationType.Android);

        // When
        PlatformDistributor platformDistributor = new PlatformDistributor() {
            @Override
            protected String getIOSClassName() {
                return null;
            }

            @Override
            protected String getAndroidClassName() {
                return "abc.abc.NotExistingClass";
            }

            @Override
            protected String getWebGLClassName() {
                return null;
            }
        };

        // Then
        Assert.assertTrue(platformDistributor.platformObject instanceof PlatformDistributorTest);
    }
}