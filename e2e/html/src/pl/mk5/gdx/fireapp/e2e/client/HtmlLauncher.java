package pl.mk5.gdx.fireapp.e2e.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import pl.mk5.gdx.fireapp.e2e.GdxFireappE2ETests;

public class HtmlLauncher extends GwtApplication {

    // USE THIS CODE FOR A FIXED SIZE APPLICATION
    @Override
    public GwtApplicationConfiguration getConfig() {
        return new GwtApplicationConfiguration(480, 320);
    }
    // END CODE FOR FIXED SIZE APPLICATION

    @Override
    public void log(String tag, String message) {
        jsLog(tag, message);
    }

    @Override
    public void log(String tag, String message, Throwable exception) {
        jsLog(tag, message + ", exception: " + exception.getLocalizedMessage());
    }

    @Override
    public void error(String tag, String message) {
        jsError(tag, message);
    }

    @Override
    public void error(String tag, String message, Throwable exception) {
        jsError(tag, message + ", exception: " + exception.getLocalizedMessage());
    }

    public static native void jsLog(String tag, String message) /*-{
        console.log(tag + ":", message);
    }-*/;

    public static native void jsError(String tag, String message) /*-{
        console.error(tag + ":", message);
    }-*/;

    // UNCOMMENT THIS CODE FOR A RESIZABLE APPLICATION
    // PADDING is to avoid scrolling in iframes, set to 20 if you have problems
    // private static final int PADDING = 0;
    // private GwtApplicationConfiguration cfg;
    //
    // @Override
    // public GwtApplicationConfiguration getConfig() {
    //     int w = Window.getClientWidth() - PADDING;
    //     int h = Window.getClientHeight() - PADDING;
    //     cfg = new GwtApplicationConfiguration(w, h);
    //     Window.enableScrolling(false);
    //     Window.setMargin("0");
    //     Window.addResizeHandler(new ResizeListener());
    //     cfg.preferFlash = false;
    //     return cfg;
    // }
    //
    // class ResizeListener implements ResizeHandler {
    //     @Override
    //     public void onResize(ResizeEvent event) {
    //         int width = event.getWidth() - PADDING;
    //         int height = event.getHeight() - PADDING;
    //         getRootPanel().setWidth("" + width + "px");
    //         getRootPanel().setHeight("" + height + "px");
    //         getApplicationListener().resize(width, height);
    //         Gdx.graphics.setWindowedMode(width, height);
    //     }
    // }
    // END OF CODE FOR RESIZABLE APPLICATION

    @Override
    public ApplicationListener createApplicationListener() {
        try {
            return new GdxFireappE2ETests();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}