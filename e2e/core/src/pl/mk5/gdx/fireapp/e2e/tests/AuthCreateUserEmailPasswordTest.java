package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.graphics.g2d.Batch;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;

public class AuthCreateUserEmailPasswordTest extends E2ETest {

    public static final String USER = "test@test.com";
    public static final String PASSWORD = "secret";

    @Override
    public void action() {
        GdxFIRAuth.instance()
                .createUserWithEmailAndPassword(USER, PASSWORD.toCharArray())
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        success();
                    }
                });
    }

    @Override
    public void draw(Batch batch) {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void dispose() {

    }
}
