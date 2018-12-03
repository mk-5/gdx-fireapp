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

package mk.gdx.firebase.ios.database;

import org.junit.Test;
import org.mockito.Mockito;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import apple.foundation.NSNumber;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, NSNumber.class})
public class NSNumberHelperTest extends GdxIOSAppTest {

    private NSNumber nsNumber;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(NSNumber.class);
        nsNumber = Mockito.mock(NSNumber.class);
    }

    @Test
    public void getNSNumberPrimitive_boolean() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("c");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).boolValue();
    }

    @Test
    public void getNSNumberPrimitive_float() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("f");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).floatValue();
    }

    @Test
    public void getNSNumberPrimitive_integer() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("i");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).integerValue();
    }

    @Test
    public void getNSNumberPrimitive_default() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("xxx");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).integerValue();
    }

    @Test
    public void getNSNumberPrimitive_double() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("d");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).doubleValue();
    }

    @Test
    public void getNSNumberPrimitive_long() {
        // Given
        Mockito.when(nsNumber.objCType()).thenReturn("q");

        // When
        Object result = NSNumberHelper.getNSNumberPrimitive(nsNumber);

        // Then
        Mockito.verify(nsNumber).longValue();
    }
}