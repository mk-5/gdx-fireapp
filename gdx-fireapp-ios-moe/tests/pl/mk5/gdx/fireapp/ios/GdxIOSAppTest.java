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
package pl.mk5.gdx.fireapp.ios;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.utils.Timer;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import pl.mk5.gdx.fireapp.GdxFIRApp;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NatJ.class, Timer.class})
public abstract class GdxIOSAppTest {

//    @Rule
//    public PowerMockRule powerMockRule = new PowerMockRule();


    @BeforeClass
    public static void before() {
        GdxFIRApp.setAutoSubscribePromises(false);
    }

    @Before
    public void setup() {
        PowerMockito.mockStatic(NatJ.class);
        IOSApplication application = Mockito.mock(IOSApplication.class);
        Mockito.when(application.getType()).thenReturn(Application.ApplicationType.iOS);
        Gdx.app = application;
        GdxFIRApp.setThrowFailureByDefault(false);
    }

}
