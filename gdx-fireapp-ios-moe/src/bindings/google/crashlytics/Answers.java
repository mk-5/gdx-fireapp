package bindings.google.crashlytics;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDecimalNumber;
import apple.foundation.NSDictionary;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSNumber;
import apple.foundation.NSSet;
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
public class Answers extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected Answers(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native Answers alloc();

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
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native Answers init();

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
	@Selector("logAddToCartWithPrice:currency:itemName:itemType:itemId:customAttributes:")
	public static native void logAddToCartWithPriceCurrencyItemNameItemTypeItemIdCustomAttributes(
			NSDecimalNumber itemPriceOrNil, String currencyOrNil,
			String itemNameOrNil, String itemTypeOrNil, String itemIdOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logContentViewWithName:contentType:contentId:customAttributes:")
	public static native void logContentViewWithNameContentTypeContentIdCustomAttributes(
			String contentNameOrNil, String contentTypeOrNil,
			String contentIdOrNil, NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logCustomEventWithName:customAttributes:")
	public static native void logCustomEventWithNameCustomAttributes(
			String eventName, NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logInviteWithMethod:customAttributes:")
	public static native void logInviteWithMethodCustomAttributes(
			String inviteMethodOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logLevelEnd:score:success:customAttributes:")
	public static native void logLevelEndScoreSuccessCustomAttributes(
			String levelNameOrNil, NSNumber scoreOrNil,
			NSNumber levelCompletedSuccesfullyOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logLevelStart:customAttributes:")
	public static native void logLevelStartCustomAttributes(
			String levelNameOrNil, NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logLoginWithMethod:success:customAttributes:")
	public static native void logLoginWithMethodSuccessCustomAttributes(
			String loginMethodOrNil, NSNumber loginSucceededOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logPurchaseWithPrice:currency:success:itemName:itemType:itemId:customAttributes:")
	public static native void logPurchaseWithPriceCurrencySuccessItemNameItemTypeItemIdCustomAttributes(
			NSDecimalNumber itemPriceOrNil, String currencyOrNil,
			NSNumber purchaseSucceededOrNil, String itemNameOrNil,
			String itemTypeOrNil, String itemIdOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logRating:contentName:contentType:contentId:customAttributes:")
	public static native void logRatingContentNameContentTypeContentIdCustomAttributes(
			NSNumber ratingOrNil, String contentNameOrNil,
			String contentTypeOrNil, String contentIdOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logSearchWithQuery:customAttributes:")
	public static native void logSearchWithQueryCustomAttributes(
			String queryOrNil, NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logShareWithMethod:contentName:contentType:contentId:customAttributes:")
	public static native void logShareWithMethodContentNameContentTypeContentIdCustomAttributes(
			String shareMethodOrNil, String contentNameOrNil,
			String contentTypeOrNil, String contentIdOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logSignUpWithMethod:success:customAttributes:")
	public static native void logSignUpWithMethodSuccessCustomAttributes(
			String signUpMethodOrNil, NSNumber signUpSucceededOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Selector("logStartCheckoutWithPrice:currency:itemCount:customAttributes:")
	public static native void logStartCheckoutWithPriceCurrencyItemCountCustomAttributes(
			NSDecimalNumber totalPriceOrNil, String currencyOrNil,
			NSNumber itemCountOrNil,
			NSDictionary<String, ?> customAttributesOrNil);

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

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