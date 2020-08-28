package pl.mk5.gdx.fireapp.e2e.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.List;

import pl.mk5.gdx.fireapp.GdxFIRAuth;
import pl.mk5.gdx.fireapp.GdxFIRDatabase;
import pl.mk5.gdx.fireapp.annotations.MapConversion;
import pl.mk5.gdx.fireapp.database.OrderByMode;
import pl.mk5.gdx.fireapp.e2e.runner.E2ETest;
import pl.mk5.gdx.fireapp.functional.Consumer;

public class DatabaseOrderByChildTest extends E2ETest {

    private BitmapFont font;

    @Override
    public void action() {
        final Employee employee = new Employee("bob", 3000L);
        final Employee employee2 = new Employee("john", 1000L);
        final Employee employee3 = new Employee("fred", 2000L);

        GdxFIRAuth.inst()
                .signInAnonymously()
                .then(GdxFIRDatabase.inst()
                        .inReference("/employee").removeValue())
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
                .then(GdxFIRDatabase.inst()
                        .inReference("/employee")
                        .push()
                        .setValue(employee3))
                .then(new Consumer<Void>() {
                    @Override
                    public void accept(Void aVoid) {
                        GdxFIRDatabase.inst().inReference("/employee")
                                .orderBy(OrderByMode.ORDER_BY_CHILD, "salary")
                                .readValue(List.class)
                                .then(new Consumer<List<Employee>>() {
                                    @Override
                                    @MapConversion(Employee.class)
                                    public void accept(List<Employee> list) {
                                        Gdx.app.log("Test", "list: " + list);
                                        if (list.get(0).name.equals("john") &&
                                                list.get(1).name.equals("fred") &&
                                                list.get(2).name.equals("bob")
                                        ) {
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
