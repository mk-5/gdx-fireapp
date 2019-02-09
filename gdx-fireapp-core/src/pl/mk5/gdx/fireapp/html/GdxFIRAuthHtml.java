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

/**
 * Special configuration for GWT platform.
 */
public class GdxFIRAuthHtml {

    private static boolean googleAuthViaPopup = true;
    private static boolean googleAuthViaRedirect;
    private static boolean googleAuthAlwaysPromptForAccount;

    private GdxFIRAuthHtml() {
        // 
    }

    /**
     * @return If true, google sign-in will be do via redirection, this method is preferred on mobile devices.
     */
    public static boolean isGoogleAuthViaRedirect() {
        return googleAuthViaRedirect;
    }

    /**
     * @param googleAuthViaRedirect If true, google sign-in will be do via redirection, this method is preferred on mobile devices.
     */
    public static void setGoogleAuthViaRedirect(boolean googleAuthViaRedirect) {
        GdxFIRAuthHtml.googleAuthViaRedirect = googleAuthViaRedirect;
        GdxFIRAuthHtml.googleAuthViaPopup = false;
    }

    /**
     * @return If true google sign-in will always prompt for account.
     */
    public static boolean isGoogleAuthAlwaysPromptForAccount() {
        return googleAuthAlwaysPromptForAccount;
    }

    /**
     * @param googleAuthAlwaysPromptForAccount If true google sign-in will always prompt for account.
     */
    public static void setGoogleAuthAlwaysPromptForAccount(boolean googleAuthAlwaysPromptForAccount) {
        GdxFIRAuthHtml.googleAuthAlwaysPromptForAccount = googleAuthAlwaysPromptForAccount;
    }

    /**
     * @return If true, google sign-in will be do via popup.
     */
    public static boolean isGoogleAuthViaPopup() {
        return googleAuthViaPopup;
    }

    /**
     * @param googleAuthViaPopup If true, google sign-in will be do via popup.
     */
    public static void setGoogleAuthViaPopup(boolean googleAuthViaPopup) {
        GdxFIRAuthHtml.googleAuthViaPopup = googleAuthViaPopup;
        GdxFIRAuthHtml.googleAuthViaRedirect = false;
    }
}
