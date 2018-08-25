package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class GdxFIRDatabaseTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRDatabase instance = GdxFIRDatabase.instance();
        assertTrue(instance != null);
    }


}