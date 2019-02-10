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

package pl.mk5.gdx.fireapp.html.storage;

import com.google.gwt.typedarrays.shared.ArrayBuffer;
import com.google.gwt.typedarrays.shared.TypedArrays;
import com.google.gwt.typedarrays.shared.Uint8Array;
import com.google.gwt.xhr.client.ReadyStateChangeHandler;
import com.google.gwt.xhr.client.XMLHttpRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import pl.mk5.gdx.fireapp.promises.FuturePromise;


@PrepareForTest({XMLHttpRequest.class, TypedArrays.class})
public class UrlDownloaderTest {

    private XMLHttpRequest xmlHttpRequest;

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(XMLHttpRequest.class);
        PowerMockito.mockStatic(TypedArrays.class);
        xmlHttpRequest = PowerMockito.mock(XMLHttpRequest.class);
        Mockito.when(XMLHttpRequest.create()).thenReturn(xmlHttpRequest);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) {
                ((ReadyStateChangeHandler) invocation.getArgument(0)).onReadyStateChange(xmlHttpRequest);
                return null;
            }
        }).when(xmlHttpRequest).setOnReadyStateChange(Mockito.any(ReadyStateChangeHandler.class));
        Uint8Array uint8Array = Mockito.mock(Uint8Array.class);
        Mockito.when(xmlHttpRequest.getResponseArrayBuffer()).thenReturn(Mockito.mock(ArrayBuffer.class));
        Mockito.when(uint8Array.length()).thenReturn(10);
        Mockito.when(TypedArrays.createUint8Array(Mockito.any(ArrayBuffer.class))).thenReturn(uint8Array);
    }

    @Test
    public void onSuccess() {
        // Given
        String downloadUrl = "http://url.with.token.com?token=abc";
        FuturePromise futurePromise = Mockito.spy(FuturePromise.class);
        UrlDownloader urlDownloader = new UrlDownloader(futurePromise);
        Mockito.when(xmlHttpRequest.getReadyState()).thenReturn(XMLHttpRequest.DONE);
        Mockito.when(xmlHttpRequest.getStatus()).thenReturn(200);

        // When
        urlDownloader.onSuccess(downloadUrl);

        // Then
        Mockito.verify(xmlHttpRequest, VerificationModeFactory.times(1)).open(Mockito.eq("GET"), Mockito.eq(downloadUrl));
        Mockito.verify(xmlHttpRequest, VerificationModeFactory.times(1)).setRequestHeader(Mockito.eq("Authorization"), Mockito.eq("Bearer abc"));
        Mockito.verify(xmlHttpRequest, VerificationModeFactory.times(1)).send();
        Mockito.verify(futurePromise, VerificationModeFactory.times(1)).doComplete(Mockito.any(byte[].class));
    }

    @Test
    public void onSuccess_fail() {
        // Given
        String downloadUrl = "http://url.with.token.com?token=abc";
        FuturePromise futurePromise = Mockito.spy(FuturePromise.class);
        futurePromise.silentFail();
        UrlDownloader urlDownloader = new UrlDownloader(futurePromise);
        Mockito.when(xmlHttpRequest.getReadyState()).thenReturn(XMLHttpRequest.DONE);
        Mockito.when(xmlHttpRequest.getStatus()).thenReturn(501);

        // When
        urlDownloader.onSuccess(downloadUrl);

        // Then
        Mockito.verify(futurePromise, VerificationModeFactory.times(1)).doFail(Mockito.any(Exception.class));
    }

    @Test(expected = IllegalStateException.class)
    public void onSuccess_wrongUrl() {
        // Given
        String downloadUrl = "http://url.without.token.com";
        FuturePromise futurePromise = Mockito.spy(FuturePromise.class);
        UrlDownloader urlDownloader = new UrlDownloader(futurePromise);

        // When
        urlDownloader.onSuccess(downloadUrl);

        // Then
        Assert.fail();
    }

    @Test
    public void onFail() {
        // Given
        FuturePromise futurePromise = Mockito.spy(FuturePromise.class);
        futurePromise.silentFail();
        UrlDownloader urlDownloader = new UrlDownloader(futurePromise);
        Exception exception = Mockito.mock(Exception.class);

        // When
        urlDownloader.onFail(exception);

        // Then
        Mockito.verify(futurePromise, VerificationModeFactory.times(1)).doFail(Mockito.refEq(exception));
    }
}