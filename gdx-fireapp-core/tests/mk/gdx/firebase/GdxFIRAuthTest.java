package mk.gdx.firebase;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import mk.gdx.firebase.callbacks.AuthCallback;
import mk.gdx.firebase.callbacks.CompleteCallback;
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
        GdxFIRAuth.instance().createUserWithEmailAndPassword("user", "password".toCharArray());

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).createUserWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password".toCharArray()));
    }

    @Test
    public void signInWithEmailAndPassword() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInWithEmailAndPassword("user", "password".toCharArray());

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInWithEmailAndPassword(Mockito.eq("user"), Mockito.eq("password".toCharArray()));
    }

    @Test
    public void signInWithToken() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInWithToken("token");

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInWithToken(Mockito.eq("token"));
    }

    @Test
    public void signInAnonymously() {
        // Given
        AuthCallback callback = Mockito.mock(AuthCallback.class);

        // When
        GdxFIRAuth.instance().signInAnonymously();

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signInAnonymously();
    }

    @Test
    public void sendPasswordResetEmail() {
        // Given
        CompleteCallback callback = Mockito.mock(CompleteCallback.class);
        String arg1 = "email";

        // When
        GdxFIRAuth.instance().sendPasswordResetEmail(arg1);

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).sendPasswordResetEmail(Mockito.eq(arg1));
    }

    @Test
    public void signOut() {
        // Given
        SignOutCallback callback = Mockito.mock(SignOutCallback.class);

        // When
        GdxFIRAuth.instance().signOut();

        // Then
        Mockito.verify(authDistribution, VerificationModeFactory.times(1)).signOut();
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