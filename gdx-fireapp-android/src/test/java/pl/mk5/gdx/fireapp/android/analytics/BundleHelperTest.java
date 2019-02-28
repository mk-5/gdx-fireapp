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

package pl.mk5.gdx.fireapp.android.analytics;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE)
public class BundleHelperTest {

    @Test
    public void putNumberParam() {
        // Given
        Bundle bundle = new Bundle();
        String intKey = "int";
        String intValue = "3";
        String longKey = "long";
        String longValue = "8L";
        String longKey2 = "long2";
        String longValue2 = "" + Long.MAX_VALUE;
        String doubleKey = "double";
        String doubleValue = "4.0";
        String floatKey = "float";
        String floatValue = "2f";

        // When
        BundleHelper.putNumberParam(bundle, intKey, intValue);
        BundleHelper.putNumberParam(bundle, longKey, longValue);
        BundleHelper.putNumberParam(bundle, longKey2, longValue2);
        BundleHelper.putNumberParam(bundle, floatKey, floatValue);
        BundleHelper.putNumberParam(bundle, doubleKey, doubleValue);


        // Then
        Assert.assertTrue("Integer value should be 3, not " + bundle.getInt(intKey), bundle.getInt(intKey) == 3);
        Assert.assertTrue("Long value should be 8L, not " + bundle.getLong(longKey), bundle.getLong(longKey) == 8L);
        Assert.assertTrue("Long value should be " + Long.MAX_VALUE + ", not " + bundle.getLong(longKey2), bundle.getLong(longKey2) == Long.MAX_VALUE);
        Assert.assertTrue("Float value should be 2f, not " + bundle.getFloat(floatKey), bundle.getFloat(floatKey) == 2f);
        Assert.assertTrue("Double value should be 4.0, not " + bundle.getDouble(doubleKey), bundle.getDouble(doubleKey) == 4.0);
    }
}