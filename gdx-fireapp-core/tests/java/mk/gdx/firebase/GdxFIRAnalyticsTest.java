package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import java.util.HashMap;

import mk.gdx.firebase.distributions.AnalyticsDistribution;

import static org.junit.Assert.assertNotNull;


public class GdxFIRAnalyticsTest extends GdxAppTest {

    @Mock
    private AnalyticsDistribution analyticsDistribution;

    @Before
    public void setup() {
        super.setup();
        GdxFIRAnalytics.instance().platformObject = analyticsDistribution;
    }

    @Test
    public void instance() {
        GdxFIRAnalytics instance = GdxFIRAnalytics.instance();
        assertNotNull(instance);
    }

    @Test
    public void logEvent() {
        // Given
        HashMap map = new HashMap<String, String>();

        // When
        GdxFIRAnalytics.instance().logEvent("", map);

        // Then
        Mockito.verify(analyticsDistribution, VerificationModeFactory.times(1)).logEvent(Mockito.eq(""), Mockito.refEq(map));
    }

    @Test
    public void setScreen() {
        // Given
        // When
        GdxFIRAnalytics.instance().setScreen("", GdxFIRAnalyticsTest.class);

        // Then
        Mockito.verify(analyticsDistribution, VerificationModeFactory.times(1)).setScreen(Mockito.eq(""), Mockito.eq(GdxFIRAnalyticsTest.class));
    }

    @Test
    public void setUserProperty() {
        // Given
        // When
        GdxFIRAnalytics.instance().setUserProperty("", "value");

        // Then
        Mockito.verify(analyticsDistribution, VerificationModeFactory.times(1)).setUserProperty(Mockito.eq(""), Mockito.eq("value"));
    }

    @Test
    public void setUserId() {
        // Given
        // When
        GdxFIRAnalytics.instance().setUserId("user_id");

        // Then
        Mockito.verify(analyticsDistribution, VerificationModeFactory.times(1)).setUserId(Mockito.eq("user_id"));
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals("mk.gdx.firebase.ios.analytics.Analytics", GdxFIRAnalytics.instance().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.analytics.Analytics", GdxFIRAnalytics.instance().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.analytics.Analytics", GdxFIRAnalytics.instance().getWebGLClassName());
    }
}