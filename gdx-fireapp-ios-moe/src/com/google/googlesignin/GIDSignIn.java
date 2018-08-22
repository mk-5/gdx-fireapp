package com.google.googlesignin;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.NSURL;
import com.google.googlesignin.protocol.GIDSignInDelegate;
import com.google.googlesignin.protocol.GIDSignInUIDelegate;
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
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("GoogleSignIn")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class GIDSignIn extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected GIDSignIn(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native GIDSignIn alloc();

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
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("clientID")
	public native String clientID();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native GIDSignInDelegate delegate();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("disconnect")
	public native void disconnect();

	@Generated
	@Selector("handleURL:sourceApplication:annotation:")
	public native boolean handleURLSourceApplicationAnnotation(NSURL url,
			String sourceApplication,
			@Mapped(ObjCObjectMapper.class) Object annotation);

	@Generated
	@Selector("hasAuthInKeychain")
	public native boolean hasAuthInKeychain();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("hostedDomain")
	public native String hostedDomain();

	@Generated
	@Selector("init")
	public native GIDSignIn init();

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
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("language")
	public native String language();

	@Generated
	@Selector("loginHint")
	public native String loginHint();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("openIDRealm")
	public native String openIDRealm();

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("scopes")
	public native NSArray<?> scopes();

	@Generated
	@Selector("serverClientID")
	public native String serverClientID();

	@Generated
	@Selector("setClientID:")
	public native void setClientID(String value);

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) GIDSignInDelegate value);

	@Generated
	public void setDelegate(
			@Mapped(ObjCObjectMapper.class) GIDSignInDelegate value) {
		Object __old = delegate();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setDelegate_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setHostedDomain:")
	public native void setHostedDomain(String value);

	@Generated
	@Selector("setLanguage:")
	public native void setLanguage(String value);

	@Generated
	@Selector("setLoginHint:")
	public native void setLoginHint(String value);

	@Generated
	@Selector("setOpenIDRealm:")
	public native void setOpenIDRealm(String value);

	@Generated
	@Selector("setScopes:")
	public native void setScopes(NSArray<?> value);

	@Generated
	@Selector("setServerClientID:")
	public native void setServerClientID(String value);

	@Generated
	@Selector("setShouldFetchBasicProfile:")
	public native void setShouldFetchBasicProfile(boolean value);

	@Generated
	@Selector("setUiDelegate:")
	public native void setUiDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) GIDSignInUIDelegate value);

	@Generated
	public void setUiDelegate(
			@Mapped(ObjCObjectMapper.class) GIDSignInUIDelegate value) {
		Object __old = uiDelegate();
		if (value != null) {
			org.moe.natj.objc.ObjCRuntime.associateObjCObject(this, value);
		}
		setUiDelegate_unsafe(value);
		if (__old != null) {
			org.moe.natj.objc.ObjCRuntime.dissociateObjCObject(this, __old);
		}
	}

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("sharedInstance")
	public static native GIDSignIn sharedInstance();

	@Generated
	@Selector("shouldFetchBasicProfile")
	public native boolean shouldFetchBasicProfile();

	@Generated
	@Selector("signIn")
	public native void signIn();

	@Generated
	@Selector("signInSilently")
	public native void signInSilently();

	@Generated
	@Selector("signOut")
	public native void signOut();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("uiDelegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native GIDSignInUIDelegate uiDelegate();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();

	@Generated
	@Selector("currentUser")
	public native GIDGoogleUser currentUser();
}