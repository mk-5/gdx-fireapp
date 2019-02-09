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

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;

import pl.mk5.gdx.fireapp.functional.Function;

/**
 * Takes JSON data from javascript, process it as java object and return back as json.
 */
class JsonDataModifier<T> {
    private Class<T> wantedType;
    private Function transactionFunction;

    JsonDataModifier(Class<T> wantedType, Function transactionFunction) {
        this.wantedType = wantedType;
        this.transactionFunction = transactionFunction;
    }

    /**
     * Returns modified json data.
     *
     * @param oldJsonData Old data as json string.
     * @return New data as json string
     */
    @SuppressWarnings("unchecked")
    public String modify(String oldJsonData) {
        T oldData = JsonProcessor.process(wantedType, oldJsonData);
        T newData = (T) transactionFunction.apply(oldData);
        Json json = new Json();
        json.setTypeName(null);
        json.setQuoteLongValues(true);
        json.setIgnoreUnknownFields(true);
        json.setOutputType(JsonWriter.OutputType.json);
        return json.toJson(newData, wantedType);
    }
}
