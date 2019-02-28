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
package pl.mk5.gdx.fireapp.android.analytics;

import android.os.Bundle;

/**
 * The helper methods for {@link android.os.Bundle}.
 */
class BundleHelper {

    private BundleHelper() {
        //
    }

    /**
     * Put numeric value from string inside given bundle.
     * If {@code number} do not contain numeric value - nothing will happen
     *
     * @param bundle Bundle in which you want to put String numeric value.
     * @param key    Bundle key where you want to put the new value.
     * @param number String which should contain numeric value.
     */
    static void putNumberParam(Bundle bundle, String key, String number) {
        String[] types = {"int", "long", "double", "float"};
        boolean found = false;
        for (String type : types) {
            try {
                if (type.equals("int") && !number.toLowerCase().endsWith("l")) {
                    int value = Integer.parseInt(number);
                    bundle.putInt(key, value);
                    found = true;
                    break;
                } else if (type.equals("long")) {
                    if (number.toLowerCase().endsWith("l")) {
                        number = number.substring(0, number.length() - 1);
                    }
                    long value = Long.parseLong(number);
                    bundle.putLong(key, value);
                    found = true;
                    break;
                } else if (type.equals("float")) {
                    float value = Float.parseFloat(number);
                    bundle.putFloat(key, value);
                    found = true;
                    break;
                } else if (type.equals("double") && !number.toLowerCase().endsWith("f")) {
                    double value = Double.parseDouble(number);
                    bundle.putDouble(key, value);
                    found = true;
                    break;
                }
            } catch (NumberFormatException e) {
                // Do nothing
            }
        }
        if (!found) {
            throw new IllegalArgumentException();
        }
    }
}
