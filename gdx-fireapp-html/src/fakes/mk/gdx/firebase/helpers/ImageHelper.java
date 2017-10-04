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

import com.badlogic.gdx.graphics.g2d.TextureRegion;

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
        // Gwt does not support Pixmap(byte[], int, int) and throws
        // error: 'The constructor Pixmap(byte[], int, int) is undefined' on compilation step.
        // Need to find other way on GWT.

        return null;
    }
}
