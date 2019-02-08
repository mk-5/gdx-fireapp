package pl.mk5.gdxfireapp.e2e.runner;

class TestAction implements Runnable{

    private final E2ETest test;

    TestAction(E2ETest test) {
        this.test = test;
    }

    @Override
    public void run() {
        test.action();
    }
}
