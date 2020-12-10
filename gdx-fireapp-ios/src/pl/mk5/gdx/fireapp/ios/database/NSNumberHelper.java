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

import org.robovm.apple.foundation.NSNumber;

class NSNumberHelper {

    private NSNumberHelper() {

    }

    static Object getNSNumberPrimitive(NSNumber nsNumber) {
        String cType = nsNumber.getObjCType();
        switch (cType) {
            case "c":
                return nsNumber.booleanValue();
            case "i":
                return nsNumber.intValue();
            case "q":
                return nsNumber.longValue();
            case "f":
                return nsNumber.floatValue();
            case "d":
                return nsNumber.doubleValue();
            default:
                return nsNumber.intValue();
        }
    }
}
