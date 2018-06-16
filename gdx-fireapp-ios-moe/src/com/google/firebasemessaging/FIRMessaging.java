package com.google.firebasemessaging;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSData;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import com.google.firebasemessaging.protocol.FIRMessagingDelegate;
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
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIRMessaging extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRMessaging(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("APNSToken")
	public native NSData APNSToken();

	@Generated
	@Selector("FCMToken")
	public native String FCMToken();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRMessaging alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("appDidReceiveMessage:")
	public native FIRMessagingMessageInfo appDidReceiveMessage(
			NSDictionary<?, ?> message);

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
	@Deprecated
	@Selector("connectWithCompletion:")
	public native void connectWithCompletion(
			@ObjCBlock(name = "call_connectWithCompletion") Block_connectWithCompletion handler);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_connectWithCompletion {
		@Generated
		void call_connectWithCompletion(NSError arg0);
	}

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("delegate")
	@MappedReturn(ObjCObjectMapper.class)
	public native FIRMessagingDelegate delegate();

	@Generated
	@Selector("deleteFCMTokenForSenderID:completion:")
	public native void deleteFCMTokenForSenderIDCompletion(
			String senderID,
			@ObjCBlock(name = "call_deleteFCMTokenForSenderIDCompletion") Block_deleteFCMTokenForSenderIDCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_deleteFCMTokenForSenderIDCompletion {
		@Generated
		void call_deleteFCMTokenForSenderIDCompletion(NSError arg0);
	}

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Deprecated
	@Selector("disconnect")
	public native void disconnect();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRMessaging init();

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
	@Selector("isAutoInitEnabled")
	public native boolean isAutoInitEnabled();

	@Generated
	@Selector("isDirectChannelEstablished")
	public native boolean isDirectChannelEstablished();

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("messaging")
	public static native FIRMessaging messaging();

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
	@Selector("retrieveFCMTokenForSenderID:completion:")
	public native void retrieveFCMTokenForSenderIDCompletion(
			String senderID,
			@ObjCBlock(name = "call_retrieveFCMTokenForSenderIDCompletion") Block_retrieveFCMTokenForSenderIDCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_retrieveFCMTokenForSenderIDCompletion {
		@Generated
		void call_retrieveFCMTokenForSenderIDCompletion(String arg0,
				NSError arg1);
	}

	@Generated
	@Selector("sendMessage:to:withMessageID:timeToLive:")
	public native void sendMessageToWithMessageIDTimeToLive(
			NSDictionary<?, ?> message, String receiver, String messageID,
			long ttl);

	@Generated
	@Selector("setAPNSToken:")
	public native void setAPNSToken(NSData value);

	@Generated
	@Selector("setAPNSToken:type:")
	public native void setAPNSTokenType(NSData apnsToken, @NInt long type);

	@Generated
	@Selector("setAutoInitEnabled:")
	public native void setAutoInitEnabled(boolean value);

	@Generated
	@Selector("setDelegate:")
	public native void setDelegate_unsafe(
			@Mapped(ObjCObjectMapper.class) FIRMessagingDelegate value);

	@Generated
	public void setDelegate(
			@Mapped(ObjCObjectMapper.class) FIRMessagingDelegate value) {
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
	@Selector("setShouldEstablishDirectChannel:")
	public native void setShouldEstablishDirectChannel(boolean value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("shouldEstablishDirectChannel")
	public native boolean shouldEstablishDirectChannel();

	@Generated
	@Selector("subscribeToTopic:")
	public native void subscribeToTopic(String topic);

	@Generated
	@Selector("subscribeToTopic:completion:")
	public native void subscribeToTopicCompletion(
			String topic,
			@ObjCBlock(name = "call_subscribeToTopicCompletion") Block_subscribeToTopicCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_subscribeToTopicCompletion {
		@Generated
		void call_subscribeToTopicCompletion(NSError arg0);
	}

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("unsubscribeFromTopic:")
	public native void unsubscribeFromTopic(String topic);

	@Generated
	@Selector("unsubscribeFromTopic:completion:")
	public native void unsubscribeFromTopicCompletion(
			String topic,
			@ObjCBlock(name = "call_unsubscribeFromTopicCompletion") Block_unsubscribeFromTopicCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_unsubscribeFromTopicCompletion {
		@Generated
		void call_unsubscribeFromTopicCompletion(NSError arg0);
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}