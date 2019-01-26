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

package mk.gdx.firebase;

import com.badlogic.gdx.Gdx;

/**
 * Provides GdxFireapp logs. Default disabled.
 * <p>
 * Client can disable/enable logging by calling
 */
public class GdxFIRLogger {
    private static final String LOG_TAG = "GdxFireapp";

    private static boolean enabled;

    private GdxFIRLogger() {
        //
    }

    /**
     * Logs message to the console or logcat.
     *
     * @param msg Log message
     */
    public static void log(String msg) {
        if (!enabled) return;
        Gdx.app.log(LOG_TAG, msg);
    }

    /**
     * Logs message to the console or logcat.
     *
     * @param msg Log message
     * @param t   Described exception
     */
    public static void log(String msg, Throwable t) {
        if (!enabled) return;
        Gdx.app.log(LOG_TAG, msg, t);
    }

    /**
     * Logs error to the console or logcat.
     *
     * @param msg Log message
     */
    public static void error(String msg) {
        if (!enabled) return;
        Gdx.app.error(LOG_TAG, msg);
    }

    /**
     * Logs error to the console or logcat.
     *
     * @param msg Log message
     * @param t   Described exception
     */
    public static void error(String msg, Throwable t) {
        if (!enabled) return;
        Gdx.app.error(LOG_TAG, msg, t);
    }

    /**
     * Gets the log tag.
     *
     * @return The log tag, not null
     */
    public static String getLogTag() {
        return LOG_TAG;
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        GdxFIRLogger.enabled = enabled;
    }
}
