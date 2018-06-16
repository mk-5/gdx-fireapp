package com.google.firebasecore;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.protocol.NSCopying;
import org.moe.natj.c.ann.FunctionPtr;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
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
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIROptions extends NSObject implements NSCopying {
	static {
		NatJ.register();
	}

	@Generated
	protected FIROptions(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("APIKey")
	public native String APIKey();

	@Generated
	@Selector("GCMSenderID")
	public native String GCMSenderID();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIROptions alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("androidClientID")
	public native String androidClientID();

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("bundleID")
	public native String bundleID();

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
	@Owned
	@Selector("copyWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object copyWithZone(VoidPtr zone);

	@Generated
	@Selector("databaseURL")
	public native String databaseURL();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("deepLinkURLScheme")
	public native String deepLinkURLScheme();

	@Generated
	@Selector("defaultOptions")
	public static native FIROptions defaultOptions();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("googleAppID")
	public native String googleAppID();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIROptions init();

	@Generated
	@Selector("initWithContentsOfFile:")
	public native FIROptions initWithContentsOfFile(String plistPath);

	@Generated
	@Selector("initWithGoogleAppID:GCMSenderID:")
	public native FIROptions initWithGoogleAppIDGCMSenderID(String googleAppID,
			String GCMSenderID);

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
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("projectID")
	public native String projectID();

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setAPIKey:")
	public native void setAPIKey(String value);

	@Generated
	@Selector("setAndroidClientID:")
	public native void setAndroidClientID(String value);

	@Generated
	@Selector("setBundleID:")
	public native void setBundleID(String value);

	@Generated
	@Selector("setClientID:")
	public native void setClientID(String value);

	@Generated
	@Selector("setDatabaseURL:")
	public native void setDatabaseURL(String value);

	@Generated
	@Selector("setDeepLinkURLScheme:")
	public native void setDeepLinkURLScheme(String value);

	@Generated
	@Selector("setGCMSenderID:")
	public native void setGCMSenderID(String value);

	@Generated
	@Selector("setGoogleAppID:")
	public native void setGoogleAppID(String value);

	@Generated
	@Selector("setProjectID:")
	public native void setProjectID(String value);

	@Generated
	@Selector("setStorageBucket:")
	public native void setStorageBucket(String value);

	@Generated
	@Selector("setTrackingID:")
	public native void setTrackingID(String value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("storageBucket")
	public native String storageBucket();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("trackingID")
	public native String trackingID();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}