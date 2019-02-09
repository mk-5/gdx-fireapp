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

package pl.mk5.gdx.fireapp.html;

import org.junit.Assert;
import org.junit.Test;

public class GdxFIRAuthHtmlTest {


    @Test
    public void test_isGoogleAuthViaPopupShouldBeDefaultTrue() {
        Assert.assertTrue(GdxFIRAuthHtml.isGoogleAuthViaPopup());
    }

    @Test
    public void test_isGoogleAuthViaRedirectShouldBeDefaultFalse() {
        Assert.assertFalse(GdxFIRAuthHtml.isGoogleAuthViaRedirect());
    }

    @Test
    public void setGoogleAuthViaRedirect() {
        // When
        GdxFIRAuthHtml.setGoogleAuthViaRedirect(true);

        // Then
        Assert.assertTrue(GdxFIRAuthHtml.isGoogleAuthViaRedirect());
        Assert.assertFalse(GdxFIRAuthHtml.isGoogleAuthViaPopup());
    }

    @Test
    public void setGoogleAuthViaPopup() {
        // When
        GdxFIRAuthHtml.setGoogleAuthViaPopup(true);

        // Then
        Assert.assertTrue(GdxFIRAuthHtml.isGoogleAuthViaPopup());
        Assert.assertFalse(GdxFIRAuthHtml.isGoogleAuthViaRedirect());
    }

    @Test
    public void setGoogleAuthAlwaysPromptForAccount() {
        // When
        GdxFIRAuthHtml.setGoogleAuthAlwaysPromptForAccount(true);

        // Then
        Assert.assertTrue(GdxFIRAuthHtml.isGoogleAuthAlwaysPromptForAccount());
    }
}