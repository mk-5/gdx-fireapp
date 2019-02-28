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

package pl.mk5.gdx.fireapp.database.queries;

import com.badlogic.gdx.utils.Array;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import pl.mk5.gdx.fireapp.database.Filter;
import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.validators.ArgumentsValidator;
import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;


public class GdxFireappQueryTest {

    @Test
    public void withArgs() {
        // Given
        GdxFireappQuery query = new GdxFireappQuery(Mockito.mock(DatabaseDistribution.class), "/test") {
            @Override
            protected ArgumentsValidator createArgumentsValidator() {
                return null;
            }

            @Override
            protected void applyFilters() {

            }

            @Override
            protected Object run() {
                return null;
            }

            @Override
            protected void terminate() {

            }
        };

        // When
        query.withArgs("test");

        // Then
        Assert.assertTrue(query.arguments.contains("test", false));
    }

    @Test
    public void with() {
        // Given
        GdxFireappQuery query = new GdxFireappQuery(Mockito.mock(DatabaseDistribution.class), "/test") {
            @Override
            protected ArgumentsValidator createArgumentsValidator() {
                return null;
            }

            @Override
            protected void applyFilters() {

            }

            @Override
            protected Object run() {
                return null;
            }

            @Override
            protected void terminate() {

            }
        };
        Filter filter = new Filter();
        Array<Filter> filterArray = new Array<>();
        filterArray.add(filter);

        // When
        query.with(filterArray);

        // Then
        Assert.assertTrue(query.filters.contains(filter, true));
    }

    @Test
    public void with1() {
        // Given
        GdxFireappQuery query = new GdxFireappQuery(Mockito.mock(DatabaseDistribution.class), "/test") {
            @Override
            protected ArgumentsValidator createArgumentsValidator() {
                return null;
            }

            @Override
            protected void applyFilters() {

            }

            @Override
            protected Object run() {
                return null;
            }

            @Override
            protected void terminate() {

            }
        };
        OrderByClause orderByClause = new OrderByClause();

        // When
        query.with(orderByClause);

        // Then
        Assert.assertEquals(orderByClause, query.orderByClause);
    }

    @Test
    public void execute() {
        // Given
        final ArgumentsValidator validator = Mockito.mock(ArgumentsValidator.class);
        final int[] counter = {0};
        final String result = "test";
        GdxFireappQuery query = new GdxFireappQuery(Mockito.mock(DatabaseDistribution.class), "/test") {
            @Override
            protected ArgumentsValidator createArgumentsValidator() {
                System.out.println("ARGUMENTS VALIDATOR");
                return validator;
            }

            @Override
            protected void applyFilters() {
                counter[0]++;
            }

            @Override
            protected Object run() {
                counter[0]++;
                return result;
            }

            @Override
            protected void terminate() {
                counter[0]++;
            }
        };

        // When
        Object queryResult = query.execute();

        // Then
        Mockito.verify(validator, VerificationModeFactory.times(1)).validate(Mockito.any(Array.class));
        Assert.assertEquals(3, counter[0]);
        Assert.assertEquals(0, query.filters.size);
        Assert.assertEquals(0, query.arguments.size);
        Assert.assertEquals(result, queryResult);
    }

    @Test
    public void createArgumentsValidator() {
        // Given
        final ArgumentsValidator validator = Mockito.mock(ArgumentsValidator.class);
        GdxFireappQuery query = new GdxFireappQuery(Mockito.mock(DatabaseDistribution.class), "/test") {
            @Override
            protected ArgumentsValidator createArgumentsValidator() {
                return validator;
            }

            @Override
            protected void applyFilters() {

            }

            @Override
            protected Object run() {
                return null;
            }

            @Override
            protected void terminate() {

            }
        };
        // When
        // Then
        Assert.assertNotNull(query.argumentsValidator);
        Assert.assertEquals(validator, query.argumentsValidator);
    }
}