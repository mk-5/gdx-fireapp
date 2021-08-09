package pl.mk5.gdx.fireapp.ios.auth;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.iosrobovm.IOSApplication;

import org.robovm.apple.authservices.ASAuthorizationController;
import org.robovm.apple.authservices.ASAuthorizationControllerPresentationContextProviding;
import org.robovm.apple.authservices.ASAuthorizationControllerPresentationContextProvidingAdapter;
import org.robovm.apple.uikit.UIWindow;

class ApplePresentationContextProvider extends ASAuthorizationControllerPresentationContextProvidingAdapter {
    @Override
    public UIWindow presentationAnchorForAuthorizationController(ASAuthorizationController asAuthorizationController) {
        return ((IOSApplication) Gdx.app).getUIWindow();
    }
}
