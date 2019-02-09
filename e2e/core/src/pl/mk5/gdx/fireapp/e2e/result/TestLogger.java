package pl.mk5.gdx.fireapp.e2e.result;

import com.badlogic.gdx.Gdx;

import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETestRunner;

public class TestLogger {

    private static final String SUCCESS = "Success";

    public static void success(E2ETest test) {
        Gdx.app.log(E2ETestRunner.class.toString(), test.getClass().getSimpleName() + " " + SUCCESS);
    }

    public static void log(E2ETest test, String msg, Throwable t) {
        Gdx.app.error(test.getClass().toString(), msg, t);
    }

    public static void error(E2ETest test, String msg, Throwable t) {
        Gdx.app.error(test.getClass().toString(), msg, t);
    }
}
