/*
 * Copyright 2018 mk
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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.rule.PowerMockRule;

import pl.mk5.gdx.fireapp.functional.Consumer;

@PrepareForTest({Pixmap.class, TextureRegion.class, Gdx2DPixmap.class, Pixmap.Format.class})
@SuppressStaticInitializationFor("com.badlogic.gdx.graphics.g2d.Gdx2DPixmap")
public class ImageHelperTest {

    @Rule
    public PowerMockRule powerMockRule = new PowerMockRule();

    private Pixmap pixmap;

    @Before
    public void setUp() throws Exception {
        PowerMockito.mockStatic(Pixmap.class);
        PowerMockito.mockStatic(Pixmap.Format.class);
        PowerMockito.mockStatic(Gdx2DPixmap.class);
        PowerMockito.mockStatic(TextureRegion.class);
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx2DPixmap gdx2DPixmap = PowerMockito.mock(Gdx2DPixmap.class);
        pixmap = PowerMockito.mock(Pixmap.class);
        Mockito.when(pixmap.getWidth()).thenReturn(10);
        Mockito.when(pixmap.getHeight()).thenReturn(10);
        Mockito.when(pixmap.getFormat()).thenReturn(Pixmap.Format.RGB888);
        PowerMockito.whenNew(Gdx2DPixmap.class).withAnyArguments().thenReturn(gdx2DPixmap);
        PowerMockito.whenNew(Pixmap.class).withAnyArguments().thenReturn(pixmap);
    }

    @Test
    public void createTextureFromBytes() {
        // Given
        Consumer consumer = Mockito.mock(Consumer.class);
        byte[] bytes = {1, 0, 1, 1, 1, 1, 0, 0, 0, 0};
        ImageHelper imageHelper = new ImageHelper();

        // When
        TextureRegion textureRegion = imageHelper.createTextureFromBytes(bytes);

        // Then
        Assert.assertNotNull(textureRegion);
//        Mockito.verify(pixmap, VerificationModeFactory.times(2)).dispose();
    }

    @Test
    public void createTextureFromBytes1() {
        // Given
        Consumer consumer = Mockito.mock(Consumer.class);
        byte[] bytes = {1, 0, 1, 1, 1, 1, 0, 0, 0, 0};
        ImageHelper imageHelper = new ImageHelper();

        // When
        imageHelper.createTextureFromBytes(bytes, consumer);

        // Then
        Mockito.verify(consumer).accept(Mockito.any());
    }
}