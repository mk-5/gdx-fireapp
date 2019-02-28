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

package pl.mk5.gdx.fireapp.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.annotation.Annotation;

public class AnnotationFinderTest {

    @Test
    public void getMethodAnnotation() {
        // Given
        TestCaseClass testCaseClass = new TestCaseClass();

        // When
        Annotation annotation = AnnotationFinder.getMethodAnnotation(Deprecated.class, testCaseClass);
        Annotation annotation2 = AnnotationFinder.getMethodAnnotation(Mock.class, testCaseClass);

        // Then
        Assert.assertNotNull(annotation);
        Assert.assertNull(annotation2);
    }

    private class TestCaseClass {

        @Deprecated
        public void someMethod() {

        }
    }
}