package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.graphics.g2d.Batch;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.auth.GdxFirebaseUser;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;

import static pl.mk5.gdx.fireapp.e2e.tests.AuthCreateUserEmailPasswordTest.PASSWORD;
import static pl.mk5.gdx.fireapp.e2e.tests.AuthCreateUserEmailPasswordTest.USER;

public class AuthSignInUserEmailPasswordTest extends E2ETest {

    @Override
    public void action() {
        GdxFIRAuth.instance()
                .signInWithEmailAndPassword(USER, PASSWORD.toCharArray())
                .then(new Consumer<GdxFirebaseUser>() {
                    @Override
                    public void accept(GdxFirebaseUser gdxFirebaseUser) {
                        success();
                        // TODO - delete before creation?
                        gdxFirebaseUser.delete();
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
