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

package mk.gdx.firebase.html.storage;

import com.google.gwt.user.server.Base64Utils;

import mk.gdx.firebase.callbacks.DownloadCallback;

/**
 * Download callback working with base64 data.
 */
public class Base64DownloadCallback implements DownloadCallback<String>
{

    private DownloadCallback originalCallback;

    public Base64DownloadCallback(DownloadCallback originalCallback)
    {
        this.originalCallback = originalCallback;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onSuccess(String base64string)
    {
        byte[] data = Base64Utils.fromBase64(base64string);
        originalCallback.onSuccess(data); // todo - limit?
    }

    /**
     * @param e Exception describes what was wrong during authorization.
     */
    @Override
    public void onFail(Exception e)
    {
        originalCallback.onFail(e);
    }

}
