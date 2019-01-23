/*
 * Copyright 2018 mk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mk.gdx.firebase.ios.auth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosmoe.IOSApplication;
import com.badlogic.gdx.utils.Array;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.moe.natj.general.NatJ;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.reflect.Whitebox;

import apple.uikit.UITableViewController;
import apple.uikit.UIViewController;
import bindings.google.firebasecore.FIRApp;
import bindings.google.firebasecore.FIROptions;
import bindings.google.googlesignin.GIDSignIn;
import bindings.google.googlesignin.protocol.GIDSignInDelegate;
import bindings.google.googlesignin.protocol.GIDSignInUIDelegate;
import mk.gdx.firebase.ios.GdxIOSAppTest;
import mk.gdx.firebase.promises.FuturePromise;

@PrepareForTest({NatJ.class, GIDSignIn.class, FIRApp.class,
        FIROptions.class, UITableViewController.class, GIDSignInUIDelegate.class,
        GIDSignInDelegate.class,
        GoogleAuthProvider.GIDSignDelegate.class, GoogleAuthProvider.GIDViewController.class
})
public class GoogleAuthProviderTest extends GdxIOSAppTest {

    private GIDSignIn gidSignIn;

    @Override
    public void setup() {
        super.setup();
        PowerMockito.mockStatic(GIDSignIn.class);
        PowerMockito.mockStatic(UITableViewController.class);
        PowerMockito.mockStatic(GIDSignInUIDelegate.class);
        PowerMockito.mockStatic(GIDSignInDelegate.class);
        PowerMockito.mockStatic(GoogleAuthProvider.GIDViewController.class);
        PowerMockito.mockStatic(GoogleAuthProvider.GIDSignDelegate.class);
        PowerMockito.mockStatic(FIRApp.class);
        PowerMockito.mockStatic(FIROptions.class);
        gidSignIn = PowerMockito.mock(GIDSignIn.class);
        Mockito.when(GIDSignIn.sharedInstance()).thenReturn(gidSignIn);
        FIRApp firApp = PowerMockito.mock(FIRApp.class);
        Mockito.when(firApp.options()).thenReturn(PowerMockito.mock(FIROptions.class));
        Mockito.when(FIRApp.defaultApp()).thenReturn(firApp);
        Mockito.when(GoogleAuthProvider.GIDViewController.alloc()).thenReturn(PowerMockito.mock(GoogleAuthProvider.GIDViewController.class));
    }

    @Test
    public void isInitialized() {
        // Given
        GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();

        // When
        boolean defaultValue = googleAuthProvider.isInitialized();

        // Then
        Assert.assertFalse(defaultValue);
    }

    @Test
    public void initializeOnce() {
        // Given
        GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();
        Mockito.when(((IOSApplication) Gdx.app).getUIViewController()).thenReturn(Mockito.mock(UIViewController.class));

        // When
        googleAuthProvider.initializeOnce();
        googleAuthProvider.initializeOnce();
        googleAuthProvider.initializeOnce();

        // Then
        PowerMockito.verifyStatic(FIRApp.class, VerificationModeFactory.times(1));
        FIRApp.defaultApp();
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).setClientID(Mockito.nullable(String.class));
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).setDelegate(Mockito.any(GIDSignInDelegate.class));
        Mockito.verify(gidSignIn, VerificationModeFactory.times(1)).setUiDelegate(Mockito.nullable(GIDSignInUIDelegate.class));

    }

    @Test
    public void addSignInCallback() {
        // Given
        GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();
        FuturePromise promise = Mockito.mock(FuturePromise.class);

        // When
        googleAuthProvider.addSignInPromise(promise);

        // Then
        Assert.assertTrue(((Array) Whitebox.getInternalState(googleAuthProvider, "signInPromises")).contains(promise, true));
    }

    @Test
    public void addDisconnectCallback() {
        // Given
        GoogleAuthProvider googleAuthProvider = new GoogleAuthProvider();
        FuturePromise promise = Mockito.mock(FuturePromise.class);

        // When
        googleAuthProvider.addDisconnectPromise(promise);

        // Then
        Assert.assertTrue(((Array) Whitebox.getInternalState(googleAuthProvider, "disconnectPromises")).contains(promise, true));
    }
}