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

package mk.gdx.firebase.html.database;

import com.badlogic.gdx.utils.Array;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.powermock.reflect.Whitebox;

import mk.gdx.firebase.database.DataListenersManager;
import mk.gdx.firebase.listeners.DataChangeListener;

public class GwtDataListenersManagerTest {

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    public void addDataListener_detach() {
        // Given
        DataListenersManager<DataChangeListener> listenersManager = Mockito.mock(DataListenersManager.class);
        Mockito.when(listenersManager.hasListeners(Mockito.anyString())).thenReturn(true);
        Whitebox.setInternalState(GwtDataListenersManager.class, "listenersManager", listenersManager);
        String arg = "/test";

        // When
        GwtDataListenersManager.addDataListener(arg, null);

        // Then
        Mockito.verify(listenersManager, VerificationModeFactory.times(1)).hasListeners(Mockito.eq(arg));
        Mockito.verify(listenersManager, VerificationModeFactory.times(1)).removeListenersForPath(Mockito.eq(arg));
    }

    @Test
    public void addDataListener() {
        // Given
        DataListenersManager<DataChangeListener> listenersManager = Mockito.mock(DataListenersManager.class);
        Mockito.when(listenersManager.hasListeners(Mockito.anyString())).thenReturn(false);
        Whitebox.setInternalState(GwtDataListenersManager.class, "listenersManager", listenersManager);
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        String arg = "/test";

        // When
        GwtDataListenersManager.addDataListener(arg, listener);

        // Then
        Mockito.verify(listenersManager, VerificationModeFactory.times(1)).hasListeners(Mockito.eq(arg));
        Mockito.verify(listenersManager, VerificationModeFactory.times(1)).addNewListener(Mockito.eq(arg), Mockito.refEq(listener));
    }

    @Test
    public void callListener_withoutListener() {
        // Given
        DataListenersManager<DataChangeListener> listenersManager = Mockito.mock(DataListenersManager.class);
        Mockito.when(listenersManager.hasListeners(Mockito.anyString())).thenReturn(false);
        Whitebox.setInternalState(GwtDataListenersManager.class, "listenersManager", listenersManager);

        // When
        GwtDataListenersManager.callListener("/not-exists", "test");

        // Then
        Mockito.verify(listenersManager, VerificationModeFactory.times(0)).getListeners(Mockito.anyString());
    }

    @Test
    public void callListener() {
        // Given
        DataListenersManager<DataChangeListener> listenersManager = Mockito.mock(DataListenersManager.class);
        Array<DataChangeListener> listeners = new Array<>();
        DataChangeListener listener = Mockito.mock(DataChangeListener.class);
        listeners.add(listener);
        Mockito.when(listenersManager.hasListeners(Mockito.anyString())).thenReturn(true);
        Mockito.when(listenersManager.getListeners(Mockito.anyString())).thenReturn(listeners);
        Whitebox.setInternalState(GwtDataListenersManager.class, "listenersManager", listenersManager);

        // When
        GwtDataListenersManager.callListener("/exists", "test");

        // Then
        Mockito.verify(listener, VerificationModeFactory.times(1)).onChange(Mockito.anyString());
    }

    @Test
    public void removeDataListener() {
        // Given
        DataListenersManager<DataChangeListener> listenersManager = Mockito.mock(DataListenersManager.class);
        Mockito.when(listenersManager.hasListeners(Mockito.anyString())).thenReturn(true);
        Whitebox.setInternalState(GwtDataListenersManager.class, "listenersManager", listenersManager);
        String arg = "/test";

        // When
        GwtDataListenersManager.removeDataListener(arg);

        // Then
        Mockito.verify(listenersManager, VerificationModeFactory.times(1)).removeListenersForPath(Mockito.eq(arg));
    }
}