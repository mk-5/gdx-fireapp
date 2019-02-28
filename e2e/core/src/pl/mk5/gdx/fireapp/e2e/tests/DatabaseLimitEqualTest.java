package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.database.FilterType;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseLimitEqualTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final Map<String, String> employee = new HashMap<>();
        final Map<String, String> employee2 = new HashMap<>();
        employee.put("name", "bob");
        employee.put("salary", "1000");
        employee2.put("name", "john");
        employee2.put("salary", "5000");

        GdxFIRAuth.instance()
                .signInAnonymously()
                .then(GdxFIRDatabase.inst()
                        .inReference("/employee")
                        .removeValue())
                .then(GdxFIRDatabase.inst()
                        .inReference("/employee")
                        .push()
                        .setValue(employee))
                .then(GdxFIRDatabase.inst()
                        .inReference("/employee")
                        .push()
                        .setValue(employee2))
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        GdxFIRDatabase.inst().inReference("/employee")
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
