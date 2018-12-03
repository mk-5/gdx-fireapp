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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Arrays;
import java.util.List;

import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSMutableArray;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({NatJ.class, NSArray.class, DataProcessor.class, NSMutableArray.class})
public class NSArrayHelperTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(NSArray.class);
        PowerMockito.mockStatic(NSMutableArray.class);
        PowerMockito.mockStatic(DataProcessor.class);
    }

    @Test
    public void toList() {
        // Given
        NSArray nsArray = Mockito.mock(NSArray.class);

        // When
        Object result = NSArrayHelper.toList(nsArray);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof List);
    }

    @Test
    public void toArray() {
        // Given
        List list = Arrays.asList("test", "test2", "test3");
        NSMutableArray mutableArray = Mockito.mock(NSMutableArray.class);
        Mockito.when(mutableArray.init()).thenReturn(mutableArray);
        Mockito.when(NSMutableArray.alloc()).thenReturn(mutableArray);


        // When
        Object result = NSArrayHelper.toArray(list);

        // Then
        Assert.assertNotNull(result);
        Assert.assertTrue(result instanceof List);
        Mockito.verify(mutableArray, VerificationModeFactory.times(3)).add(Mockito.nullable(NSObject.class));
    }
}