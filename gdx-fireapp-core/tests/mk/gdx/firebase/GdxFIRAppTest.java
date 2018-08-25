package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GdxFIRAppTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRApp instance = GdxFIRApp.instance();
        assertTrue(instance != null);
    }

}