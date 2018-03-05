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

package mk.gdx.firebase.android.database;

import com.google.firebase.database.Query;

import mk.gdx.firebase.database.FilterProvider;
import mk.gdx.firebase.database.FilterType;

/**
 * Provides filtering for {@code DatabaseReference} instance.
 */
public class QueryFilterProvider implements FilterProvider<Query, Query>
{

    public static final String WRONG_ARGUMENT_TYPE = "Wrong argument type. Available type is: Integer.";
    public static final String WRONG_ARGUMENT_TYPE2 = "Wrong argument type. Available types are: String, Boolean, Double.";
    public static final String MISSING_FILTER_ARGUMENTS = "Missing filter arguments.";

    /**
     * Apply filtering to the database ref and return appropriate query instance.
     *
     * @param filterType      Filter type which will be applied
     * @param target          Target DatabaseReference instance
     * @param filterArguments Arguments for filtering for ex. 100 or some String key
     * @param <V>             Type for filterArgument, one from following: Integer, String, Double, Boolean
     * @return Query with filtering applied
     */
    @Override
    public <V> Query apply(FilterType filterType, Query target, V[] filterArguments)
    {
        if (filterArguments.length == 0)
            throw new IllegalArgumentException(MISSING_FILTER_ARGUMENTS);
        switch (filterType) {
            case LIMIT_FIRST:
                if (!(filterArguments[0] instanceof Integer))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                return target.limitToFirst((Integer) filterArguments[0]);
            case LIMIT_LAST:
                if (!(filterArguments[0] instanceof Integer))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                return target.limitToLast((Integer) filterArguments[0]);
            case START_AT:
                // TODO - 2 arguments call
                if (filterArguments[0] instanceof Double) {
                    return target.startAt((Double) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.startAt((Boolean) filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.startAt((String) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            case END_AT:
                if (filterArguments[0] instanceof Double) {
                    return target.endAt((Double) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.endAt((Boolean) filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.endAt((String) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            case EQUAL_TO:
                if (filterArguments[0] instanceof Double) {
                    return target.equalTo((Double) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.equalTo((Boolean) filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.equalTo((String) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            default:
                throw new IllegalStateException();
        }
    }
}
