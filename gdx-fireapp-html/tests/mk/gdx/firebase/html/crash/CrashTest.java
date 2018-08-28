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

package mk.gdx.firebase.html.crash;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Constructor;
import com.google.gwt.core.client.ScriptInjector;

import org.junit.Assert;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;

import mk.gdx.firebase.GdxFIRCrash;
import mk.gdx.firebase.html.GdxHtmlAppTest;

@PrepareForTest({ClassReflection.class, Constructor.class, ScriptInjector.class, Crash.class})
public class CrashTest extends GdxHtmlAppTest {

    @Test
    public void log() {
        GdxFIRCrash.instance().log("test");
        Assert.assertTrue(true);
    }

    @Test
    public void initialize() {
        GdxFIRCrash.instance().initialize();
        Assert.assertTrue(true);
    }
}