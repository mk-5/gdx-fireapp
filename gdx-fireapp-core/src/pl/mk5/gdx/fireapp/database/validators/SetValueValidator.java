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

package pl.mk5.gdx.fireapp.database.validators;


import com.badlogic.gdx.utils.Array;

import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;

/**
 * Validates arguments for {@link DatabaseDistribution#setValue(Object)}
 */
public class SetValueValidator implements ArgumentsValidator {

    private static final String MESSAGE1 = "Database#setValue needs at least 1 argument";

    @Override
    public void validate(Array<Object> arguments) {
        if (arguments.size == 0)
            throw new IllegalArgumentException(MESSAGE1);
    }
}
