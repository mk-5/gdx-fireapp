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

package pl.mk5.gdx.fireapp.html.firebase;

import com.badlogic.gdx.Gdx;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import pl.mk5.gdx.fireapp.html.GdxHtmlAppTest;

public class FirebaseConfigParserTest extends GdxHtmlAppTest {

    private static String FIREBASE_HTML_VALID = "<script src=\"https://www.gstatic.com/firebasejs/5.4.0/firebase.js\"></script>\n" +
            "<script>\n" +
            "  // Initialize Firebase\n" +
            "  var config = {\n" +
            "    apiKey: \"abc\",\n" +
            "    authDomain: \"abc\",\n" +
            "    databaseURL: \"abc\",\n" +
            "    projectId: \"abc\",\n" +
            "    storageBucket: \"abc\",\n" +
            "    messagingSenderId: \"abc\"\n" +
            "  };\n" +
            "  firebase.initializeApp(config);\n" +
            "</script>";

    private static String FIREBASE_HTML_WITHOUT_SCRIPT = "<script>\n" +
            "  // Initialize Firebase\n" +
            "  var config = {\n" +
            "    apiKey: \"abc\",\n" +
            "    authDomain: \"abc\",\n" +
            "    databaseURL: \"abc\",\n" +
            "    projectId: \"abc\",\n" +
            "    storageBucket: \"abc\",\n" +
            "    messagingSenderId: \"abc\"\n" +
            "  };\n" +
            "  firebase.initializeApp(config);\n" +
            "</script>";

    @Test
    public void getFirebaseScriptSrc() {
        // Given
        FirebaseConfigParser configParser = new FirebaseConfigParser(FIREBASE_HTML_VALID);

        // When
        String firebaseSrcScript = configParser.getFirebaseScriptSrc();

        // Then
        Assert.assertEquals("https://www.gstatic.com/firebasejs/5.4.0/firebase.js", firebaseSrcScript);
    }

    @Test
    public void getFirebaseScriptSrc_notValid() {
        // Given
        FirebaseConfigParser configParser = new FirebaseConfigParser(FIREBASE_HTML_WITHOUT_SCRIPT);

        // When
        String firebaseSrcScript = configParser.getFirebaseScriptSrc();

        // Then
        Assert.assertNull(firebaseSrcScript);
        Mockito.verify(Gdx.app, VerificationModeFactory.times(1)).error(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void getInitializationScript() {
        // Given
        FirebaseConfigParser configParser = new FirebaseConfigParser(FIREBASE_HTML_VALID);

        // When
        String initializationScript = configParser.getInitializationScript();

        // Then
        Assert.assertEquals("" +
                "// Initialize Firebase\n" +
                "  var config = {\n" +
                "    apiKey: \"abc\",\n" +
                "    authDomain: \"abc\",\n" +
                "    databaseURL: \"abc\",\n" +
                "    projectId: \"abc\",\n" +
                "    storageBucket: \"abc\",\n" +
                "    messagingSenderId: \"abc\"\n" +
                "  };\n" +
                "  firebase.initializeApp(config);", initializationScript.trim());
    }

    @Test
    public void getLazyLoadingInitializationScript() {
        // Given
        FirebaseConfigParser configParser = new FirebaseConfigParser(FIREBASE_HTML_VALID);

        // When
        String lazyLoadingScript = configParser.getLazyLoadingInitializationScript();

        // Then
        Assert.assertTrue(lazyLoadingScript.contains(configParser.getFirebaseScriptSrc()));
        Assert.assertTrue(lazyLoadingScript.contains(configParser.getInitializationScript()));
    }
}