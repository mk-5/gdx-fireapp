package pl.mk5.gdxfireapp.e2e.runner;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

import pl.mk5.gdxfireapp.e2e.result.TestLogger;

public abstract class E2ETest implements Disposable {

    private boolean complete;

    public abstract void action();

    public abstract void draw(Batch batch);

    public abstract void update(float dt);

    protected void success() {
        TestLogger.success(this);
        complete = true;
    }

    boolean isComplete() {
        return complete;
    }
}
