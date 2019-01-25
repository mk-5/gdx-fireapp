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

package mk.gdx.firebase.database.pojos;

import org.junit.Assert;
import org.junit.Test;

import mk.gdx.firebase.database.Filter;
import mk.gdx.firebase.database.FilterType;

public class FilterTest {

    @Test
    public void setFilterType() {
        // Given
        Filter filter = new Filter();

        // When
        filter.setFilterType(FilterType.EQUAL_TO);

        // Then
        Assert.assertEquals(FilterType.EQUAL_TO, filter.getFilterType());
    }

    @Test
    public void setFilterType2() {
        // Given
        Filter filter = new Filter();

        // When
        filter.setFilterType(FilterType.LIMIT_FIRST);

        // Then
        Assert.assertEquals(FilterType.LIMIT_FIRST, filter.getFilterType());
    }

    @Test
    public void setFilterArguments() {
        // Given
        Filter filter = new Filter();
        Object[] arguments = new Object[]{"test", 1, 3};

        // When
        filter.setFilterArguments(arguments);

        // Then
        Assert.assertArrayEquals(arguments, filter.getFilterArguments());
    }

    @Test
    public void getFilterType() {
        // Given
        Filter filter = new Filter();

        // When
        Object res = filter.getFilterType();

        // Then
        Assert.assertNull(res);
    }

    @Test
    public void getFilterArguments() {
        // Given
        Filter filter = new Filter();

        // When
        Object res = filter.getFilterArguments();

        // Then
        Assert.assertNull(res);
    }

    @Test
    public void constructor() {
        // Given
        // When
        Filter filter = new Filter(FilterType.LIMIT_FIRST, 2);

        // Then
        Assert.assertEquals(FilterType.LIMIT_FIRST, filter.getFilterType());
        Assert.assertEquals(2, filter.getFilterArguments()[0]);
    }
}