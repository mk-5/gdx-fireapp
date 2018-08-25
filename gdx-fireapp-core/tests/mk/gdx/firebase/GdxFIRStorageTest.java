package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GdxFIRStorageTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRStorage instance = GdxFIRStorage.instance();
        assertTrue(instance != null);
    }

}