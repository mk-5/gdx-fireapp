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
    public void instance() {
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
        Assert.assertEquals("mk.gdx.firebase.ios.App", GdxFIRApp.instance().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.App", GdxFIRApp.instance().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.App", GdxFIRApp.instance().getWebGLClassName());
    }
}