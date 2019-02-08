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

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import mk.gdx.firebase.distributions.StorageDistribution;
import mk.gdx.firebase.promises.Promise;
import mk.gdx.firebase.storage.FileMetadata;

/**
 * Gets access to Firebase Analytics API in multi-modules.
 *
 * @see StorageDistribution
 * @see PlatformDistributor
 */
public class GdxFIRStorage extends PlatformDistributor<StorageDistribution> implements StorageDistribution {

    private static volatile GdxFIRStorage instance;

    /**
     * GdxFIRStorage protected constructor.
     * <p>
     * Instance of this class should be getting by {@link #instance()}
     * <p>
     * {@link PlatformDistributor#PlatformDistributor()}
     */
    private GdxFIRStorage() {
    }

    /**
     * @return Thread-safe singleton instance of this class.
     */
    public static GdxFIRStorage instance() {
        GdxFIRStorage result = instance;
        if (result == null) {
            synchronized (GdxFIRStorage.class) {
                result = instance;
                if (result == null) {
                    instance = result = new GdxFIRStorage();
                }
            }
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileMetadata> upload(String path, FileHandle file) {
        return platformObject.upload(path, file);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileMetadata> upload(String path, byte[] data) {
        return platformObject.upload(path, data);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<byte[]> download(String path, long bytesLimit) {
        return platformObject.download(path, bytesLimit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<FileHandle> download(String path, FileHandle targetFile) {
        return platformObject.download(path, targetFile);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Promise<Void> delete(String path) {
        return platformObject.delete(path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StorageDistribution inBucket(String url) {
        return platformObject.inBucket(url);
    }


    /**
     * Downloads texture from Firebase storage.
     * <p>
     * Image is represented by TextureRegion because of need of size which should be power of two.<p>
     * Remember to dispose texture when you done with it:
     * <p>
     * {@code
     * region.getTexture().dispose();
     * }
     * <p>
     *
     * @param path Path in FirebaseStorage bucket.
     */
    public Promise<TextureRegion> downloadImage(String path) {
        return new TextureRegionDownloader(path).download();
    }


    /**
     * {@inheritDoc}
     */
    @Override
    protected String getIOSClassName() {
        return "mk.gdx.firebase.ios.storage.Storage";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getAndroidClassName() {
        return "mk.gdx.firebase.android.storage.Storage";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getWebGLClassName() {
        return "mk.gdx.firebase.html.storage.Storage";
    }
}
