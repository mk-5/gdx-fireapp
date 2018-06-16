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

package mk.gdx.firebase.callbacks;

/**
 * Handles response when dealing with data and get care about concurrent modification of same data.
 *
 * @param <T> Type of data you want to deal with.
 */
public interface TransactionCallback<T> {
    /**
     * Deals with firebase database value when you need to get care about concurrent modification of same data.
     *
     * @param transactionData Transaction input data - before modification
     * @return Data after modification, this value will be pass as Transaction result to Firebase database
     */
    T run(T transactionData);
}
