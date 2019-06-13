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
    private Class<? extends E2ETest> onlyType;

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
        if (onlyType != null) {
            testsStable.clear();
            try {
                addNext(onlyType, testTimeout.get(onlyType));
            } catch (ReflectionException e) {
                Gdx.app.error(E2ETestRunnerImpl.class.getSimpleName(), e.getMessage(), e);
            }
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

    @Override
    public void only(Class<? extends E2ETest> testType) {
        this.onlyType = testType;
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
