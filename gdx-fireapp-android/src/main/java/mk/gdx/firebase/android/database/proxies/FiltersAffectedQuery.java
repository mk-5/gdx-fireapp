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

package mk.gdx.firebase.android.database.proxies;

import com.google.firebase.database.ValueEventListener;

/**
 * Methods from {@code Query} that are filters affected.
 */
public interface FiltersAffectedQuery {

    /**
     * @see com.google.firebase.database.DatabaseReference#addListenerForSingleValueEvent(ValueEventListener)
     * @see com.google.firebase.database.Query#addListenerForSingleValueEvent(ValueEventListener)
     */
    void addListenerForSingleValueEvent(ValueEventListener valueEventListener);

    /**
     * @see com.google.firebase.database.DatabaseReference#addValueEventListener(ValueEventListener)
     * @see com.google.firebase.database.Query#addValueEventListener(ValueEventListener)
     */
    ValueEventListener addValueEventListener(ValueEventListener valueEventListener);
}
