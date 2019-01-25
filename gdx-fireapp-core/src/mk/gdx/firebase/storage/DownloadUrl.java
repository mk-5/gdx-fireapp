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

package mk.gdx.firebase.storage;

import mk.gdx.firebase.functional.Consumer;

/**
 * Holds reference to the download url with support for async fetch.
 * <p>
 */
public class DownloadUrl {

    private final Consumer<Consumer<String>> urlConsumer;

    public DownloadUrl(final String url) {
        urlConsumer = new Consumer<Consumer<String>>() {
            @Override
            public void accept(Consumer<String> stringConsumer) {
                stringConsumer.accept(url);
            }
        };
    }

    public DownloadUrl(Consumer<Consumer<String>> urlConsumer) {
        this.urlConsumer = urlConsumer;
    }

    /**
     * Get download url, the url may be null.
     *
     * @param urlConsumer The url consumer, not null
     */
    public void getUrl(Consumer<String> urlConsumer) {
        this.urlConsumer.accept(urlConsumer);
    }
}
