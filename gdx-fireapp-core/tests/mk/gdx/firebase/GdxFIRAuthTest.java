package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GdxFIRAuthTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRAuth instance = GdxFIRAuth.instance();
        assertTrue(instance != null);
    }

}