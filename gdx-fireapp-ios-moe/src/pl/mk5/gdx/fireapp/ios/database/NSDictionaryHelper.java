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

import com.badlogic.gdx.utils.Json;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import apple.NSObject;
import apple.foundation.NSDictionary;
import apple.foundation.NSMutableDictionary;
import apple.foundation.NSString;

/**
 * Transforms {@code NSDictionary} and {@code Map}.
 * <p>
 */
class NSDictionaryHelper {

    private NSDictionaryHelper() {

    }

    @SuppressWarnings("unchecked")
    static Map<String, Object> toMap(NSDictionary<NSString, NSObject> nsDictionary) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Object key : nsDictionary.keySet()) {
            Object value = nsDictionary.get(key);
            map.put((String) key, DataProcessor.iosDataToJava(value));
        }
        return map;
    }

    @SuppressWarnings("unchecked")
    static NSDictionary toNSDictionary(Map<String, Object> map) {
        NSMutableDictionary<NSString, NSObject> dictionary = (NSMutableDictionary<NSString, NSObject>) NSMutableDictionary.alloc().init();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            Object value = map.get(key);
            dictionary.put(NSString.alloc().initWithString(key), DataProcessor.javaDataToIos(value));
        }
        return dictionary;
    }

    // TODO - check type erasure error occur?
    @SuppressWarnings("unchecked")
    static NSDictionary toNSDictionary(Object object) {
        if (object instanceof Map) {
            return toNSDictionary((Map) object);
        } else {
            String objectJsonData = new Json().toJson(object);
            Json json = new Json();
            json.setIgnoreUnknownFields(true);
            Map objectMap = json.fromJson(HashMap.class, objectJsonData);
            return toNSDictionary(objectMap);
        }
    }

}
