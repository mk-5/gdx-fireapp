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

package pl.mk5.gdx.fireapp.ios.analytics;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Collections;

import apple.foundation.NSDictionary;
import apple.foundation.NSMutableDictionary;
import apple.foundation.NSOperationQueue;
import apple.foundation.NSString;
import bindings.google.firebaseanalytics.FIRAnalytics;
import pl.mk5.gdx.fireapp.ios.GdxIOSAppTest;

@PrepareForTest({FIRAnalytics.class, NatJ.class, NSDictionary.class, NSMutableDictionary.class, NSString.class, NSOperationQueue.class})
public class AnalyticsTest extends GdxIOSAppTest {

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(FIRAnalytics.class);
    }

    @Test
    public void logEvent() {
        // Given
        PowerMockito.mockStatic(NSDictionary.class);
        PowerMockito.mockStatic(NSMutableDictionary.class);
        PowerMockito.mockStatic(NSString.class);
        NSMutableDictionary dictionary = PowerMockito.mock(NSMutableDictionary.class);
        NSString string = PowerMockito.mock(NSString.class);
        Mockito.when(NSMutableDictionary.alloc()).thenReturn(dictionary);
        Mockito.when(NSString.alloc()).thenReturn(string);
        Mockito.when(dictionary.init()).thenReturn(dictionary);
        Analytics analytics = new Analytics();

        // When
        analytics.logEvent("test", Collections.singletonMap("test_key", "test_value"));

        // Then
        PowerMockito.verifyStatic(FIRAnalytics.class, VerificationModeFactory.times(1));
        FIRAnalytics.logEventWithNameParameters(Mockito.eq("test"), Mockito.any(NSDictionary.class));
    }

    @Test
    public void setScreen() throws Exception {
        // Given
        PowerMockito.mockStatic(NSOperationQueue.class);
        NSOperationQueue nsOperationQueue = PowerMockito.mock(NSOperationQueue.class);
        PowerMockito.when(NSOperationQueue.class, "mainQueue").thenReturn(nsOperationQueue);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((NSOperationQueue.Block_addOperationWithBlock) invocation.getArgument(0)).call_addOperationWithBlock();
                return null;
            }
        }).when(nsOperationQueue).addOperationWithBlock(Mockito.any(NSOperationQueue.Block_addOperationWithBlock.class));

        Analytics analytics = new Analytics();

        // When
        analytics.setScreen("test", AnalyticsTest.class);

        // Then
        PowerMockito.verifyStatic(FIRAnalytics.class, VerificationModeFactory.times(1));
        FIRAnalytics.setScreenNameScreenClass(Mockito.eq("test"), Mockito.eq(AnalyticsTest.class.getSimpleName()));
    }

    @Test
    public void setUserProperty() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserProperty("test", "test_value");

        // Then
        PowerMockito.verifyStatic(FIRAnalytics.class, VerificationModeFactory.times(1));
        FIRAnalytics.setUserPropertyStringForName(Mockito.eq("test_value"), Mockito.eq("test"));
    }

    @Test
    public void setUserId() {
        // Given
        Analytics analytics = new Analytics();

        // When
        analytics.setUserId("test");

        // Then
        PowerMockito.verifyStatic(FIRAnalytics.class, VerificationModeFactory.times(1));
        FIRAnalytics.setUserID(Mockito.eq("test"));
    }
}