/*
 * Copyright 2019 mk
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

package pl.mk5.gdx.fireapp.ios.storage;

import apple.foundation.NSError;
import pl.mk5.gdx.fireapp.promises.FuturePromise;

/**
 * Wraps error handling flow for different callbacks type.
 * <p>
 * Wrapped callbacks:
 * <ul>
 * <li>{@link FuturePromise}
 */
class ErrorHandler {
    static boolean handleError(NSError error, FuturePromise promise) {
        if (error != null) {
            promise.doFail(new Exception(error.localizedDescription()));
            return true;
        }
        return false;
    }
}
