package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.SignOutCallback;
import mk.gdx.firebase.distributions.AuthDistribution;

import static org.junit.Assert.assertNotNull;

public class GdxFIRAuthTest extends GdxAppTest {

    @Mock
    private AuthDistribution authDistribution;

    @Override
    public void setup() {
        super.setup();
        GdxFIRAuth.instance().platformObject = authDistribution;
    }

    @Test
    public void instance() throws Exception {
        assertNotNull(GdxFIRAuth.instance());
    }

    @Test
    public void google() {
        assertNotNull(GdxFIRAuth.instance().google());
    }

    @Test
    public void getCurrentUser() {
    }

    @Test
    public void createUserWithEmailAndPassword() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().createUserWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password".toCharArray()), Mockito.refEq(callback));
    }

    @Test
    public void signInWithEmailAndPassword() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInWithEmailAndPassword("user", "password".toCharArray(), callback);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password".toCharArray()), Mockito.refEq(callback));
    }

    @Test
    public void signInWithToken() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInWithToken("token", callback);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInWithToken(Mockito.eq("token"), Mockito.refEq(callback));
    }

    @Test
    public void signInAnonymously() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInAnonymously(callback);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInAnonymously(Mockito.refEq(callback));
    }

    @Test
    public void signOut() {
        // Given
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);

        // When
        GdxFIRAuth.instance().signOut(callback);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signOut(Mockito.refEq(callback));
    }

    @Test
    public void getIOSClassName() {
        Assert.assertEquals("mk.gdx.firebase.ios.auth.Auth", GdxFIRAuth.instance().getIOSClassName());
    }

    @Test
    public void getAndroidClassName() {
        Assert.assertEquals("mk.gdx.firebase.android.auth.Auth", GdxFIRAuth.instance().getAndroidClassName());
    }

    @Test
    public void getWebGLClassName() {
        Assert.assertEquals("mk.gdx.firebase.html.auth.Auth", GdxFIRAuth.instance().getWebGLClassName());
    }
}