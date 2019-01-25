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

package mk.gdx.firebase.database;

import com.badlogic.gdx.utils.Array;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class FilteringStateEnsurerTest {

    @Test
    public void checkFilteringState_valid() {
        // Given
        Array<Filter> filtersArray = new Array<>();
        filtersArray.add(new Filter());
        OrderByClause orderByClause = null;
        Class wantedType = List.class;

        // When
        try {
            FilteringStateEnsurer.checkFilteringState(filtersArray, orderByClause, wantedType);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void checkFilteringState_valid2() {
        // Given
        Array<Filter> filtersArray = new Array<>();
        OrderByClause orderByClause = new OrderByClause();
        Class wantedType = List.class;

        // When
        try {
            FilteringStateEnsurer.checkFilteringState(filtersArray, orderByClause, wantedType);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void checkFilteringState_wrongType() {
        // Given
        Array<Filter> filtersArray = new Array<>();
        filtersArray.add(new Filter());
        OrderByClause orderByClause = null;
        Class wantedType = Map.class;

        // When
        FilteringStateEnsurer.checkFilteringState(filtersArray, orderByClause, wantedType);

        // Then
        Assert.fail();
    }
}