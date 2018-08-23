package bindings.google.firebasestorage;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSData;
import apple.foundation.NSError;
import apple.foundation.NSMethodSignature;
import apple.foundation.NSSet;
import apple.foundation.NSURL;
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
public class FIRStorageReference extends NSObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRStorageReference(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRStorageReference alloc();

	@Generated
	@Selector("allocWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object allocWithZone(VoidPtr zone);

	@Generated
	@Selector("automaticallyNotifiesObserversForKey:")
	public static native boolean automaticallyNotifiesObserversForKey(String key);

	@Generated
	@Selector("bucket")
	public native String bucket();

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
	public native FIRStorageReference child(String path);

	@Generated
	@Selector("classFallbacksForKeyedArchiver")
	public static native NSArray<String> classFallbacksForKeyedArchiver();

	@Generated
	@Selector("classForKeyedUnarchiver")
	public static native Class classForKeyedUnarchiver();

	@Generated
	@Selector("dataWithMaxSize:completion:")
	public native FIRStorageDownloadTask dataWithMaxSizeCompletion(
			long size,
			@ObjCBlock(name = "call_dataWithMaxSizeCompletion") Block_dataWithMaxSizeCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_dataWithMaxSizeCompletion {
		@Generated
		void call_dataWithMaxSizeCompletion(NSData arg0, NSError arg1);
	}

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
	@Selector("downloadURLWithCompletion:")
	public native void downloadURLWithCompletion(
			@ObjCBlock(name = "call_downloadURLWithCompletion") Block_downloadURLWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_downloadURLWithCompletion {
		@Generated
		void call_downloadURLWithCompletion(NSURL arg0, NSError arg1);
	}

	@Generated
	@Selector("fullPath")
	public native String fullPath();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRStorageReference init();

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
	@Selector("metadataWithCompletion:")
	public native void metadataWithCompletion(
			@ObjCBlock(name = "call_metadataWithCompletion") Block_metadataWithCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_metadataWithCompletion {
		@Generated
		void call_metadataWithCompletion(FIRStorageMetadata arg0, NSError arg1);
	}

	@Generated
	@Selector("name")
	public native String name();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("parent")
	public native FIRStorageReference parent();

	@Generated
	@Selector("putData:")
	public native FIRStorageUploadTask putData(NSData uploadData);

	@Generated
	@Selector("putData:metadata:")
	public native FIRStorageUploadTask putDataMetadata(NSData uploadData,
			FIRStorageMetadata metadata);

	@Generated
	@Selector("putData:metadata:completion:")
	public native FIRStorageUploadTask putDataMetadataCompletion(
			NSData uploadData,
			FIRStorageMetadata metadata,
			@ObjCBlock(name = "call_putDataMetadataCompletion") Block_putDataMetadataCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_putDataMetadataCompletion {
		@Generated
		void call_putDataMetadataCompletion(FIRStorageMetadata arg0,
				NSError arg1);
	}

	@Generated
	@Selector("putFile:")
	public native FIRStorageUploadTask putFile(NSURL fileURL);

	@Generated
	@Selector("putFile:metadata:")
	public native FIRStorageUploadTask putFileMetadata(NSURL fileURL,
			FIRStorageMetadata metadata);

	@Generated
	@Selector("putFile:metadata:completion:")
	public native FIRStorageUploadTask putFileMetadataCompletion(
			NSURL fileURL,
			FIRStorageMetadata metadata,
			@ObjCBlock(name = "call_putFileMetadataCompletion") Block_putFileMetadataCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_putFileMetadataCompletion {
		@Generated
		void call_putFileMetadataCompletion(FIRStorageMetadata arg0,
				NSError arg1);
	}

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("root")
	public native FIRStorageReference root();

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("storage")
	public native FIRStorage storage();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("updateMetadata:completion:")
	public native void updateMetadataCompletion(
			FIRStorageMetadata metadata,
			@ObjCBlock(name = "call_updateMetadataCompletion") Block_updateMetadataCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_updateMetadataCompletion {
		@Generated
		void call_updateMetadataCompletion(FIRStorageMetadata arg0, NSError arg1);
	}

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();

	@Generated
	@Selector("writeToFile:")
	public native FIRStorageDownloadTask writeToFile(NSURL fileURL);

	@Generated
	@Selector("writeToFile:completion:")
	public native FIRStorageDownloadTask writeToFileCompletion(
			NSURL fileURL,
			@ObjCBlock(name = "call_writeToFileCompletion") Block_writeToFileCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_writeToFileCompletion {
		@Generated
		void call_writeToFileCompletion(NSURL arg0, NSError arg1);
	}
}