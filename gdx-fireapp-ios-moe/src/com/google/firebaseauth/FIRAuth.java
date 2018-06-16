package com.google.firebaseauth;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSData;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.NSURL;
import com.google.firebasecore.FIRApp;
import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.NInt;
import org.moe.natj.general.ann.NUInt;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.ReferenceInfo;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.Ptr;
import org.moe.natj.general.ptr.VoidPtr;
import org.moe.natj.objc.Class;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.SEL;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIRAuth extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRAuth(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("APNSToken")
	public native NSData APNSToken();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("addAuthStateDidChangeListener:")
	@MappedReturn(ObjCObjectMapper.class)
	public native apple.protocol.NSObject addAuthStateDidChangeListener(
			@ObjCBlock(name = "call_addAuthStateDidChangeListener") Block_addAuthStateDidChangeListener listener);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addAuthStateDidChangeListener {
		@Generated
		void call_addAuthStateDidChangeListener(FIRAuth arg0, FIRUser arg1);
	}

	@Generated
	@Selector("addIDTokenDidChangeListener:")
	@MappedReturn(ObjCObjectMapper.class)
	public native apple.protocol.NSObject addIDTokenDidChangeListener(
			@ObjCBlock(name = "call_addIDTokenDidChangeListener") Block_addIDTokenDidChangeListener listener);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_addIDTokenDidChangeListener {
		@Generated
		void call_addIDTokenDidChangeListener(FIRAuth arg0, FIRUser arg1);
	}

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRAuth alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("app")
	public native FIRApp app();

	@Generated
	@Selector("applyActionCode:completion:")
	public native void applyActionCodeCompletion(
			String code,
			@ObjCBlock(name = "call_applyActionCodeCompletion") Block_applyActionCodeCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_applyActionCodeCompletion {
		@Generated
		void call_applyActionCodeCompletion(NSError arg0);
	}

	@Generated
	@Selector("auth")
	public static native FIRAuth auth();

	@Generated
	@Selector("authWithApp:")
	public static native FIRAuth authWithApp(FIRApp app);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("canHandleNotification:")
	public native boolean canHandleNotification(NSDictionary<?, ?> userInfo);

	@Generated
	@Selector("canHandleURL:")
	public native boolean canHandleURL(NSURL URL);

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
	@Selector("checkActionCode:completion:")
	public native void checkActionCodeCompletion(
			String code,
			@ObjCBlock(name = "call_checkActionCodeCompletion") Block_checkActionCodeCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_checkActionCodeCompletion {
		@Generated
		void call_checkActionCodeCompletion(FIRActionCodeInfo arg0, NSError arg1);
	}

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("confirmPasswordResetWithCode:newPassword:completion:")
	public native void confirmPasswordResetWithCodeNewPasswordCompletion(
			String code,
			String newPassword,
			@ObjCBlock(name = "call_confirmPasswordResetWithCodeNewPasswordCompletion") Block_confirmPasswordResetWithCodeNewPasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_confirmPasswordResetWithCodeNewPasswordCompletion {
		@Generated
		void call_confirmPasswordResetWithCodeNewPasswordCompletion(NSError arg0);
	}

	@Generated
	@Deprecated
	@Selector("createUserAndRetrieveDataWithEmail:password:completion:")
	public native void createUserAndRetrieveDataWithEmailPasswordCompletion(
			String email,
			String password,
			@ObjCBlock(name = "call_createUserAndRetrieveDataWithEmailPasswordCompletion") Block_createUserAndRetrieveDataWithEmailPasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_createUserAndRetrieveDataWithEmailPasswordCompletion {
		@Generated
		void call_createUserAndRetrieveDataWithEmailPasswordCompletion(
				FIRAuthDataResult arg0, NSError arg1);
	}

	@Generated
	@Selector("createUserWithEmail:password:completion:")
	public native void createUserWithEmailPasswordCompletion(
			String email,
			String password,
			@ObjCBlock(name = "call_createUserWithEmailPasswordCompletion") Block_createUserWithEmailPasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_createUserWithEmailPasswordCompletion {
		@Generated
		void call_createUserWithEmailPasswordCompletion(FIRAuthDataResult arg0,
				NSError arg1);
	}

	@Generated
	@Selector("currentUser")
	public native FIRUser currentUser();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("fetchProvidersForEmail:completion:")
	public native void fetchProvidersForEmailCompletion(
			String email,
			@ObjCBlock(name = "call_fetchProvidersForEmailCompletion") Block_fetchProvidersForEmailCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_fetchProvidersForEmailCompletion {
		@Generated
		void call_fetchProvidersForEmailCompletion(NSArray<String> arg0,
				NSError arg1);
	}

	@Generated
	@Selector("fetchSignInMethodsForEmail:completion:")
	public native void fetchSignInMethodsForEmailCompletion(
			String email,
			@ObjCBlock(name = "call_fetchSignInMethodsForEmailCompletion") Block_fetchSignInMethodsForEmailCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_fetchSignInMethodsForEmailCompletion {
		@Generated
		void call_fetchSignInMethodsForEmailCompletion(NSArray<String> arg0,
				NSError arg1);
	}

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRAuth init();

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
	@Selector("isSignInWithEmailLink:")
	public native boolean isSignInWithEmailLink(String link);

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("languageCode")
	public native String languageCode();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("removeAuthStateDidChangeListener:")
	public native void removeAuthStateDidChangeListener(
			@Mapped(ObjCObjectMapper.class) apple.protocol.NSObject listenerHandle);

	@Generated
	@Selector("removeIDTokenDidChangeListener:")
	public native void removeIDTokenDidChangeListener(
			@Mapped(ObjCObjectMapper.class) apple.protocol.NSObject listenerHandle);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("sendPasswordResetWithEmail:actionCodeSettings:completion:")
	public native void sendPasswordResetWithEmailActionCodeSettingsCompletion(
			String email,
			FIRActionCodeSettings actionCodeSettings,
			@ObjCBlock(name = "call_sendPasswordResetWithEmailActionCodeSettingsCompletion") Block_sendPasswordResetWithEmailActionCodeSettingsCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_sendPasswordResetWithEmailActionCodeSettingsCompletion {
		@Generated
		void call_sendPasswordResetWithEmailActionCodeSettingsCompletion(
				NSError arg0);
	}

	@Generated
	@Selector("sendPasswordResetWithEmail:completion:")
	public native void sendPasswordResetWithEmailCompletion(
			String email,
			@ObjCBlock(name = "call_sendPasswordResetWithEmailCompletion") Block_sendPasswordResetWithEmailCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_sendPasswordResetWithEmailCompletion {
		@Generated
		void call_sendPasswordResetWithEmailCompletion(NSError arg0);
	}

	@Generated
	@Selector("sendSignInLinkToEmail:actionCodeSettings:completion:")
	public native void sendSignInLinkToEmailActionCodeSettingsCompletion(
			String email,
			FIRActionCodeSettings actionCodeSettings,
			@ObjCBlock(name = "call_sendSignInLinkToEmailActionCodeSettingsCompletion") Block_sendSignInLinkToEmailActionCodeSettingsCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_sendSignInLinkToEmailActionCodeSettingsCompletion {
		@Generated
		void call_sendSignInLinkToEmailActionCodeSettingsCompletion(NSError arg0);
	}

	@Generated
	@Selector("setAPNSToken:")
	public native void setAPNSToken(NSData value);

	@Generated
	@Selector("setAPNSToken:type:")
	public native void setAPNSTokenType(NSData token, @NInt long type);

	@Generated
	@Selector("setLanguageCode:")
	public native void setLanguageCode(String value);

	@Generated
	@Selector("setSettings:")
	public native void setSettings(FIRAuthSettings value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("settings")
	public native FIRAuthSettings settings();

	@Generated
	@Selector("signInAndRetrieveDataWithCredential:completion:")
	public native void signInAndRetrieveDataWithCredentialCompletion(
			FIRAuthCredential credential,
			@ObjCBlock(name = "call_signInAndRetrieveDataWithCredentialCompletion") Block_signInAndRetrieveDataWithCredentialCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInAndRetrieveDataWithCredentialCompletion {
		@Generated
		void call_signInAndRetrieveDataWithCredentialCompletion(
				FIRAuthDataResult arg0, NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("signInAndRetrieveDataWithCustomToken:completion:")
	public native void signInAndRetrieveDataWithCustomTokenCompletion(
			String token,
			@ObjCBlock(name = "call_signInAndRetrieveDataWithCustomTokenCompletion") Block_signInAndRetrieveDataWithCustomTokenCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInAndRetrieveDataWithCustomTokenCompletion {
		@Generated
		void call_signInAndRetrieveDataWithCustomTokenCompletion(
				FIRAuthDataResult arg0, NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("signInAndRetrieveDataWithEmail:password:completion:")
	public native void signInAndRetrieveDataWithEmailPasswordCompletion(
			String email,
			String password,
			@ObjCBlock(name = "call_signInAndRetrieveDataWithEmailPasswordCompletion") Block_signInAndRetrieveDataWithEmailPasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInAndRetrieveDataWithEmailPasswordCompletion {
		@Generated
		void call_signInAndRetrieveDataWithEmailPasswordCompletion(
				FIRAuthDataResult arg0, NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("signInAnonymouslyAndRetrieveDataWithCompletion:")
	public native void signInAnonymouslyAndRetrieveDataWithCompletion(
			@ObjCBlock(name = "call_signInAnonymouslyAndRetrieveDataWithCompletion") Block_signInAnonymouslyAndRetrieveDataWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInAnonymouslyAndRetrieveDataWithCompletion {
		@Generated
		void call_signInAnonymouslyAndRetrieveDataWithCompletion(
				FIRAuthDataResult arg0, NSError arg1);
	}

	@Generated
	@Selector("signInAnonymouslyWithCompletion:")
	public native void signInAnonymouslyWithCompletion(
			@ObjCBlock(name = "call_signInAnonymouslyWithCompletion") Block_signInAnonymouslyWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInAnonymouslyWithCompletion {
		@Generated
		void call_signInAnonymouslyWithCompletion(FIRAuthDataResult arg0,
				NSError arg1);
	}

	@Generated
	@Deprecated
	@Selector("signInWithCredential:completion:")
	public native void signInWithCredentialCompletion(
			FIRAuthCredential credential,
			@ObjCBlock(name = "call_signInWithCredentialCompletion") Block_signInWithCredentialCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInWithCredentialCompletion {
		@Generated
		void call_signInWithCredentialCompletion(FIRUser arg0, NSError arg1);
	}

	@Generated
	@Selector("signInWithCustomToken:completion:")
	public native void signInWithCustomTokenCompletion(
			String token,
			@ObjCBlock(name = "call_signInWithCustomTokenCompletion") Block_signInWithCustomTokenCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInWithCustomTokenCompletion {
		@Generated
		void call_signInWithCustomTokenCompletion(FIRAuthDataResult arg0,
				NSError arg1);
	}

	@Generated
	@Selector("signInWithEmail:link:completion:")
	public native void signInWithEmailLinkCompletion(
			String email,
			String link,
			@ObjCBlock(name = "call_signInWithEmailLinkCompletion") Block_signInWithEmailLinkCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInWithEmailLinkCompletion {
		@Generated
		void call_signInWithEmailLinkCompletion(FIRAuthDataResult arg0,
				NSError arg1);
	}

	@Generated
	@Selector("signInWithEmail:password:completion:")
	public native void signInWithEmailPasswordCompletion(
			String email,
			String password,
			@ObjCBlock(name = "call_signInWithEmailPasswordCompletion") Block_signInWithEmailPasswordCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_signInWithEmailPasswordCompletion {
		@Generated
		void call_signInWithEmailPasswordCompletion(FIRAuthDataResult arg0,
				NSError arg1);
	}

	@Generated
	@Selector("signOut:")
	public native boolean signOut(
			@ReferenceInfo(type = NSError.class) Ptr<NSError> error);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("updateCurrentUser:completion:")
	public native void updateCurrentUserCompletion(
			FIRUser user,
			@ObjCBlock(name = "call_updateCurrentUserCompletion") Block_updateCurrentUserCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updateCurrentUserCompletion {
		@Generated
		void call_updateCurrentUserCompletion(NSError arg0);
	}

	@Generated
	@Selector("useAppLanguage")
	public native void useAppLanguage();

	@Generated
	@Selector("verifyPasswordResetCode:completion:")
	public native void verifyPasswordResetCodeCompletion(
			String code,
			@ObjCBlock(name = "call_verifyPasswordResetCodeCompletion") Block_verifyPasswordResetCodeCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_verifyPasswordResetCodeCompletion {
		@Generated
		void call_verifyPasswordResetCodeCompletion(String arg0, NSError arg1);
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}