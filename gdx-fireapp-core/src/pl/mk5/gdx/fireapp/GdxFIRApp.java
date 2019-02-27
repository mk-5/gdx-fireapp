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

package pl.mk5.gdx.fireapp;

import pl.mk5.gdx.fireapp.distributions.AppDistribution;
import pl.mk5.gdx.fireapp.promises.FuturePromise;
import pl.mk5.gdx.fireapp.promises.Promise;

/**
 * Gets access to Firebase App API in multi-modules.
 *
 * @see AppDistribution
 * @see PlatformDistributor
 */
public class GdxFIRApp extends PlatformDistributor<AppDistribution> implements AppDistribution {

    /**
     * GdxFIRApp protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    GdxFIRApp() {
    }

    public static GdxFIRApp instance() {
        return (GdxFIRApp) Api.instance(AppDistribution.class);
    }

    /**
     * Alias of {@link #instance()}
     */
    public static GdxFIRApp inst() {
        return instance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configure() {
        platformObject.configure();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "pl.mk5.gdx.fireapp.ios.App";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "pl.mk5.gdx.fireapp.android.App";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "pl.mk5.gdx.fireapp.html.App";
    }

    /**
     * If true (default) promises will be automatically subscribe
     *
     * @return default true
     */
    public static boolean isAutoSubscribePromises() {
        return FuturePromise.isAutoSubscribe();
    }

    /**
     * Sets 'promise auto subscribe' option.
     * <p>
     * If true (default) all promises will be automatically subscribe so you don't need to
     * subscribe it by {@link Promise#subscribe()} method.
     */
    public static void setAutoSubscribePromises(boolean autoSubscribePromises) {
        FuturePromise.setAutoSubscribe(autoSubscribePromises);
    }

    /**
     * If true all promises will be create with 'silentFail' as default (true).
     *
     * @return default true
     */
    public static boolean isThrowFailureByDefault() {
        return FuturePromise.isThrowFailByDefault();
    }

    /**
     * Sets global silent fail for all future promises.
     * <p>
     * If true (default) all promises will throw exception on failure by default.
     */
    public static void setThrowFailureByDefault(boolean throwFailureByDefault) {
        FuturePromise.setThrowFailByDefault(throwFailureByDefault);
    }
}
