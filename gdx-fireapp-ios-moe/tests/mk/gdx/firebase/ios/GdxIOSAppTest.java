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
package mk.gdx.firebase.ios;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;

@RunWith(PowerMockRunner.class)
@PrepareForTest({NatJ.class})
public abstract class GdxIOSAppTest {

//    @Rule
//    public PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setup() {
        PowerMockito.mockStatic(NatJ.class);
        IOSApplication application = Mockito.mock(IOSApplication.class);
        Mockito.when(application.getType()).thenReturn(Application.ApplicationType.iOS);
        Gdx.app = application;
    }

}
