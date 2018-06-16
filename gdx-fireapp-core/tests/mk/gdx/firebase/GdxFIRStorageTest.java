package mk.gdx.firebase;

import com.badlogic.gdx.Gdx;

import org.junit.Before;
import org.junit.Test;

import mk.gdx.firebase.utils.TestApp;

import static org.junit.Assert.assertTrue;

public class GdxFIRStorageTest {

    @Before
    public void setUp() throws Exception {
        Gdx.app = new TestApp();
    }

    @Test
    public void instance() throws Exception {
        GdxFIRStorage instance = GdxFIRStorage.instance();
        assertTrue(instance != null);
    }

}