package pl.mk5.gdxfireapp.e2e.runner;

public class E2ETestRunnerFactory {
    public static E2ETestRunner factory() {
        return new E2ETestRunnerImpl();
    }
}
