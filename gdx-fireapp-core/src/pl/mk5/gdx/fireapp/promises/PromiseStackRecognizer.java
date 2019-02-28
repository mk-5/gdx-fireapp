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

package pl.mk5.gdx.fireapp.promises;

class PromiseStackRecognizer {

    private final FuturePromise targetPromise;

    PromiseStackRecognizer(FuturePromise targetPromise) {
        this.targetPromise = targetPromise;
    }

    FuturePromise getBottomThenPromise() {
        if (targetPromise.getThenPromise() == null) return targetPromise;
        FuturePromise promise;
        FuturePromise tmp = targetPromise;
        do {
            promise = tmp;
            tmp = promise.getThenPromise();
        } while (tmp != null);
        return promise;
    }

    FuturePromise getTopParentPromise() {
        if (targetPromise.getParentPromise() == null) return null;
        FuturePromise promise;
        FuturePromise tmp = targetPromise.getParentPromise();
        do {
            promise = tmp;
            tmp = promise.getParentPromise();
        } while (tmp != null);
        return promise;
    }
}
