package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GdxFIRCrashTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRCrash instance = GdxFIRCrash.instance();
        assertTrue(instance != null);
    }


}