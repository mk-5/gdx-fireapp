package pl.mk5.gdx.fireapp.e2e.runner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.ReflectionException;


class E2ETestRunnerImpl implements E2ETestRunner {

    private static final float DEFAULT_TEST_DURATION = 5f;
    private final Array<E2ETest> tests = new Array<>();
    private final Array<E2ETest> testsStable = new Array<>();
    private final ObjectMap<Class<? extends E2ETest>, Float> testTimeout = new ObjectMap<>();
    private float state = 0f;
    private Runnable onFinish;

    @Override
    public void addNext(Class<? extends E2ETest> testType) throws ReflectionException {
        this.addNext(testType, DEFAULT_TEST_DURATION);
    }

    @Override
    public void addNext(Class<? extends E2ETest> testType, float timeoutSeconds) throws ReflectionException {
        E2ETest test = ClassReflection.newInstance(testType);
        testsStable.insert(0, test);
        testTimeout.put(testType, timeoutSeconds);
    }

    @Override
    public void start() {
        if (testsStable.size == 0) {
            throw new IllegalStateException("No tests to run.");
        }
        tests.addAll(testsStable);
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

    private void startTest(E2ETest test) {
        Gdx.app.postRunnable(new TestAction(tests.peek()));
        Gdx.app.log(E2ETestRunner.class.toString(), "Start new test " + tests.peek().getClass());
        state = 0;
    }

    private void checkTestsStates() {
        for (int i = tests.size - 1; i >= 0; i--) {
            E2ETest test = tests.get(i);
            if (!testTimeout.containsKey(test.getClass())) {
                throw new IllegalStateException();
            }
            if (test.isComplete()) {
                tests.peek().dispose();
                tests.pop();
                if (tests.size > 0) {
                    startTest(tests.peek());
                } else {
                    Gdx.app.log(E2ETestRunner.class.toString(), "No more tests to run");
                    if (this.onFinish != null) {
                        this.onFinish.run();
                    }
                }
                break;
            } else if (state >= testTimeout.get(test.getClass())) {
                throw new IllegalStateException(test.getClass().getSimpleName() + " timeout");
            }
        }
    }
}
