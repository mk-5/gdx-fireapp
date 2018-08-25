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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class GdxFIRLoggerTest extends GdxAppTest {

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Before
    @Override
    public void setup() {
        super.setup();
        GdxFIRLogger.setEnabled(true);
    }

    @Test
    public void log() {
        GdxFIRLogger.log("test");
        Assert.assertEquals(GdxFIRLogger.getLogTag() + ": test", systemOutRule.getLog().trim());
    }

    @Test
    public void log1() {
        GdxFIRLogger.log("test", new Exception("test message"));
        Assert.assertTrue(systemOutRule.getLog().trim().contains(GdxFIRLogger.getLogTag() + ": test"));
        Assert.assertTrue(systemOutRule.getLog().trim().contains("java.lang.Exception: test message"));
    }

    @Test
    public void error() {
        GdxFIRLogger.error("test");
        Assert.assertEquals(GdxFIRLogger.getLogTag() + ": test", systemErrRule.getLog().trim());
    }

    @Test
    public void error1() {
        GdxFIRLogger.error("test", new Exception("test message"));
        Assert.assertTrue(systemErrRule.getLog().trim().contains(GdxFIRLogger.getLogTag() + ": test"));
        Assert.assertTrue(systemErrRule.getLog().trim().contains("java.lang.Exception: test message"));
    }

    @Test
    public void getLogTag() {
        Assert.assertEquals(GdxFIRLogger.getLogTag(), "GdxFireapp Api");
    }

    @Test
    public void isEnabled() {
        GdxFIRLogger.setEnabled(false);
        Assert.assertEquals(GdxFIRLogger.isEnabled(), false);
    }

    @Test
    public void setEnabled() {
        GdxFIRLogger.setEnabled(false);
        GdxFIRLogger.log("test");

        Assert.assertEquals(GdxFIRLogger.isEnabled(), false);
        Assert.assertTrue(systemOutRule.getLog().isEmpty());

        GdxFIRLogger.setEnabled(true);
        GdxFIRLogger.log("test");
        Assert.assertTrue(!systemOutRule.getLog().isEmpty());
    }
}