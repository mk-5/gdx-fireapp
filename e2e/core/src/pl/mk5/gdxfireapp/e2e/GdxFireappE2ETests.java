package pl.mk5.gdxfireapp.e2e;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mk5.gdxfireapp.e2e.runner.E2ETestRunner;
import pl.mk5.gdxfireapp.e2e.runner.E2ETestRunnerFactory;
import pl.mk5.gdxfireapp.e2e.tests.AuthAnonymousTest;
import pl.mk5.gdxfireapp.e2e.tests.AuthCreateUserEmailPasswordTest;
import pl.mk5.gdxfireapp.e2e.tests.AuthGoogleSignInTest;
import pl.mk5.gdxfireapp.e2e.tests.AuthSignInUserEmailPasswordTest;
import pl.mk5.gdxfireapp.e2e.tests.AuthSignOutTest;
import pl.mk5.gdxfireapp.e2e.tests.BadlogicTest;
import pl.mk5.gdxfireapp.e2e.tests.StorageDeleteTest;
import pl.mk5.gdxfireapp.e2e.tests.StorageDownloadBytesTest;
import pl.mk5.gdxfireapp.e2e.tests.StorageDownloadImageTest;
import pl.mk5.gdxfireapp.e2e.tests.StorageUploadBytesTest;
import pl.mk5.gdxfireapp.e2e.tests.StorageUploadImageTest;

public class GdxFireappE2ETests extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;

    private final E2ETestRunner e2ETestRunner = E2ETestRunnerFactory.factory();

    public GdxFireappE2ETests() throws Exception {
        e2ETestRunner.addNext(BadlogicTest.class);
        e2ETestRunner.addNext(AuthAnonymousTest.class);
        e2ETestRunner.addNext(AuthCreateUserEmailPasswordTest.class);
        e2ETestRunner.addNext(AuthSignInUserEmailPasswordTest.class);
//        e2ETestRunner.addNext(AuthGoogleSignInTest.class);
        e2ETestRunner.addNext(AuthSignOutTest.class);

        e2ETestRunner.addNext(StorageUploadImageTest.class);
        e2ETestRunner.addNext(StorageUploadImageTest.class);
        e2ETestRunner.addNext(StorageDownloadImageTest.class, 30);
        e2ETestRunner.addNext(StorageUploadBytesTest.class);
        e2ETestRunner.addNext(StorageDownloadBytesTest.class, 30);
        e2ETestRunner.addNext(StorageDeleteTest.class, 30);
        e2ETestRunner.onFinish(new Runnable() {
            @Override
            public void run() {
                Gdx.app.exit();
            }
        });
    }

    @Override
    public void create() {
        batch = new SpriteBatch();
        e2ETestRunner.start();
    }

    @Override
    public void render() {
        e2ETestRunner.render(batch);
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
