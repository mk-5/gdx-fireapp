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

package mk.gdx.firebase.database;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.reflect.ClassReflection;

import java.util.List;

/**
 * Ensures that filters will be applied in right context.
 */
public class FilteringStateEnsurer {
    private static final String FILTER_SHOULD_BE_APPLIED_FOR_LIST = "Filter should be applied only for the List type";

    private FilteringStateEnsurer() {

    }

    /**
     * Throws IllegalStateException when filter was applied but type of wanted data is not a list.
     *
     * @param wantedType The type of requested data
     */
    public static void checkFilteringState(Array<Filter> filters, OrderByClause orderByClause, Class<?> wantedType) {
        if ((filters.size > 0 || orderByClause != null) && !ClassReflection.isAssignableFrom(List.class, wantedType))
            throw new IllegalStateException(FILTER_SHOULD_BE_APPLIED_FOR_LIST);
    }
}
