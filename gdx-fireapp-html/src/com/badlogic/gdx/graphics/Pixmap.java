package com.badlogic.gdx.graphics;

import com.google.gwt.dom.client.ImageElement;

import pl.mk5.gdx.fireapp.functional.Consumer;

/**
 * Stub implementation - do nothing.
 * <p>
 * libGDX gwt backend has fake emu implementation of Pixmap which has Pixmap(ImageElement) constructor.
 * <p>
 * To avoid compilation errors while call this constructor inside {@link pl.mk5.gdx.fireapp.helpers.ImageHelper#createTextureFromBytes(byte[], Consumer)}
 * it is need to create this stub.
 */
public class Pixmap {

    public Pixmap(ImageElement img) {
        // Stub, do nothing
    }

    public Pixmap(int width, int height, Pixmap.Format format) {
        // Stub, do nothing
    }

    public int getWidth() {
        return 0;
    }

    public int getHeight() {
        return 0;
    }

    public void drawPixmap(Pixmap pixmap, int x, int y, int srcx, int srcy, int srcWidth, int srcHeight) {
        // Stub, do nothing
    }

    public Pixmap.Format getFormat() {
        return null;
    }

    enum Format {

    }

    public void dispose() {
        // Stub, do nothing
    }
}
