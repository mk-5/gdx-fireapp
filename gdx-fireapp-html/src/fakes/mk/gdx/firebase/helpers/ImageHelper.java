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

package mk.gdx.firebase.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Base64Coder;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.DOM;

;

/**
 * Fake class implementation. For GWT only.
 */
public class ImageHelper
{

    /**
     * Transforms byte[] to Texture Region.
     * <p>
     * FIXME
     */
    public static TextureRegion createTextureFromBytes(byte[] bytes)
    {
        String base64 = "data:image/png;base64," + new String(Base64Coder.encode(bytes));
        ImageElement imageElement = DOM.createImg().cast();
        DOM.setImgSrc(imageElement, base64);
//        RootPanel.getBodyElement().appendChild(imageElement);
        // TODO Base64 data is ok here (checked in browser as base64 url string)
        // TODO - it is a problem with pixmap - it is not ready now, is need to wait until it is loaded.
        // TODO - maybe LazyTextureRegion class? or make GWT image downloading by GdxFIRStorage#downloadImage only?
        Pixmap pixmap = new Pixmap(imageElement);
        Gdx.app.log("ImageHelper", "pixmap: " + pixmap.getWidth() + "/" + pixmap.getHeight());
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
}
