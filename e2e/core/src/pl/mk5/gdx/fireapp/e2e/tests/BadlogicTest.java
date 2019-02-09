package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;

public class BadlogicTest extends E2ETest {
    private Texture img;

    @Override
    public void action() {
        img = new Texture("badlogic.jpg");
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                success();
            }
        });
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        batch.draw(img, 0, 0);
        batch.end();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        img.dispose();
    }
}
