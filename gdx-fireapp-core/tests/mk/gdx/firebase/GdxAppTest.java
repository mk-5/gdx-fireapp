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
import com.badlogic.gdx.ApplicationLogger;
import com.badlogic.gdx.Gdx;

import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public abstract class GdxAppTest {

    @Before
    public void setup() {
        Application application = Mockito.mock(Application.class);
        ApplicationLogger applicationLogger = Mockito.mock(ApplicationLogger.class);

        // Mock application logger
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println(invocation.getArgument(0) + ": " + invocation.getArgument(1));
                return null;
            }
        }).when(applicationLogger).log(Mockito.anyString(), Mockito.anyString());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.out.println(invocation.getArgument(0) + ": " + invocation.getArgument(1));
                ((Throwable) invocation.getArgument(2)).printStackTrace(System.out);
                return null;
            }
        }).when(applicationLogger).log(Mockito.anyString(), Mockito.anyString(), Mockito.any(Throwable.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.err.println(invocation.getArgument(0) + ": " + invocation.getArgument(1));
                return null;
            }
        }).when(applicationLogger).error(Mockito.anyString(), Mockito.anyString());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                System.err.println(invocation.getArgument(0) + ": " + invocation.getArgument(1));
                ((Throwable) invocation.getArgument(2)).printStackTrace(System.err);
                return null;
            }
        }).when(applicationLogger).error(Mockito.anyString(), Mockito.anyString(), Mockito.any(Throwable.class));

        // Mock application
//        Mockito.when(application.getType()).thenReturn(Application.ApplicationType.Desktop);
        Mockito.when(application.getApplicationLogger()).thenReturn(applicationLogger);
        Mockito.when(application.getApplicationLogger()).thenReturn(applicationLogger);
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Application) invocation.getMock()).getApplicationLogger().log((String) invocation.getArgument(0), (String) invocation.getArgument(1));
                return null;
            }
        }).when(application).log(Mockito.anyString(), Mockito.anyString());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Application) invocation.getMock()).getApplicationLogger().log((String) invocation.getArgument(0), (String) invocation.getArgument(1), (Throwable) invocation.getArgument(2));
                return null;
            }
        }).when(application).log(Mockito.anyString(), Mockito.anyString(), Mockito.any(Throwable.class));
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Application) invocation.getMock()).getApplicationLogger().error((String) invocation.getArgument(0), (String) invocation.getArgument(1));
                return null;
            }
        }).when(application).error(Mockito.anyString(), Mockito.anyString());
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                ((Application) invocation.getMock()).getApplicationLogger().error((String) invocation.getArgument(0), (String) invocation.getArgument(1), (Throwable) invocation.getArgument(2));
                return null;
            }
        }).when(application).error(Mockito.anyString(), Mockito.anyString(), Mockito.any(Throwable.class));


        Gdx.app = application;
    }
}
