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
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.user.client.ui.Image;

import mk.gdx.firebase.html.GwtBase64Utils;

/**
 * Fake class implementation. For GWT only.
 */
public class ImageHelper
{

    /**
     * Transforms byte[] to Texture Region.
     */
    public static TextureRegion createTextureFromBytes(byte[] bytes)
    {
        // TODO -
        String base64 = "data:image/png;base64," + GwtBase64Utils.instance().toBase64(bytes);
        Image image = new Image(base64);
        image.getElement().setAttribute("crossOrigin", "anonymous");
        ((GwtApplication) Gdx.app).getPreloader().images.put(base64, ImageElement.as(image.getElement()));
        return new TextureRegion(new Texture(Gdx.files.internal(base64)));
    }
}
