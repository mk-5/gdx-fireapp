/*
 * Copyright 2017 mk
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

package pl.mk5.gdx.fireapp.database;


/**
 * Applies OrderByClause to the target object instance.
 *
 * @param <T> The type of object to which order-by is applied
 * @param <R> The return type after order-by
 */
public interface OrderByResolver<T, R> {

    /**
     * Resolves given OrderByClause.
     *
     * @param orderByClause Order by clause, may be null
     * @param target        Target object, for ex. query representation, not null
     * @return Object with order-by applied
     */
    R resolve(OrderByClause orderByClause, T target);
}
