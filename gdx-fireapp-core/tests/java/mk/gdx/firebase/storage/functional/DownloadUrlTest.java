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

package mk.gdx.firebase.storage.functional;

import org.junit.Assert;
import org.junit.Test;

import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.storage.DownloadUrl;

public class DownloadUrlTest {

    @Test
    public void getUrl() {
        // Given
        DownloadUrl downloadUrl = new DownloadUrl("abc");

        // When
        final String[] string = {null};
        downloadUrl.getUrl(new Consumer<String>() {
            @Override
            public void accept(String s) {
                string[0] = s;
            }
        });

        // Then
        Assert.assertEquals("abc", string[0]);
    }
}