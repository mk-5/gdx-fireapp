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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;

import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;

@PrepareForTest({DatabaseReference.class})
public class QueryPushTest {

    @Rule
    public final PowerMockRule powerMockRule = new PowerMockRule();

    @Before
    public void setUp() {
        PowerMockito.mockStatic(DatabaseReference.class);
    }

    @Test(expected = UnsatisfiedLinkError.class)
    public void runJS() {
        // Given
        QueryPush query = new QueryPush(Mockito.mock(Database.class));

        // When
        query.runJS();

        // Then
        Assert.fail("Native method should be run");
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        QueryPush query = new QueryPush(Mockito.mock(Database.class));

        // When
        ArgumentsValidator argumentsValidator = query.createArgumentsValidator();

        // Then
        Assert.assertNull(argumentsValidator);
    }
}