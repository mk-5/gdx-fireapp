package com.google.firebasedatabase;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDictionary;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
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
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("FirebaseDatabase")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIRDatabaseReference extends FIRDatabaseQuery {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRDatabaseReference(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("URL")
	public native String URL();

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRDatabaseReference alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("cancelDisconnectOperations")
	public native void cancelDisconnectOperations();

	@Generated
	@Selector("cancelDisconnectOperationsWithCompletionBlock:")
	public native void cancelDisconnectOperationsWithCompletionBlock(
			@ObjCBlock(name = "call_cancelDisconnectOperationsWithCompletionBlock") Block_cancelDisconnectOperationsWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_cancelDisconnectOperationsWithCompletionBlock {
		@Generated
		void call_cancelDisconnectOperationsWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

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
	@Selector("child:")
	public native FIRDatabaseReference child(String pathString);

	@Generated
	@Deprecated
	@Selector("childByAppendingPath:")
	public native FIRDatabaseReference childByAppendingPath(String pathString);

	@Generated
	@Selector("childByAutoId")
	public native FIRDatabaseReference childByAutoId();

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
	@Selector("database")
	public native FIRDatabase database();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public native String description();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("goOffline")
	public static native void goOffline();

	@Generated
	@Selector("goOnline")
	public static native void goOnline();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRDatabaseReference init();

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
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keepSynced:")
	public native void keepSynced(boolean keepSynced);

	@Generated
	@Selector("key")
	public native String key();

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("load")
	public static native void load_objc_static();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("observeEventType:andPreviousSiblingKeyWithBlock:")
	@NUInt
	public native long observeEventTypeAndPreviousSiblingKeyWithBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeEventTypeAndPreviousSiblingKeyWithBlock") Block_observeEventTypeAndPreviousSiblingKeyWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeAndPreviousSiblingKeyWithBlock {
		@Generated
		void call_observeEventTypeAndPreviousSiblingKeyWithBlock(
				FIRDataSnapshot arg0, String arg1);
	}

	@Generated
	@Selector("observeEventType:andPreviousSiblingKeyWithBlock:withCancelBlock:")
	@NUInt
	public native long observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1") Block_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1 block,
			@ObjCBlock(name = "call_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2") Block_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2 cancelBlock);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1 {
		@Generated
		void call_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1(
				FIRDataSnapshot arg0, String arg1);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2 {
		@Generated
		void call_observeEventTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2(
				NSError arg0);
	}

	@Generated
	@Selector("observeEventType:withBlock:")
	@NUInt
	public native long observeEventTypeWithBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeEventTypeWithBlock") Block_observeEventTypeWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeWithBlock {
		@Generated
		void call_observeEventTypeWithBlock(FIRDataSnapshot arg0);
	}

	@Generated
	@Selector("observeEventType:withBlock:withCancelBlock:")
	@NUInt
	public native long observeEventTypeWithBlockWithCancelBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeEventTypeWithBlockWithCancelBlock_1") Block_observeEventTypeWithBlockWithCancelBlock_1 block,
			@ObjCBlock(name = "call_observeEventTypeWithBlockWithCancelBlock_2") Block_observeEventTypeWithBlockWithCancelBlock_2 cancelBlock);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeWithBlockWithCancelBlock_1 {
		@Generated
		void call_observeEventTypeWithBlockWithCancelBlock_1(
				FIRDataSnapshot arg0);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeEventTypeWithBlockWithCancelBlock_2 {
		@Generated
		void call_observeEventTypeWithBlockWithCancelBlock_2(NSError arg0);
	}

	@Generated
	@Selector("observeSingleEventOfType:andPreviousSiblingKeyWithBlock:")
	public native void observeSingleEventOfTypeAndPreviousSiblingKeyWithBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlock") Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlock {
		@Generated
		void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlock(
				FIRDataSnapshot arg0, String arg1);
	}

	@Generated
	@Selector("observeSingleEventOfType:andPreviousSiblingKeyWithBlock:withCancelBlock:")
	public native void observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1") Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1 block,
			@ObjCBlock(name = "call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2") Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2 cancelBlock);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1 {
		@Generated
		void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_1(
				FIRDataSnapshot arg0, String arg1);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2 {
		@Generated
		void call_observeSingleEventOfTypeAndPreviousSiblingKeyWithBlockWithCancelBlock_2(
				NSError arg0);
	}

	@Generated
	@Selector("observeSingleEventOfType:withBlock:")
	public native void observeSingleEventOfTypeWithBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeSingleEventOfTypeWithBlock") Block_observeSingleEventOfTypeWithBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeWithBlock {
		@Generated
		void call_observeSingleEventOfTypeWithBlock(FIRDataSnapshot arg0);
	}

	@Generated
	@Selector("observeSingleEventOfType:withBlock:withCancelBlock:")
	public native void observeSingleEventOfTypeWithBlockWithCancelBlock(
			@NInt long eventType,
			@ObjCBlock(name = "call_observeSingleEventOfTypeWithBlockWithCancelBlock_1") Block_observeSingleEventOfTypeWithBlockWithCancelBlock_1 block,
			@ObjCBlock(name = "call_observeSingleEventOfTypeWithBlockWithCancelBlock_2") Block_observeSingleEventOfTypeWithBlockWithCancelBlock_2 cancelBlock);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeWithBlockWithCancelBlock_1 {
		@Generated
		void call_observeSingleEventOfTypeWithBlockWithCancelBlock_1(
				FIRDataSnapshot arg0);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_observeSingleEventOfTypeWithBlockWithCancelBlock_2 {
		@Generated
		void call_observeSingleEventOfTypeWithBlockWithCancelBlock_2(
				NSError arg0);
	}

	@Generated
	@Selector("onDisconnectRemoveValue")
	public native void onDisconnectRemoveValue();

	@Generated
	@Selector("onDisconnectRemoveValueWithCompletionBlock:")
	public native void onDisconnectRemoveValueWithCompletionBlock(
			@ObjCBlock(name = "call_onDisconnectRemoveValueWithCompletionBlock") Block_onDisconnectRemoveValueWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_onDisconnectRemoveValueWithCompletionBlock {
		@Generated
		void call_onDisconnectRemoveValueWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("onDisconnectSetValue:")
	public native void onDisconnectSetValue(
			@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("onDisconnectSetValue:andPriority:")
	public native void onDisconnectSetValueAndPriority(
			@Mapped(ObjCObjectMapper.class) Object value,
			@Mapped(ObjCObjectMapper.class) Object priority);

	@Generated
	@Selector("onDisconnectSetValue:andPriority:withCompletionBlock:")
	public native void onDisconnectSetValueAndPriorityWithCompletionBlock(
			@Mapped(ObjCObjectMapper.class) Object value,
			@Mapped(ObjCObjectMapper.class) Object priority,
			@ObjCBlock(name = "call_onDisconnectSetValueAndPriorityWithCompletionBlock") Block_onDisconnectSetValueAndPriorityWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_onDisconnectSetValueAndPriorityWithCompletionBlock {
		@Generated
		void call_onDisconnectSetValueAndPriorityWithCompletionBlock(
				NSError arg0, FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("onDisconnectSetValue:withCompletionBlock:")
	public native void onDisconnectSetValueWithCompletionBlock(
			@Mapped(ObjCObjectMapper.class) Object value,
			@ObjCBlock(name = "call_onDisconnectSetValueWithCompletionBlock") Block_onDisconnectSetValueWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_onDisconnectSetValueWithCompletionBlock {
		@Generated
		void call_onDisconnectSetValueWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("onDisconnectUpdateChildValues:")
	public native void onDisconnectUpdateChildValues(NSDictionary<?, ?> values);

	@Generated
	@Selector("onDisconnectUpdateChildValues:withCompletionBlock:")
	public native void onDisconnectUpdateChildValuesWithCompletionBlock(
			NSDictionary<?, ?> values,
			@ObjCBlock(name = "call_onDisconnectUpdateChildValuesWithCompletionBlock") Block_onDisconnectUpdateChildValuesWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_onDisconnectUpdateChildValuesWithCompletionBlock {
		@Generated
		void call_onDisconnectUpdateChildValuesWithCompletionBlock(
				NSError arg0, FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("parent")
	public native FIRDatabaseReference parent();

	@Generated
	@Selector("queryEndingAtValue:")
	public native FIRDatabaseQuery queryEndingAtValue(
			@Mapped(ObjCObjectMapper.class) Object endValue);

	@Generated
	@Selector("queryEndingAtValue:childKey:")
	public native FIRDatabaseQuery queryEndingAtValueChildKey(
			@Mapped(ObjCObjectMapper.class) Object endValue, String childKey);

	@Generated
	@Selector("queryEqualToValue:")
	public native FIRDatabaseQuery queryEqualToValue(
			@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("queryEqualToValue:childKey:")
	public native FIRDatabaseQuery queryEqualToValueChildKey(
			@Mapped(ObjCObjectMapper.class) Object value, String childKey);

	@Generated
	@Selector("queryLimitedToFirst:")
	public native FIRDatabaseQuery queryLimitedToFirst(@NUInt long limit);

	@Generated
	@Selector("queryLimitedToLast:")
	public native FIRDatabaseQuery queryLimitedToLast(@NUInt long limit);

	@Generated
	@Selector("queryOrderedByChild:")
	public native FIRDatabaseQuery queryOrderedByChild(String key);

	@Generated
	@Selector("queryOrderedByKey")
	public native FIRDatabaseQuery queryOrderedByKey();

	@Generated
	@Selector("queryOrderedByPriority")
	public native FIRDatabaseQuery queryOrderedByPriority();

	@Generated
	@Selector("queryStartingAtValue:")
	public native FIRDatabaseQuery queryStartingAtValue(
			@Mapped(ObjCObjectMapper.class) Object startValue);

	@Generated
	@Selector("queryStartingAtValue:childKey:")
	public native FIRDatabaseQuery queryStartingAtValueChildKey(
			@Mapped(ObjCObjectMapper.class) Object startValue, String childKey);

	@Generated
	@Selector("removeAllObservers")
	public native void removeAllObservers();

	@Generated
	@Selector("removeObserverWithHandle:")
	public native void removeObserverWithHandle(@NUInt long handle);

	@Generated
	@Selector("removeValue")
	public native void removeValue();

	@Generated
	@Selector("removeValueWithCompletionBlock:")
	public native void removeValueWithCompletionBlock(
			@ObjCBlock(name = "call_removeValueWithCompletionBlock") Block_removeValueWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_removeValueWithCompletionBlock {
		@Generated
		void call_removeValueWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("root")
	public native FIRDatabaseReference root();

	@Generated
	@Selector("runTransactionBlock:")
	public native void runTransactionBlock(
			@ObjCBlock(name = "call_runTransactionBlock") Block_runTransactionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_runTransactionBlock {
		@Generated
		FIRTransactionResult call_runTransactionBlock(FIRMutableData arg0);
	}

	@Generated
	@Selector("runTransactionBlock:andCompletionBlock:")
	public native void runTransactionBlockAndCompletionBlock(
			@ObjCBlock(name = "call_runTransactionBlockAndCompletionBlock_0") Block_runTransactionBlockAndCompletionBlock_0 block,
			@ObjCBlock(name = "call_runTransactionBlockAndCompletionBlock_1") Block_runTransactionBlockAndCompletionBlock_1 completionBlock);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_runTransactionBlockAndCompletionBlock_0 {
		@Generated
		FIRTransactionResult call_runTransactionBlockAndCompletionBlock_0(
				FIRMutableData arg0);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_runTransactionBlockAndCompletionBlock_1 {
		@Generated
		void call_runTransactionBlockAndCompletionBlock_1(NSError arg0,
				boolean arg1, FIRDataSnapshot arg2);
	}

	@Generated
	@Selector("runTransactionBlock:andCompletionBlock:withLocalEvents:")
	public native void runTransactionBlockAndCompletionBlockWithLocalEvents(
			@ObjCBlock(name = "call_runTransactionBlockAndCompletionBlockWithLocalEvents_0") Block_runTransactionBlockAndCompletionBlockWithLocalEvents_0 block,
			@ObjCBlock(name = "call_runTransactionBlockAndCompletionBlockWithLocalEvents_1") Block_runTransactionBlockAndCompletionBlockWithLocalEvents_1 completionBlock,
			boolean localEvents);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_runTransactionBlockAndCompletionBlockWithLocalEvents_0 {
		@Generated
		FIRTransactionResult call_runTransactionBlockAndCompletionBlockWithLocalEvents_0(
				FIRMutableData arg0);
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_runTransactionBlockAndCompletionBlockWithLocalEvents_1 {
		@Generated
		void call_runTransactionBlockAndCompletionBlockWithLocalEvents_1(
				NSError arg0, boolean arg1, FIRDataSnapshot arg2);
	}

	@Generated
	@Selector("setPriority:")
	public native void setPriority(
			@Mapped(ObjCObjectMapper.class) Object priority);

	@Generated
	@Selector("setPriority:withCompletionBlock:")
	public native void setPriorityWithCompletionBlock(
			@Mapped(ObjCObjectMapper.class) Object priority,
			@ObjCBlock(name = "call_setPriorityWithCompletionBlock") Block_setPriorityWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_setPriorityWithCompletionBlock {
		@Generated
		void call_setPriorityWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("setValue:")
	public native void setValue(@Mapped(ObjCObjectMapper.class) Object value);

	@Generated
	@Selector("setValue:andPriority:")
	public native void setValueAndPriority(
			@Mapped(ObjCObjectMapper.class) Object value,
			@Mapped(ObjCObjectMapper.class) Object priority);

	@Generated
	@Selector("setValue:andPriority:withCompletionBlock:")
	public native void setValueAndPriorityWithCompletionBlock(
			@Mapped(ObjCObjectMapper.class) Object value,
			@Mapped(ObjCObjectMapper.class) Object priority,
			@ObjCBlock(name = "call_setValueAndPriorityWithCompletionBlock") Block_setValueAndPriorityWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_setValueAndPriorityWithCompletionBlock {
		@Generated
		void call_setValueAndPriorityWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("setValue:withCompletionBlock:")
	public native void setValueWithCompletionBlock(
			@Mapped(ObjCObjectMapper.class) Object value,
			@ObjCBlock(name = "call_setValueWithCompletionBlock") Block_setValueWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_setValueWithCompletionBlock {
		@Generated
		void call_setValueWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("updateChildValues:")
	public native void updateChildValues(NSDictionary<?, ?> values);

	@Generated
	@Selector("updateChildValues:withCompletionBlock:")
	public native void updateChildValuesWithCompletionBlock(
			NSDictionary<?, ?> values,
			@ObjCBlock(name = "call_updateChildValuesWithCompletionBlock") Block_updateChildValuesWithCompletionBlock block);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updateChildValuesWithCompletionBlock {
		@Generated
		void call_updateChildValuesWithCompletionBlock(NSError arg0,
				FIRDatabaseReference arg1);
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}