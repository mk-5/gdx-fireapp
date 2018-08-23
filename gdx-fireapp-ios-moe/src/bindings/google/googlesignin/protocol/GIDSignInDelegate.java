package bindings.google.googlesignin.protocol;


import apple.foundation.NSError;
import bindings.google.googlesignin.GIDGoogleUser;
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
@ObjCProtocolName("GIDSignInDelegate")
public interface GIDSignInDelegate {
	@Generated
	@IsOptional
	@Selector("signIn:didDisconnectWithUser:withError:")
	default void signInDidDisconnectWithUserWithError(GIDSignIn signIn,
			GIDGoogleUser user, NSError error) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@Selector("signIn:didSignInForUser:withError:")
	void signInDidSignInForUserWithError(GIDSignIn signIn, GIDGoogleUser user,
			NSError error);
}