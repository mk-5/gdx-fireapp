package bindings.google.crashlytics;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import bindings.google.crashlytics.protocol.CrashlyticsDelegate;
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
@Library("Crashlytics")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class Crashlytics extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected Crashlytics(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("APIKey")
	public native String APIKey();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native Crashlytics alloc();

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
	@Selector("crash")
	public native void crash();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("debugMode")
	public native boolean debugMode();

	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native CrashlyticsDelegate delegate();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native Crashlytics init();

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
	@Deprecated
	@Selector("logEvent:")
	public native void logEvent(String eventName);

	@Generated
	@Deprecated
	@Selector("logEvent:")
	public static native void logEvent_static(String eventName);

	@Generated
	@Deprecated
	@Selector("logEvent:attributes:")
	public native void logEventAttributes(String eventName,
			NSDictionary<?, ?> attributes);

	@Generated
	@Deprecated
	@Selector("logEvent:attributes:")
	public static native void logEventAttributes_static(String eventName,
			NSDictionary<?, ?> attributes);

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("recordCustomExceptionName:reason:frameArray:")
	public native void recordCustomExceptionNameReasonFrameArray(String name,
			String reason, NSArray<? extends CLSStackFrame> frameArray);

	@Generated
	@Selector("recordError:")
	public native void recordError(NSError error);

	@Generated
	@Selector("recordError:withAdditionalUserInfo:")
	public native void recordErrorWithAdditionalUserInfo(NSError error,
			NSDictionary<String, ?> userInfo);

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setBoolValue:forKey:")
	public native void setBoolValueForKey(boolean value, String key);

	@Generated
	@Deprecated
	@Selector("setBoolValue:forKey:")
	public static native void setBoolValueForKey_static(boolean value,
			String key);

	@Generated
	@Selector("setDebugMode:")
	public native void setDebugMode(boolean value);

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) CrashlyticsDelegate value);

	@Generated
	public void setDelegate(
			@Mapped(ObjCObjectMapper.class) CrashlyticsDelegate value) {
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
	@Selector("setFloatValue:forKey:")
	public native void setFloatValueForKey(float value, String key);

	@Generated
	@Deprecated
	@Selector("setFloatValue:forKey:")
	public static native void setFloatValueForKey_static(float value, String key);

	@Generated
	@Selector("setIntValue:forKey:")
	public native void setIntValueForKey(int value, String key);

	@Generated
	@Deprecated
	@Selector("setIntValue:forKey:")
	public static native void setIntValueForKey_static(int value, String key);

	@Generated
	@Selector("setObjectValue:forKey:")
	public native void setObjectValueForKey(
			@Mapped(ObjCObjectMapper.class) Object value, String key);

	@Generated
	@Deprecated
	@Selector("setObjectValue:forKey:")
	public static native void setObjectValueForKey_static(
			@Mapped(ObjCObjectMapper.class) Object value, String key);

	@Generated
	@Selector("setUserEmail:")
	public native void setUserEmail(String email);

	@Generated
	@Deprecated
	@Selector("setUserEmail:")
	public static native void setUserEmail_static(String email);

	@Generated
	@Selector("setUserIdentifier:")
	public native void setUserIdentifier(String identifier);

	@Generated
	@Deprecated
	@Selector("setUserIdentifier:")
	public static native void setUserIdentifier_static(String identifier);

	@Generated
	@Selector("setUserName:")
	public native void setUserName(String name);

	@Generated
	@Deprecated
	@Selector("setUserName:")
	public static native void setUserName_static(String name);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("sharedInstance")
	public static native Crashlytics sharedInstance();

	@Generated
	@Selector("startWithAPIKey:")
	public static native Crashlytics startWithAPIKey(String apiKey);

	@Generated
	@Deprecated
	@Selector("startWithAPIKey:afterDelay:")
	public static native Crashlytics startWithAPIKeyAfterDelay(String apiKey,
			double delay);

	@Generated
	@Selector("startWithAPIKey:delegate:")
	public static native Crashlytics startWithAPIKeyDelegate(String apiKey,
			@Mapped(ObjCObjectMapper.class) CrashlyticsDelegate delegate);

	@Generated
	@Deprecated
	@Selector("startWithAPIKey:delegate:afterDelay:")
	public static native Crashlytics startWithAPIKeyDelegateAfterDelay(
			String apiKey,
			@Mapped(ObjCObjectMapper.class) CrashlyticsDelegate delegate,
			double delay);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("throwException")
	public native void throwException();

	@Generated
	@Selector("version")
	public native String version();
}