package pl.mk5.gdxfireapp.e2e.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Timer;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRStorage;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdxfireapp.e2e.runner.E2ETest;

public class StorageDownloadImageTest extends E2ETest {

    Texture img;

    @Override
    public void action() {
        GdxFIRStorage.instance()
                .downloadImage(StorageUploadImageTest.STORAGE_PATH)
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<TextureRegion>() {
                    @Override
                    public void accept(TextureRegion textureRegion) {
                        img = textureRegion.getTexture();
                        Timer.schedule(new Timer.Task() {
                            @Override
                            public void run() {
                                success();
                            }
                        }, 2f);
                    }
                });
    }

    @Override
    public void draw(Batch batch) {
        if (img == null) return;
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {

    }
}
