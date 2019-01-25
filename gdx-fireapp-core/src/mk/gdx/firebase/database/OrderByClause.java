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

package mk.gdx.firebase.database;

/**
 * Keeps information about order-by clause.
 */
public class OrderByClause {
    private OrderByMode orderByMode;
    private String argument;

    public OrderByClause() {

    }

    public OrderByClause(OrderByMode orderByMode, String argument) {
        this.orderByMode = orderByMode;
        this.argument = argument;
    }

    public OrderByMode getOrderByMode() {
        return orderByMode;
    }

    public void setOrderByMode(OrderByMode orderByMode) {
        this.orderByMode = orderByMode;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }
}
