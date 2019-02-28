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

import pl.mk5.gdx.fireapp.database.FilterResolver;
import pl.mk5.gdx.fireapp.database.FilterType;

/**
 * Resolves filtering for javascript firebase db.
 */
class ResolverDatabaseReferenceFilter implements FilterResolver<DatabaseReference, DatabaseReference> {

    private static final String WRONG_ARGUMENT_TYPE = "Wrong argument type. Available type is: Number";
    private static final String WRONG_ARGUMENT_TYPE2 = "Wrong argument type. Available types are: String, Boolean, Number";
    private static final String MISSING_FILTER_ARGUMENTS = "Missing filter arguments.";

    @Override
    @SafeVarargs
    public final <V> DatabaseReference resolve(FilterType filterType, DatabaseReference target, V... filterArguments) {
        if (filterArguments.length == 0) {
            throw new IllegalArgumentException(MISSING_FILTER_ARGUMENTS);
        }
        switch (filterType) {
            case LIMIT_FIRST:
                if (!(filterArguments[0] instanceof Number))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                target = target.limitToFirst(((Number) filterArguments[0]).doubleValue());
                break;
            case LIMIT_LAST:
                if (!(filterArguments[0] instanceof Number))
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE);
                target = target.limitToLast(((Number) filterArguments[0]).doubleValue());
                break;
            case START_AT:
                if (filterArguments[0] instanceof Number) {
                    target = target.startAt(((Number) filterArguments[0]).doubleValue());
                } else if (filterArguments[0] instanceof String) {
                    target = target.startAt((String) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    target = target.startAt((Boolean) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
                break;
            case END_AT:
                if (filterArguments[0] instanceof Number) {
                    target = target.endAt(((Number) filterArguments[0]).doubleValue());
                } else if (filterArguments[0] instanceof String) {
                    target = target.endAt((String) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    target = target.endAt((Boolean) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
                break;
            case EQUAL_TO:
                if (filterArguments[0] instanceof Number) {
                    target = target.equalTo(((Number) filterArguments[0]).doubleValue());
                } else if (filterArguments[0] instanceof String) {
                    target = target.equalTo((String) filterArguments[0]);
                } else if (filterArguments[0] instanceof Boolean) {
                    target = target.equalTo((Boolean) filterArguments[0]);
                } else {
                    throw new IllegalArgumentException(WRONG_ARGUMENT_TYPE2);
                }
                break;
        }
        return target;
    }
}
