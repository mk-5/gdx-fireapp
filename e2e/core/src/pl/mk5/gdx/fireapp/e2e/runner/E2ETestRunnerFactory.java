package pl.mk5.gdx.fireapp.e2e.runner;

public class E2ETestRunnerFactory {
    public static E2ETestRunner factory() {
        return new E2ETestRunnerImpl();
    }
}
