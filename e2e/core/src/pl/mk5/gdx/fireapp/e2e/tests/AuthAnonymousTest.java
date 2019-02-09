package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import mk.gdx.firebase.GdxFIRApp;
import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.functional.BiConsumer;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdx.fireapp.e2e.result.TestLogger;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;

public class AuthAnonymousTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        font = new BitmapFont();
        font.getData().scale(Gdx.graphics.getHeight() / 200f);
        GdxFIRApp.instance().configure();
        GdxFIRAuth.instance().signInAnonymously()
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        success();
                    }
                })
                .fail(new BiConsumer<String, Throwable>() {
                    @Override
                    public void accept(String s, Throwable throwable) {
                        TestLogger.error(AuthAnonymousTest.this, s, throwable);
                    }
                });
    }

    @Override
    public void draw(Batch batch) {
        batch.begin();
        font.draw(batch, AuthAnonymousTest.class.toString() + " success", 0, 0);
        batch.end();
    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {
        font.dispose();
    }
}
