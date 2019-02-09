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
import com.badlogic.gdx.utils.Base64Coder;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.RootPanel;

import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Fake class implementation. For GWT only.
 */
public class ImageHelper {

    /**
     * Covers implementation of {@code createTextureFromBytes} on GWT platform.
     * <p>
     * LibGDX GWT backend do not support {@link Pixmap (byte[] encodedData, int offset, int len) }
     * FIXME
     */
    public static TextureRegion createTextureFromBytes(byte[] bytes) {
        throw new IllegalStateException("No such feature at GWT please use ImageHelper#createTextureFromBytes(byte[] bytes, Consumer<TextureRegion> consumer) instead");
    }

    /**
     * Creates texture region from byte[].
     * <p>
     * GWT platform requires additional step (as far as i know) to deal with Pixmap. It is need to load Image element
     * and wait until it is loaded.
     *
     * @param bytes    Image byte[] representation, not null
     * @param consumer Consumer where you should deal with region, not null
     */
    public static void createTextureFromBytes(byte[] bytes, final Consumer<TextureRegion> consumer) {
        String base64 = "data:image/png;base64," + new String(Base64Coder.encode(bytes));
        final Image image = new Image();
        image.setVisible(false);
        image.addLoadHandler(new LoadHandler() {
            @Override
            public void onLoad(LoadEvent event) {
                ImageElement imageElement = image.getElement().cast();
                Pixmap pixmap = new Pixmap(imageElement);
                GdxFIRLogger.log("Image loaded");
                final int orgWidth = pixmap.getWidth();
                final int orgHeight = pixmap.getHeight();
                int width = MathUtils.nextPowerOfTwo(orgWidth);
                int height = MathUtils.nextPowerOfTwo(orgHeight);
                final Pixmap potPixmap = new Pixmap(width, height, pixmap.getFormat());
                potPixmap.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
                pixmap.dispose();
                TextureRegion region = new TextureRegion(new Texture(potPixmap), 0, 0, orgWidth, orgHeight);
                potPixmap.dispose();
                RootPanel.get().remove(image);
                consumer.accept(region);
            }
        });
        image.setUrl(base64);
        RootPanel.get().add(image);
    }
}
