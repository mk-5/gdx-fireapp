package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseSetValueTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final Map<String, String> bob = new HashMap<>();
        final Map<String, String> john = new HashMap<>();
        bob.put("name", "bob");
        bob.put("salary", "1000");
        john.put("name", "john");
        john.put("salary", "5000");

        GdxFIRAuth.instance()
                .signInAnonymously()
                .then(GdxFIRDatabase.inst()
                        .inReference("/bob-and-john")
                        .removeValue())
                .then(GdxFIRDatabase.inst()
                        .inReference("/bob-and-john")
                        .push()
                        .setValue(bob))
                .then(GdxFIRDatabase.inst()
                        .inReference("/bob-and-john")
                        .push()
                        .setValue(john))
                .then(GdxFIRDatabase.inst()
                        .inReference("/bob-and-john")
                        .readValue(List.class))
                .then(new Consumer<List>() {
                    @Override
                    public void accept(List result) {
                        Gdx.app.log("App", "Result: " + result);
                        boolean bob = false;
                        boolean john = false;
                        for (Object o : result) {
                            if (((Map) o).get("name").equals("bob")) {
                                bob = true;
                            } else if (((Map) o).get("name").equals("john")) {
                                john = true;
                            }
                        }
                        if (bob && john) {
                            success();
                        } else {
                            Gdx.app.log(DatabaseSetValueTest.class.getSimpleName(), "result: " + result);
                        }
                        GdxFIRDatabase.inst()
                                .inReference("/bob-and-john")
                                .removeValue();
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
