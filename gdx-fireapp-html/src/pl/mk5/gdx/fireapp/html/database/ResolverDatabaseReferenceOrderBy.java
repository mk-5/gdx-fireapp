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

import pl.mk5.gdx.fireapp.database.OrderByClause;
import pl.mk5.gdx.fireapp.database.OrderByResolver;

/**
 * Resolves order-by for javascript firebase db.
 */
class ResolverDatabaseReferenceOrderBy implements OrderByResolver<DatabaseReference, DatabaseReference> {

    private final static String MISSING_ARGUMENT = "Order by argument is missing";

    @Override
    public DatabaseReference resolve(OrderByClause orderByClause, DatabaseReference target) {
        switch (orderByClause.getOrderByMode()) {
            case ORDER_BY_CHILD:
                if (orderByClause.getArgument() == null) {
                    throw new IllegalArgumentException(MISSING_ARGUMENT);
                }
                target = target.orderByChild(orderByClause.getArgument());
                break;
            case ORDER_BY_KEY:
                target = target.orderByKey();
                break;
            case ORDER_BY_VALUE:
                target = target.orderByValue();
                break;
        }
        return target;
    }
}
