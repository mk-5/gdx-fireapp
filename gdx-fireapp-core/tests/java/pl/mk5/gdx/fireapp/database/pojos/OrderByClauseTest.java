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

package pl.mk5.gdx.fireapp.database.pojos;

import org.junit.Assert;
import org.junit.Test;

import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.OrderByMode;

public class OrderByClauseTest {

    @Test
    public void setOrderByMode() {
        // Given
        OrderByClause orderByClause = new OrderByClause();

        // When
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_CHILD);

        // Then
        Assert.assertEquals(OrderByMode.ORDER_BY_CHILD, orderByClause.getOrderByMode());
    }

    @Test
    public void setOrderByMode2() {
        // Given
        OrderByClause orderByClause = new OrderByClause();

        // When
        orderByClause.setOrderByMode(OrderByMode.ORDER_BY_KEY);

        // Then
        Assert.assertEquals(OrderByMode.ORDER_BY_KEY, orderByClause.getOrderByMode());
    }

    @Test
    public void setArgument() {
        // Given
        OrderByClause orderByClause = new OrderByClause();

        // When
        orderByClause.setArgument("test");

        // Then
        Assert.assertEquals("test", orderByClause.getArgument());
    }

    @Test
    public void setArgument2() {
        // Given
        OrderByClause orderByClause = new OrderByClause();

        // When
        orderByClause.setArgument("test2");

        // Then
        Assert.assertEquals("test2", orderByClause.getArgument());
    }

    @Test
    public void constructor() {
        // Given
        // When
        OrderByClause orderByClause = new OrderByClause(OrderByMode.ORDER_BY_KEY, "test");

        // Then
        Assert.assertEquals(OrderByMode.ORDER_BY_KEY, orderByClause.getOrderByMode());
        Assert.assertEquals("test", orderByClause.getArgument());
    }
}