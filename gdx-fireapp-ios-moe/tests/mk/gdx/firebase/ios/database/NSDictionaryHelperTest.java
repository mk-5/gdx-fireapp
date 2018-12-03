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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import apple.foundation.NSDictionary;
import apple.foundation.NSMutableDictionary;
import apple.foundation.NSString;
import mk.gdx.firebase.ios.GdxIOSAppTest;

@PrepareForTest({
        NatJ.class, NSDictionary.class, NSMutableDictionary.class,
        DataProcessor.class, NSString.class
})
public class NSDictionaryHelperTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(NSDictionary.class);
        PowerMockito.mockStatic(NSMutableDictionary.class);
        PowerMockito.mockStatic(DataProcessor.class);
        PowerMockito.mockStatic(NSString.class);
        Mockito.when(NSString.alloc()).thenReturn(Mockito.mock(NSString.class));
    }

    @Test
    public void toMap() {
        // Given
        NSDictionary nsDictionary = Mockito.mock(NSDictionary.class);
        Set set = Mockito.spy(new HashSet());
        Mockito.when(nsDictionary.keySet()).thenReturn(set);

        // When
        Object result = NSDictionaryHelper.toMap(nsDictionary);

        // Then
        Assert.assertNotNull(result);
        Mockito.verify(nsDictionary.keySet(), VerificationModeFactory.times(1));
    }

    @Test
    public void toNSDictionary() {
        // Given
        NSMutableDictionary mutableDictionary = Mockito.mock(NSMutableDictionary.class);
        Mockito.when(NSMutableDictionary.alloc()).thenReturn(mutableDictionary);
        Mockito.when(mutableDictionary.init()).thenReturn(mutableDictionary);
        Map map = Mockito.spy(new HashMap());
        map.put("test", "test");
        map.put("test2", "test");
        map.put("test3", "test");

        // When
        Object result = NSDictionaryHelper.toNSDictionary(map);

        // Then
        Assert.assertNotNull(result);
        Mockito.verify(mutableDictionary, VerificationModeFactory.times(3)).put(Mockito.any(), Mockito.any());
    }

    @Test
    public void toNSDictionary1() {
        // Given
        NSMutableDictionary mutableDictionary = Mockito.mock(NSMutableDictionary.class);
        Mockito.when(NSMutableDictionary.alloc()).thenReturn(mutableDictionary);
        Mockito.when(mutableDictionary.init()).thenReturn(mutableDictionary);
        TestClass testObject = new TestClass();
        testObject.name = "bob";
        testObject.name2 = "fred";

        // When
        Object result = NSDictionaryHelper.toNSDictionary(testObject);

        // Then
        Assert.assertNotNull(result);
        Mockito.verify(mutableDictionary, VerificationModeFactory.times(2)).put(Mockito.any(), Mockito.any());
    }

    private static class TestClass {
        public String name;
        public String name2;
    }
}