package pl.mk5.gdxfireapp.e2e.tests;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mk.gdx.firebase.GdxFIRAuth;
import mk.gdx.firebase.GdxFIRDatabase;
import mk.gdx.firebase.database.FilterType;
import mk.gdx.firebase.database.OrderByMode;
import mk.gdx.firebase.functional.Consumer;
import pl.mk5.gdxfireapp.e2e.runner.E2ETest;

public class DatabaseLimitEqualTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final Map<String, String> employee = new HashMap<>();
        final Map<String, String> employee2 = new HashMap<>();
        employee.put("name", "bob");
        employee.put("salary", "1000");
        employee.put("name", "john");
        employee.put("salary", "5000");

        GdxFIRAuth.instance()
                .signInAnonymously()
                .then(GdxFIRDatabase.instance()
                        .inReference("/employee")
                        .push()
                        .setValue(employee))
//                .then(GdxFIRDatabase.instance()
//                        .inReference("/employee")
//                        .push()
//                        .setValue(employee2))
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        GdxFIRDatabase.instance().inReference("/employee")
                                .filter(FilterType.EQUAL_TO, "5000")
                                .orderBy(OrderByMode.ORDER_BY_CHILD, "salary")
                                .readValue(List.class)
                                .then(new Consumer<List>() {
                                    @Override
                                    public void accept(List list) {
                                        Map<String, String> employeeInfo = (Map<String, String>) list.get(0);
                                        if (employeeInfo.get("name").equals("john")) {
                                            success();
                                        }
                                    }
                                });
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
