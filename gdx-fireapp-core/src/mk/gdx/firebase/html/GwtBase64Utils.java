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

package mk.gdx.firebase.html;


import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import mk.gdx.firebase.PlatformDistributor;
import mk.gdx.firebase.exceptions.PlatformDistributorException;

/**
 * Access to Gwt Base64Utils from core module.
 */
public class GwtBase64Utils extends PlatformDistributor<GwtBase64UtilsDistribution> implements GwtBase64UtilsDistribution
{

    private static GwtBase64Utils instance;

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GwtBase64Utils instance()
    {
        if (instance == null) {
            synchronized (GwtBase64Utils.class) {
                if (instance == null) {
                    try {
                        instance = new GwtBase64Utils();
                    } catch (PlatformDistributorException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return instance;
    }

    /**
     * Creates platform specific object by reflection.
     * <p>
     * Uses class names given by {@link #getAndroidClassName()} and {@link #getIOSClassName()}
     * <p>
     * If you need to run project on different platform use {@link #setMockObject(Object)} to polyfill platform object.
     *
     * @throws PlatformDistributorException Throws when something is wrong with environment
     */
    protected GwtBase64Utils() throws PlatformDistributorException
    {
        if (Gdx.app.getType() != Application.ApplicationType.WebGL)
            throw new IllegalStateException();
    }

    @Override
    protected String getIOSClassName()
    {
        return null;
    }

    @Override
    protected String getAndroidClassName()
    {
        return null;
    }

    @Override
    protected String getWebGLClassName()
    {
        return "mk.gdx.firebase.html.base64.Base64Distribution";
    }

    @Override
    public byte[] fromBase64(String data)
    {
        return platformObject.fromBase64(data);
    }

    @Override
    public long longFromBase64(String value)
    {
        return platformObject.longFromBase64(value);
    }

    @Override
    public String toBase64(byte[] data)
    {
        return platformObject.toBase64(data);
    }

    @Override
    public String toBase64(long value)
    {
        return platformObject.toBase64(value);
    }
}
