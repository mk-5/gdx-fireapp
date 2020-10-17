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
 * Provides access to Firebase crash method.
 * <p>
 * Basic Firebase crash functionality is initialized during application startup.<p>
 * On iOS it is possible that your logs will be only information about ios-moe 'kill signal', then you
 * should implement {@link Thread#setUncaughtExceptionHandler(Thread.UncaughtExceptionHandler)} inside {@code ios-moe} module.
 */
public interface CrashDistribution {

    /**
     * Logs custom message to Firebase.
     * <p>
     * It will be accessible inside Firebase console within about 24 hours.
     *
     * @param message Custom message you want to log into firebase console.
     */
    void log(String message);

    /**
     * Initialize crash reporting.
     * <p>
     * Fabric.with will be call here so you don't need to add it manually to your AndroidLauncher/IOSMoeLauncher.
     */
    void initialize();

    /**
     * Records exception, you can use it in try/catch block.
     *
     * @param throwable throwable, not null
     */
    void recordException(Throwable throwable);

    /**
     * https://firebase.google.com/docs/crashlytics/customize-crash-reports?platform=android#set-user-ids
     *
     * @param userId userId, not null
     */
    void setUserId(String userId);

    /**
     * Custom keys help you get the specific state of your app leading up to a crash.
     * You can associate arbitrary key/value pairs with your crash reports and see them in the Firebase console
     *
     * @param key   key, not null
     * @param value value, not null
     * @param <T>   one of those types [boolean, int, string, float, double, long]
     */
    <T> void setCustomKey(String key, T value);
}
