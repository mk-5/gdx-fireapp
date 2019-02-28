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
 *
 *
 */

package pl.mk5.gdx.fireapp.html.firebase;

import com.badlogic.gdx.Gdx;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Gets firebase sdk api .js file and initialization script from raw Firebase configuration html.
 * <p>
 */
public class FirebaseConfigParser {

    private String rawHtml;
    private String firebaseScriptSrc;
    private String initializationScript;

    /**
     * Parse html configuration to {@code firebaseScriptSrc} and {@code initializationScri[t}
     *
     * @param rawHtml HTML from firebase console
     */
    public FirebaseConfigParser(String rawHtml) {
        this.rawHtml = rawHtml;
        firebaseScriptSrc = parseFirebaseScriptSrc();
        initializationScript = parseFirebaseInitializationScript();
    }

    public String getFirebaseScriptSrc() {
        return firebaseScriptSrc;
    }

    public String getInitializationScript() {
        return initializationScript;
    }

    /**
     * @return Laz firebase initialization script.
     */
    public String getLazyLoadingInitializationScript() {
        return "(function(){\n" +
                "var script = document.createElement(\"script\");\n" +
                "script.src = \"" + getFirebaseScriptSrc() + "\";\n" +
                "script.onload = function(){" +
                getInitializationScript() +
                "};" +
                "document.querySelector(\"body\").appendChild(script);\n" +
                "}());";
    }

    // TODO - allow to add more then one script (after sdk 5.0 should be one script per firebase package)
    private String parseFirebaseInitializationScript() {
        Pattern pattern = Pattern.compile("<script>((.|\\n|\\rn|\\r)*?)</script>");
        Matcher matcher = pattern.matcher(rawHtml);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            Gdx.app.error("GdxFireapp", "Can't find <script>...</script> inside Firebase configuration file.");
        }
        return null;
    }

    private String parseFirebaseScriptSrc() {
        Pattern pattern = Pattern.compile("script\\s+src=\"(.*?)\"");
        Matcher matcher = pattern.matcher(rawHtml);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            Gdx.app.error("GdxFireapp", "Can't find <script src=\"...\" inside Firebase configuration file.");
        }
        return null;
    }
}
