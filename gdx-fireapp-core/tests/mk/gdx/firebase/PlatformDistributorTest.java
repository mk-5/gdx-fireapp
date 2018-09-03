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

import org.junit.Assert;
import org.junit.Test;

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
}