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
package pl.mk5.gdx.fireapp.html;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class})
@SuppressStaticInitializationFor({"com.google.gwt.core.client.ScriptInjector"})
public abstract class GdxHtmlAppTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    protected FileHandle configFileMock;

    @Before
    public void setup() throws Exception {
        Application application = Mockito.mock(Application.class);
        Mockito.when(application.getType()).thenReturn(Application.ApplicationType.WebGL);
        configFileMock = Mockito.mock(FileHandle.class);
        Mockito.when(configFileMock.exists()).thenReturn(true);
        Mockito.when(configFileMock.readString()).thenReturn("<script src=\"https://www.gstatic.com/firebasejs/123/firebase.js\"></script>\n" +
                "<script>\n" +
                "  // Initialize Firebase\n" +
                "  var config = {\n" +
                "    apiKey: \"abc\",\n" +
                "    authDomain: \"abc\",\n" +
                "    databaseURL: \"abc\",\n" +
                "    projectId: \"abc\",\n" +
                "    storageBucket: \"abc\",\n" +
                "    messagingSenderId: \"123\"\n" +
                "  };\n" +
                "  firebase.initializeApp(config);\n" +
                "</script>");

        Files files = Mockito.mock(Files.class);
        Mockito.when(files.internal(Mockito.eq("firebase-config.html"))).thenReturn(configFileMock);

        PowerMockito.mockStatic(ScriptInjector.class);
        Mockito.when(ScriptInjector.fromUrl(Mockito.anyString())).thenCallRealMethod();

        PowerMockito.mockStatic(ClassReflection.class);
        Mockito.when(ClassReflection.forName(Mockito.anyString())).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Class.forName((String) invocation.getArgument(0));
            }
        });
        Mockito.when(ClassReflection.getConstructor(Mockito.any(Class.class))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                Constructor constructor = PowerMockito.mock(Constructor.class);
                Mockito.when(constructor.newInstance()).thenReturn(((Class) invocation.getArgument(0)).getConstructors()[0].newInstance());
                return constructor;
            }
        });

        Gdx.app = application;
        Gdx.files = files;
    }
}
