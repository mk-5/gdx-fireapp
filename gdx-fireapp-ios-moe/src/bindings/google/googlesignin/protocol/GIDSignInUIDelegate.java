package bindings.google.googlesignin.protocol;


import apple.foundation.NSError;
import apple.uikit.UIViewController;
import bindings.google.googlesignin.GIDSignIn;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("GoogleSignIn")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("GIDSignInUIDelegate")
public interface GIDSignInUIDelegate {
	@Generated
	@IsOptional
	@Selector("signIn:dismissViewController:")
	default void signInDismissViewController(GIDSignIn signIn,
			UIViewController viewController) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("signIn:presentViewController:")
	default void signInPresentViewController(GIDSignIn signIn,
			UIViewController viewController) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("signInWillDispatch:error:")
	default void signInWillDispatchError(GIDSignIn signIn, NSError error) {
		throw new java.lang.UnsupportedOperationException();
	}
}