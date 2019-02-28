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

package pl.mk5.gdx.fireapp.distributions;

/**
 * Starting point of work with firebase sdk.
 */
public interface AppDistribution {

    /**
     * Method needed only for ios initialization.
     * <p>
     * iOS requires manually firebase sdk initialization. So you should execute this method at same point of app initialization for ex:
     * <pre>
     * {@code
     * class Application implements ApplicationAdapter{
     *
     *      public void create()
     *      {
     *          GdxFIRApp.configure();
     *      }
     * }
     * }
     * </pre>
     * On android platform initialization is doing by google play services plugin which load and parse {@code google-services.json} file,
     * but calling this method on android does not have any effect on application.
     */
    void configure();
}
