package pl.mk5.gdx.fireapp.e2e;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import pl.mk5.gdx.fireapp.GdxFIRApp;
import pl.mk5.gdx.fireapp.GdxFIRLogger;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETestRunner;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETestRunnerFactory;
import pl.mk5.gdx.fireapp.e2e.tests.AnalyticsTest;
import pl.mk5.gdx.fireapp.e2e.tests.AuthAnonymousTest;
import pl.mk5.gdx.fireapp.e2e.tests.AuthCreateUserEmailPasswordTest;
import pl.mk5.gdx.fireapp.e2e.tests.AuthSignInUserEmailPasswordTest;
import pl.mk5.gdx.fireapp.e2e.tests.AuthSignOutTest;
import pl.mk5.gdx.fireapp.e2e.tests.BadlogicTest;
import pl.mk5.gdx.fireapp.e2e.tests.CrashTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseChildEventTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseLimitEqualTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseListenerValueCancelTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseListenerValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseOrderByChildTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseOrderByTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadPojoFailTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadPojoListTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadPojoMapWithKeysTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadPojoTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadValue2Test;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseReadValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseSetValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseTransactionDefaultDoubleValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseTransactionDefaultLongValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseTransactionDefaultStringValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseTransactionValue2Test;
import pl.mk5.gdx.fireapp.e2e.tests.DatabaseTransactionValueTest;
import pl.mk5.gdx.fireapp.e2e.tests.GdxFirebaseUserTest;
import pl.mk5.gdx.fireapp.e2e.tests.StorageDeleteTest;
import pl.mk5.gdx.fireapp.e2e.tests.StorageDownloadBytesTest;
import pl.mk5.gdx.fireapp.e2e.tests.StorageDownloadImageTest;
import pl.mk5.gdx.fireapp.e2e.tests.StorageUploadBytesTest;
import pl.mk5.gdx.fireapp.e2e.tests.StorageUploadImageTest;

public class GdxFireappE2ETests extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture img;

    private final E2ETestRunner e2ETestRunner = E2ETestRunnerFactory.factory();

    public GdxFireappE2ETests() throws Exception {
        GdxFIRLogger.setEnabled(true);
        e2ETestRunner.addNext(new BadlogicTest());
        e2ETestRunner.addNext(new AuthAnonymousTest());
        e2ETestRunner.addNext(new AuthCreateUserEmailPasswordTest());
        e2ETestRunner.addNext(new AuthSignInUserEmailPasswordTest());
//        e2ETestRunner.addNext(AuthGoogleSignInTest.class);
        e2ETestRunner.addNext(new GdxFirebaseUserTest(), 60);
        e2ETestRunner.addNext(new AuthSignOutTest());

        e2ETestRunner.addNext(new StorageUploadImageTest(), 60);
        e2ETestRunner.addNext(new StorageDownloadImageTest(), 30);
        e2ETestRunner.addNext(new StorageUploadBytesTest(), 60);
        e2ETestRunner.addNext(new StorageDownloadBytesTest(), 30);
        e2ETestRunner.addNext(new StorageDeleteTest(), 30);

        e2ETestRunner.addNext(new AnalyticsTest());
        e2ETestRunner.addNext(new CrashTest());

        e2ETestRunner.addNext(new DatabaseReadPojoTest(), 30);
        e2ETestRunner.addNext(new DatabaseReadPojoFailTest(), 30);
        e2ETestRunner.addNext(new DatabaseSetValueTest(), 10);
        e2ETestRunner.addNext(new DatabaseLimitEqualTest(), 60);
        e2ETestRunner.addNext(new DatabaseOrderByChildTest(), 60);
        e2ETestRunner.addNext(new DatabaseOrderByTest(), 60);
        e2ETestRunner.addNext(new DatabaseReadValueTest(), 10);
        e2ETestRunner.addNext(new DatabaseReadValue2Test(), 30);
        e2ETestRunner.addNext(new DatabaseListenerValueTest(), 10);
//        e2ETestRunner.addNext(new DatabaseListenerValueCancelTest(), 10);
        e2ETestRunner.addNext(new DatabaseReadPojoListTest(), 10);
        e2ETestRunner.addNext(new DatabaseReadPojoMapWithKeysTest(), 10);
        e2ETestRunner.addNext(new DatabaseChildEventTest(), 30);
        e2ETestRunner.addNext(new DatabaseTransactionValueTest(), 30);
        e2ETestRunner.addNext(new DatabaseTransactionValue2Test(), 30);
        e2ETestRunner.addNext(new DatabaseTransactionDefaultDoubleValueTest(), 120);
        e2ETestRunner.addNext(new DatabaseTransactionDefaultLongValueTest(), 30);
        e2ETestRunner.addNext(new DatabaseTransactionDefaultStringValueTest(), 30);


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
        GdxFIRApp.inst().configure();
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
