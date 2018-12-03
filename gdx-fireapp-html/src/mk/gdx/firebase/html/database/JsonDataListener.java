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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.listeners.DataChangeListener;

/**
 * Wraps data listener into callback with String as return type.
 * <p>
 * On GWT platform it is not possible to use generic types inside javascript so it is a need to wrap our {@link DataChangeListener} and parse
 * json string from javascript to Java object.
 *
 * @param <R> Result type after json string converting process
 */
class JsonDataListener<R> implements DataChangeListener<String> {

    private Class<R> wantedType;
    private DataChangeListener dataListener;

    /**
     * @param dataListener Listener, not null
     */
    JsonDataListener(Class<R> wantedType, DataChangeListener dataListener) {
        this.wantedType = wantedType;
        this.dataListener = dataListener;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onChange(String data) {
        R result = JsonProcessor.process(wantedType, data);
        dataListener.onChange(result);
    }

    @Override
    public void onCanceled(Exception e) {
        dataListener.onCanceled(e);
    }
}
