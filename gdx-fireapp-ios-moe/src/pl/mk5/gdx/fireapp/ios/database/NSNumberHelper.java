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

import apple.foundation.NSNumber;

/**
 * Transforms {@code NSNumber} to java {@code Number} equivalent.
 */
public class NSNumberHelper {

    private NSNumberHelper() {

    }

    /**
     * Transforms {@code NSNumber} to java {@code Number} equivalent.
     * <p>
     * Possible values are instances of {@link Number}
     *
     * @param nsNumber {@code NSNumber} that you want to transform.
     * @return Transformed value, default value is {@link NSNumber#integerValue()}
     */
    static Object getNSNumberPrimitive(NSNumber nsNumber) {
        String cType = nsNumber.objCType();
        switch (cType) {
            case "c":
                return nsNumber.boolValue();
            case "i":
                return nsNumber.integerValue();
            case "q":
                return nsNumber.longValue();
            case "f":
                return nsNumber.floatValue();
            case "d":
                return nsNumber.doubleValue();
            default:
                return nsNumber.integerValue();
        }
    }
}
