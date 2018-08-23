package bindings.google.googlesignin;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSCoder;
import apple.foundation.NSDate;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.protocol.NSCoding;
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
@Library("GoogleSignIn")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class GIDAuthentication extends NSObject implements NSCoding {
	static {
		NatJ.register();
	}

	@Generated
	protected GIDAuthentication(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Selector("accessToken")
	public native String accessToken();

	@Generated
	@Selector("accessTokenExpirationDate")
	public native NSDate accessTokenExpirationDate();

	@Generated
	@Owned
	@Selector("alloc")
	public static native GIDAuthentication alloc();

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
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("encodeWithCoder:")
	public native void encodeWithCoder(NSCoder aCoder);

	@Generated
	@Selector("fetcherAuthorizer")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object fetcherAuthorizer();

	@Generated
	@Deprecated
	@Selector("getAccessTokenWithHandler:")
	public native void getAccessTokenWithHandler(
			@ObjCBlock(name = "call_getAccessTokenWithHandler") Block_getAccessTokenWithHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_getAccessTokenWithHandler {
		@Generated
		void call_getAccessTokenWithHandler(String arg0, NSError arg1);
	}

	@Generated
	@Selector("getTokensWithHandler:")
	public native void getTokensWithHandler(
			@ObjCBlock(name = "call_getTokensWithHandler") Block_getTokensWithHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_getTokensWithHandler {
		@Generated
		void call_getTokensWithHandler(GIDAuthentication arg0, NSError arg1);
	}

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("idToken")
	public native String idToken();

	@Generated
	@Selector("idTokenExpirationDate")
	public native NSDate idTokenExpirationDate();

	@Generated
	@Selector("init")
	public native GIDAuthentication init();

	@Generated
	@Selector("initWithCoder:")
	public native GIDAuthentication initWithCoder(NSCoder aDecoder);

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
	@Deprecated
	@Selector("refreshAccessTokenWithHandler:")
	public native void refreshAccessTokenWithHandler(
			@ObjCBlock(name = "call_refreshAccessTokenWithHandler") Block_refreshAccessTokenWithHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_refreshAccessTokenWithHandler {
		@Generated
		void call_refreshAccessTokenWithHandler(String arg0, NSError arg1);
	}

	@Generated
	@Selector("refreshToken")
	public native String refreshToken();

	@Generated
	@Selector("refreshTokensWithHandler:")
	public native void refreshTokensWithHandler(
			@ObjCBlock(name = "call_refreshTokensWithHandler") Block_refreshTokensWithHandler handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_refreshTokensWithHandler {
		@Generated
		void call_refreshTokensWithHandler(GIDAuthentication arg0, NSError arg1);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}