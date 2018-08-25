package mk.gdx.firebase;

import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class GdxFIRAnalyticsTest extends GdxAppTest {

    @Test
    public void instance() throws Exception {
        GdxFIRAnalytics instance = GdxFIRAnalytics.instance();
        assertTrue(instance != null);
    }

}