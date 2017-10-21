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


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.File;

import mk.gdx.firebase.callbacks.DeleteCallback;
import mk.gdx.firebase.callbacks.DownloadCallback;
import mk.gdx.firebase.callbacks.UploadCallback;
import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.html.firebase.ScriptRunner;

/**
 * GWT Firebase storage api.
 *
 * @see StorageDistribution
 */
public class Storage implements StorageDistribution
{

    private String bucketUrl = "";

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(FileHandle file, String path, UploadCallback callback)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void upload(byte[] data, String path, UploadCallback callback)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void download(final String path, long bytesLimit, final DownloadCallback<byte[]> callback)
    {
        ScriptRunner.firebaseScript(new ScriptRunner.ScriptStorageAction(bucketUrl())
        {
            @Override
            public void run()
            {
                StorageJS.download(scriptBucketUrl, path, new UrlDownloadCallback(callback));
            }
        });
    }

    /**
     * Not supported in WEB api - you do not have access to file system.
     *
     * @param path       Path
     * @param targetFile Target file, if null the temporary file will be created.
     * @param callback   Callback
     */
    @Override
    public void download(String path, File targetFile, DownloadCallback<File> callback)
    {
        Gdx.app.error("GdxFireapp", "This method is not supported in Firebase Web API");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(String path, DeleteCallback callback)
    {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StorageDistribution inBucket(String url)
    {
        bucketUrl = url;
        return this;
    }

    private String bucketUrl()
    {
        return bucketUrl != null ? bucketUrl : "";
    }
}
