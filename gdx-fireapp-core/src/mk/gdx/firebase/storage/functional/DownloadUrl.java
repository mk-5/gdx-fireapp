package mk.gdx.firebase.storage.functional;

import mk.gdx.firebase.functional.Consumer;

/**
 * Holds reference to the download url with support for async fetch.
 * <p>
 */
public class DownloadUrl<T> {

    private Consumer<Consumer<String>> urlConsumer;

    public DownloadUrl(final String url)
    {
        urlConsumer = new Consumer<Consumer<String>>() {
            @Override
            public void accept(Consumer<String> stringConsumer)
            {
                stringConsumer.accept(url);
            }
        };
    }

    public DownloadUrl(Consumer<Consumer<String>> urlConsumer)
    {
        this.urlConsumer = urlConsumer;
    }

    /**
     * Get download url, the url may be null.
     *
     * @param urlConsumer The url consumer, not null
     */
    public void getUrl(Consumer<String> urlConsumer)
    {
        this.urlConsumer.accept(urlConsumer);
    }
}
