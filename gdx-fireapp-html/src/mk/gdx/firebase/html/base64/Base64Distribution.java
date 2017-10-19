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

package mk.gdx.firebase.html.base64;

import com.google.gwt.user.server.Base64Utils;

import mk.gdx.firebase.html.GwtBase64UtilsDistribution;

/**
 * Provides access to GWT {@code Base64Utils} from core module.
 */
public class Base64Distribution implements GwtBase64UtilsDistribution
{
    @Override
    public byte[] fromBase64(String data)
    {
        return Base64Utils.fromBase64(data);
    }

    @Override
    public long longFromBase64(String value)
    {
        return Base64Utils.longFromBase64(value);
    }

    @Override
    public String toBase64(byte[] data)
    {
        return Base64Utils.toBase64(data);
    }

    @Override
    public String toBase64(long value)
    {
        return Base64Utils.toBase64(value);
    }
}
