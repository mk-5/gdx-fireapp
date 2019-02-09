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
 * Represents ordering mode in firebase database.
 * <p>
 * Following filter types are dependent of order by mode:
 * - {@link FilterType#START_AT}
 * - {@link FilterType#END_AT}
 * - {@link FilterType#EQUAL_TO}
 */
public enum OrderByMode {
    ORDER_BY_CHILD,
    ORDER_BY_KEY,
    ORDER_BY_VALUE
}
