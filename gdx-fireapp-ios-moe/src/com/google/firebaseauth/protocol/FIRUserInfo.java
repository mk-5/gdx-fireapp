package com.google.firebaseauth.protocol;


import apple.foundation.NSURL;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("FirebaseAuth")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRUserInfo")
public interface FIRUserInfo {
	@Generated
	@Selector("displayName")
	String displayName();

	@Generated
	@Selector("email")
	String email();

	@Generated
	@Selector("photoURL")
	NSURL photoURL();

	@Generated
	@Selector("providerID")
	String providerID();

	@Generated
	@Selector("uid")
	String uid();
}