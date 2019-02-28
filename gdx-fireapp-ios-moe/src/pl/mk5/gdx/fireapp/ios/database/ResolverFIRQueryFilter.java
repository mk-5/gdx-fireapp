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

package pl.mk5.gdx.fireapp.ios.database;

import bindings.google.firebasedatabase.FIRDatabaseQuery;
import pl.mk5.gdx.fireapp.database.FilterResolver;
import pl.mk5.gdx.fireapp.database.FilterType;

/**
 * Provides filtering for {@code DatabaseReference} instance.
 */
class ResolverFIRQueryFilter implements FilterResolver<FIRDatabaseQuery, FIRDatabaseQuery> {

    private static final String WRONG_ARGUMENT_TYPE = "Wrong argument type. Available type is: Integer.";
    private static final String WRONG_ARGUMENT_TYPE2 = "Wrong argument type. Available types are: String, Boolean, Double.";
    public static final String MISSING_FILTER_ARGUMENTS = "Missing filter arguments.";


    @Override
    public <V> FIRDatabaseQuery resolve(FilterType filterType, FIRDatabaseQuery target, V... filterArguments) {
        if (filterArguments.length == 0)
            throw new IllegalArgumentException();
        switch (filterType) {
            case LIMIT_FIRST:
                if (!(filterArguments[0] instanceof Integer))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                return target.queryLimitedToFirst(((Integer) filterArguments[0]).longValue());
            case LIMIT_LAST:
                if (!(filterArguments[0] instanceof Integer))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                return target.queryLimitedToLast(((Integer) filterArguments[0]).longValue());
            case START_AT:
                if (filterArguments[0] instanceof Double) {
                    return target.queryStartingAtValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.queryStartingAtValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.queryStartingAtValue(filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            case END_AT:
                if (filterArguments[0] instanceof Double) {
                    return target.queryEndingAtValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.queryEndingAtValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.queryEndingAtValue(filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            case EQUAL_TO:
                if (filterArguments[0] instanceof Double) {
                    return target.queryEqualToValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    return target.queryEqualToValue(filterArguments[0]);
                } else if (filterArguments[0] instanceof String) {
                    return target.queryEqualToValue(filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
            default:
                throw new IllegalStateException();
        }
    }
}
