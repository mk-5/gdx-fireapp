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

package pl.mk5.gdx.fireapp.helpers;

/**
 * Gives information about type origin.
 */
public class JavaCoreClassDetector {

    private JavaCoreClassDetector() {
        //
    }

    /**
     * Check if given type package name starts with 'java'.
     *
     * @param type Type to examination, not null
     * @return True if type is core java class.
     */
    public static boolean isJavaCoreClass(Class<?> type) {
        return type.getName().startsWith("java");
    }
}
