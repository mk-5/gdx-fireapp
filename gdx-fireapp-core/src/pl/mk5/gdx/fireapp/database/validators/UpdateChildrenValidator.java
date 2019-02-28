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

import java.util.Map;

import pl.mk5.gdx.fireapp.distributions.DatabaseDistribution;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Validates arguments for {@link DatabaseDistribution#updateChildren(Map)}
 */
public class UpdateChildrenValidator implements ArgumentsValidator {

    private static final String MESSAGE1 = "Database#updateChildren needs at least 1 argument";
    private static final String MESSAGE2 = "The first argument should be null or Map";
    private static final String MESSAGE3 = "The second argument should be null or FuturePromise";

    @Override
    public void validate(Array<Object> arguments) {
        if (arguments.size == 0)
            throw new IllegalArgumentException(MESSAGE1);
        if (arguments.get(0) != null && !(arguments.get(0) instanceof Map))
            throw new IllegalArgumentException(MESSAGE2);
        if (arguments.size > 1 && arguments.get(1) != null && !(arguments.get(1) instanceof FuturePromise))
            throw new IllegalArgumentException(MESSAGE3);
    }
}
