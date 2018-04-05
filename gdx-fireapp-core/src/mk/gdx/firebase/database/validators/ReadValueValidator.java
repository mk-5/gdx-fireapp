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

package mk.gdx.firebase.database.validators;

import com.badlogic.gdx.utils.Array;

import mk.gdx.firebase.callbacks.DataCallback;

/**
 * Validates arguments given to {@link mk.gdx.firebase.distributions.DatabaseDistribution#readValue(Class, DataCallback)}
 */
public class ReadValueValidator implements ArgumentsValidator
{
    @Override
    public void validate(Array<Object> arguments)
    {
        if (arguments.size != 2)
            throw new IllegalStateException();
        if (!(arguments.get(0) instanceof Class))
            throw new IllegalArgumentException();
        if (!(arguments.get(1) instanceof DataCallback))
            throw new IllegalArgumentException();
    }
}
