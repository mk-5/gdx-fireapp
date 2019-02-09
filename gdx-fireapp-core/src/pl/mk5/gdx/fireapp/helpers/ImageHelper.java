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

package pl.mk5.gdx.fireapp.helpers;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Helper for dealing with transforming {@code byte[]} to {@code TextureRegion}.
 */
public class ImageHelper {

    /**
     * Transforms byte[] to Texture Region.
     * <p>
     * If you are going to call this method inside firebase callback remember to wrap it<p>
     * into {@code Gdx.app.postRunnable(Runnable)}.
     * The texture will be changed so that it has sides with length of power of 2.
     *
     * @param bytes Byte array with image description
     * @return Texture region representation of given byte array
     */
    public TextureRegion createTextureFromBytes(byte[] bytes) {
        Pixmap pixmap = new Pixmap(bytes, 0, bytes.length);
        final int orgWidth = pixmap.getWidth();
        final int orgHeight = pixmap.getHeight();
        int width = MathUtils.nextPowerOfTwo(orgWidth);
        int height = MathUtils.nextPowerOfTwo(orgHeight);
        final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
        potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
        pixmap.dispose();
        TextureRegion region = new TextureRegion(new Texture(potPixmap), 0, 0, orgWidth, orgHeight);
        potPixmap.dispose();
        return region;
    }

    /**
     * Creates texture region from byte[].
     * <p>
     * This method was created because need of consistity android-ios and gwt api.
     *
     * @param bytes    Image byte[] representation, not null
     * @param consumer Consumer where you should deal with region, not null
     */
    public void createTextureFromBytes(byte[] bytes, final Consumer<TextureRegion> consumer) {
        consumer.accept(createTextureFromBytes(bytes));
    }
}
