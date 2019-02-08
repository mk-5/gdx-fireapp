package pl.mk5.gdxfireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRStorage;
import mk.gdx.firebase.functional.Consumer;
import mk.gdx.firebase.storage.FileMetadata;
import pl.mk5.gdxfireapp.e2e.runner.E2ETest;

public class StorageUploadImageTest extends E2ETest {
    @Override
    public void action() {
        GdxFIRStorage.instance()
                .upload(Gdx.files.internal("badlogic.jpg").readBytes(), "badlogic.jpg")
                .after(GdxFIRAuth.instance().signInAnonymously())
                .then(new Consumer<FileMetadata>() {
                    @Override
                    public void accept(FileMetadata fileMetadata) {
                        success();
                    }
                });
    }

    @Override
    public void draw(Batch batch) {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {

    }
}
