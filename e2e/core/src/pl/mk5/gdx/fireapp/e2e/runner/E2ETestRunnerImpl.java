package pl.mk5.gdx.fireapp.e2e.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;


class E2ETestRunnerImpl implements E2ETestRunner {

    private static final float DEFAULT_TEST_DURATION = 10f;
    private final Array<E2ETest> tests = new Array<>();
    private final Array<E2ETest> testsTmp = new Array<>();
    private final ObjectMap<Class<? extends E2ETest>, Float> testTimeout = new ObjectMap<>();
    private float state = 0f;
    private Runnable onFinish;
    private final Array<Class<? extends E2ETest>> onlyTypes = new Array<>();

    @Override
    public void addNext(E2ETest test) {
        this.addNext(test, DEFAULT_TEST_DURATION);
    }

    @Override
    public void addNext(E2ETest test, float timeoutSeconds) {
        testsTmp.insert(0, test);
        testTimeout.put(test.getClass(), timeoutSeconds);
    }

    @Override
    public void start() {
        if (testsTmp.size == 0) {
            throw new IllegalStateException("No tests to run.");
        }
        if (!onlyTypes.isEmpty()) {
            for (int i = testsTmp.size - 1; i >= 0; i--) {
                if (!onlyTypes.contains(testsTmp.get(i).getClass(), true)) {
                    testTimeout.remove(testsTmp.get(i).getClass());
                    testsTmp.removeIndex(i);
                }
            }
        }
        tests.addAll(testsTmp);
        startTest(tests.peek());
    }

    @Override
    public void render(Batch batch) {
        if (tests.size == 0) return;
        tests.peek().update(MathUtils.clamp(0, 0.2f, Gdx.graphics.getDeltaTime()));
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        tests.peek().draw(batch);
        checkTestsStates();
        state += Gdx.graphics.getDeltaTime();
    }

    @Override
    public void onFinish(Runnable runnable) {
        this.onFinish = runnable;
    }

    @Override
    public void only(Class<? extends E2ETest>... testType) {
        this.onlyTypes.addAll(testType);
    }

    private void startTest(E2ETest test) {
        Gdx.app.postRunnable(new TestAction(test));
        Gdx.app.log(E2ETestRunner.class.getSimpleName(), "Start new test " + test.getClass().getSimpleName());
        state = 0;
    }

    private void checkTestsStates() {
        E2ETest test = tests.peek();
        if (!testTimeout.containsKey(test.getClass())) {
            throw new IllegalStateException();
        }
        if (test.isComplete()) {
            tests.pop().dispose();
            if (tests.size > 0) {
                startTest(tests.peek());
            } else {
                Gdx.app.log(E2ETestRunner.class.getSimpleName(), "No more tests to run");
                if (this.onFinish != null) {
                    this.onFinish.run();
                }
            }
        } else if (state >= testTimeout.get(test.getClass())) {
            throw new IllegalStateException(test.getClass().getSimpleName() + " timeout");
        }
    }
}
