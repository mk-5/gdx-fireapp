package bindings.google.firebasemessaging.protocol;


import bindings.google.firebasemessaging.FIRMessaging;
import bindings.google.firebasemessaging.FIRMessagingRemoteMessage;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRMessagingDelegate")
public interface FIRMessagingDelegate {
	@Generated
	@IsOptional
	@Selector("messaging:didReceiveMessage:")
	default void messagingDidReceiveMessage(FIRMessaging messaging,
			FIRMessagingRemoteMessage remoteMessage) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("messaging:didReceiveRegistrationToken:")
	default void messagingDidReceiveRegistrationToken(FIRMessaging messaging,
			String fcmToken) {
		throw new java.lang.UnsupportedOperationException();
	}
}