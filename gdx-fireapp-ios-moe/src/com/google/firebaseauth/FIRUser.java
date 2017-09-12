package com.google.firebaseauth;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.NSURL;
import com.google.firebaseauth.protocol.FIRUserInfo;
import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("FirebaseAuth")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIRUser extends NSObject implements FIRUserInfo {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRUser(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRUser alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:")
	public static native void cancelPreviousPerformRequestsWithTarget(
			@Mapped(ObjCObjectMapper.class) Object aTarget);

	@Generated
	@Selector("cancelPreviousPerformRequestsWithTarget:selector:object:")
	public static native void cancelPreviousPerformRequestsWithTargetSelectorObject(
			@Mapped(ObjCObjectMapper.class) Object aTarget, SEL aSelector,
			@Mapped(ObjCObjectMapper.class) Object anArgument);

	@Generated
	@Selector("class")
	public static native Class class_objc_static();

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("deleteWithCompletion:")
	public native void deleteWithCompletion(
			@ObjCBlock(name = "call_deleteWithCompletion") Block_deleteWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_deleteWithCompletion {
		@Generated
		void call_deleteWithCompletion(NSError arg0);
	}

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("displayName")
	public native String displayName();

	@Generated
	@Selector("email")
	public native String email();

	@Generated
	@Selector("getTokenForcingRefresh:completion:")
	public native void getTokenForcingRefreshCompletion(
			boolean forceRefresh,
			@ObjCBlock(name = "call_getTokenForcingRefreshCompletion") Block_getTokenForcingRefreshCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_getTokenForcingRefreshCompletion {
		@Generated
		void call_getTokenForcingRefreshCompletion(String arg0, NSError arg1);
	}

	@Generated
	@Selector("getTokenWithCompletion:")
	public native void getTokenWithCompletion(
			@ObjCBlock(name = "call_getTokenWithCompletion") Block_getTokenWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_getTokenWithCompletion {
		@Generated
		void call_getTokenWithCompletion(String arg0, NSError arg1);
	}

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRUser init();

	@Generated
	@Selector("initialize")
	public static native void initialize();

	@Generated
	@Selector("instanceMethodForSelector:")
	@FunctionPtr(name = "call_instanceMethodForSelector_ret")
	public static native NSObject.Function_instanceMethodForSelector_ret instanceMethodForSelector(
			SEL aSelector);

	@Generated
	@Selector("instanceMethodSignatureForSelector:")
	public static native NSMethodSignature instanceMethodSignatureForSelector(
			SEL aSelector);

	@Generated
	@Selector("instancesRespondToSelector:")
	public static native boolean instancesRespondToSelector(SEL aSelector);

	@Generated
	@Selector("isAnonymous")
	public native boolean isAnonymous();

	@Generated
	@Selector("isEmailVerified")
	public native boolean isEmailVerified();

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("linkWithCredential:completion:")
	public native void linkWithCredentialCompletion(
			FIRAuthCredential credential,
			@ObjCBlock(name = "call_linkWithCredentialCompletion") Block_linkWithCredentialCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_linkWithCredentialCompletion {
		@Generated
		void call_linkWithCredentialCompletion(FIRUser arg0, NSError arg1);
	}

	@Generated
	@Selector("load")
	public static native void load_objc_static();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("photoURL")
	public native NSURL photoURL();

	@Generated
	@Selector("profileChangeRequest")
	public native FIRUserProfileChangeRequest profileChangeRequest();

	@Generated
	@Selector("providerData")
	public native NSArray<?> providerData();

	@Generated
	@Selector("providerID")
	public native String providerID();

	@Generated
	@Selector("reauthenticateWithCredential:completion:")
	public native void reauthenticateWithCredentialCompletion(
			FIRAuthCredential credential,
			@ObjCBlock(name = "call_reauthenticateWithCredentialCompletion") Block_reauthenticateWithCredentialCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_reauthenticateWithCredentialCompletion {
		@Generated
		void call_reauthenticateWithCredentialCompletion(NSError arg0);
	}

	@Generated
	@Selector("refreshToken")
	public native String refreshToken();

	@Generated
	@Selector("reloadWithCompletion:")
	public native void reloadWithCompletion(
			@ObjCBlock(name = "call_reloadWithCompletion") Block_reloadWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_reloadWithCompletion {
		@Generated
		void call_reloadWithCompletion(NSError arg0);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("sendEmailVerificationWithCompletion:")
	public native void sendEmailVerificationWithCompletion(
			@ObjCBlock(name = "call_sendEmailVerificationWithCompletion") Block_sendEmailVerificationWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_sendEmailVerificationWithCompletion {
		@Generated
		void call_sendEmailVerificationWithCompletion(NSError arg0);
	}

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("uid")
	public native String uid();

	@Generated
	@Selector("unlinkFromProvider:completion:")
	public native void unlinkFromProviderCompletion(
			String provider,
			@ObjCBlock(name = "call_unlinkFromProviderCompletion") Block_unlinkFromProviderCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_unlinkFromProviderCompletion {
		@Generated
		void call_unlinkFromProviderCompletion(FIRUser arg0, NSError arg1);
	}

	@Generated
	@Selector("updateEmail:completion:")
	public native void updateEmailCompletion(
			String email,
			@ObjCBlock(name = "call_updateEmailCompletion") Block_updateEmailCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updateEmailCompletion {
		@Generated
		void call_updateEmailCompletion(NSError arg0);
	}

	@Generated
	@Selector("updatePassword:completion:")
	public native void updatePasswordCompletion(
			String password,
			@ObjCBlock(name = "call_updatePasswordCompletion") Block_updatePasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updatePasswordCompletion {
		@Generated
		void call_updatePasswordCompletion(NSError arg0);
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}