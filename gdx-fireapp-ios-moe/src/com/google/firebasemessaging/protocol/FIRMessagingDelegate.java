package com.google.firebasemessaging.protocol;


import com.google.firebasemessaging.FIRMessagingRemoteMessage;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("FirebaseMessaging")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRMessagingDelegate")
public interface FIRMessagingDelegate {
	@Generated
	@Selector("applicationReceivedRemoteMessage:")
	void applicationReceivedRemoteMessage(
			FIRMessagingRemoteMessage remoteMessage);
}