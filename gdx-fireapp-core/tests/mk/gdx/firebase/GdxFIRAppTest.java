package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import mk.gdx.firebase.distributions.AppDistribution;

import static org.junit.Assert.assertNotNull;

public class GdxFIRAppTest extends GdxAppTest {

    @Mock
    private AppDistribution appDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRApp.instance().platformObject = appDistribution;
    }

    @Test
    public void instance() throws Exception {
        GdxFIRApp instance = GdxFIRApp.instance();
        assertNotNull(instance);
    }

    @Test
    public void configure() {
        // Given
        // When
        GdxFIRApp.instance().configure();

        // Then
        Mockito.verify(appDistribution, VerificationModeFactory.times(1)).configure();
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals(GdxFIRApp.instance().getIOSClassName(), "mk.gdx.firebase.ios.App");
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals(GdxFIRApp.instance().getAndroidClassName(), "mk.gdx.firebase.android.App");
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals(GdxFIRApp.instance().getWebGLClassName(), "mk.gdx.firebase.html.App");
    }
}