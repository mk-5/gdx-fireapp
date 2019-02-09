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

package pl.mk5.gdx.fireapp.html.database;

import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Field;
import com.google.gwtmockito.GwtMockitoTestRunner;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

@RunWith(GwtMockitoTestRunner.class)
@PrepareForTest({ClassReflection.class})
public class StringGeneratorTest {

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Test
    public void dataToString_primitve() {
        // Given
        PowerMockito.mockStatic(ClassReflection.class);
        Mockito.when(ClassReflection.isPrimitive(Mockito.any(Class.class))).thenReturn(true);
        Object arg = 3;

        // When
        Object data = StringGenerator.dataToString(arg);

        // Then
        Assert.assertEquals(arg.toString(), data);
    }

    @Test
    public void dataToString() {
        // Given
        PowerMockito.mockStatic(ClassReflection.class);
        Mockito.when(ClassReflection.isPrimitive(Mockito.any(Class.class))).thenReturn(false);
        Mockito.when(ClassReflection.getDeclaredFields(Mockito.any(Class.class))).thenReturn(new Field[]{});
        Object arg = new TestClass();

        // When
        Object data = StringGenerator.dataToString(arg);

        // Then
        Assert.assertEquals("{}", data);
    }

    private class TestClass {
    }
}