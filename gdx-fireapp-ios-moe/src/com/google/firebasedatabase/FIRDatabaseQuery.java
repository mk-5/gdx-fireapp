package com.google.firebasedatabase;


import apple.NSObject;
import apple.foundation.NSArray;
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
public class FIRDatabaseQuery extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRDatabaseQuery(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRDatabaseQuery alloc();

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
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRDatabaseQuery init();

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
	@Selector("queryOrderedByValue")
	public native FIRDatabaseQuery queryOrderedByValue();

	@Generated
	@Selector("queryStartingAtValue:")
	public native FIRDatabaseQuery queryStartingAtValue(
			@Mapped(ObjCObjectMapper.class) Object startValue);

	@Generated
	@Selector("queryStartingAtValue:childKey:")
	public native FIRDatabaseQuery queryStartingAtValueChildKey(
			@Mapped(ObjCObjectMapper.class) Object startValue, String childKey);

	@Generated
	@Selector("ref")
	public native FIRDatabaseReference ref();

	@Generated
	@Selector("removeAllObservers")
	public native void removeAllObservers();

	@Generated
	@Selector("removeObserverWithHandle:")
	public native void removeObserverWithHandle(@NUInt long handle);

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