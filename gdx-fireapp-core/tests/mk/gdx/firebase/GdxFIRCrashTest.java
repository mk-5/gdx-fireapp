package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import mk.gdx.firebase.distributions.CrashDistribution;

import static org.junit.Assert.assertNotNull;

public class GdxFIRCrashTest extends GdxAppTest {

    @Mock
    private CrashDistribution crashDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRCrash.instance().platformObject = crashDistribution;
    }

    @Test
    public void instance() throws Exception {
        assertNotNull(GdxFIRCrash.instance());
    }

    @Test
    public void log() {
        // Given
        // When
        GdxFIRCrash.instance().log("abc");

        // Then
        Mockito.verify(crashDistribution, VerificationModeFactory.times(1)).log(Mockito.eq("abc"));
    }

    @Test
    public void initialize() {
        // Given
        // When
        GdxFIRCrash.instance().initialize();

        // Then
        Mockito.verify(crashDistribution, VerificationModeFactory.times(1)).initialize();
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals(GdxFIRCrash.instance().getIOSClassName(), "mk.gdx.firebase.ios.crash.Crash");
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals(GdxFIRCrash.instance().getAndroidClassName(), "mk.gdx.firebase.android.crash.Crash");
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals(GdxFIRCrash.instance().getWebGLClassName(), "mk.gdx.firebase.html.crash.Crash");
    }
}