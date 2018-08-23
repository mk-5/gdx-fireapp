package bindings.google.firebasestorage;


import apple.NSObject;
import apple.foundation.NSArray;
import apple.foundation.NSDate;
import apple.foundation.NSDictionary;
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
public class FIRStorageMetadata extends NSObject implements NSCopying {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRStorageMetadata(Pointer peer) {
		super(peer);
	}

	@Generated
	@Selector("accessInstanceVariablesDirectly")
	public static native boolean accessInstanceVariablesDirectly();

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRStorageMetadata alloc();

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
	@Selector("cacheControl")
	public native String cacheControl();

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
	@Selector("contentDisposition")
	public native String contentDisposition();

	@Generated
	@Selector("contentEncoding")
	public native String contentEncoding();

	@Generated
	@Selector("contentLanguage")
	public native String contentLanguage();

	@Generated
	@Selector("contentType")
	public native String contentType();

	@Generated
	@Owned
	@Selector("copyWithZone:")
	@MappedReturn(ObjCObjectMapper.class)
	public native Object copyWithZone(VoidPtr zone);

	@Generated
	@Selector("customMetadata")
	public native NSDictionary<String, String> customMetadata();

	@Generated
	@Selector("debugDescription")
	public static native String debugDescription_static();

	@Generated
	@Selector("description")
	public static native String description_static();

	@Generated
	@Selector("dictionaryRepresentation")
	public native NSDictionary<String, ?> dictionaryRepresentation();

	@Generated
	@Selector("generation")
	public native long generation();

	@Generated
	@Selector("hash")
	@NUInt
	public static native long hash_static();

	@Generated
	@Selector("init")
	public native FIRStorageMetadata init();

	@Generated
	@Selector("initWithDictionary:")
	public native FIRStorageMetadata initWithDictionary(
			NSDictionary<String, ?> dictionary);

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
	@Selector("isFile")
	public native boolean isFile();

	@Generated
	@Selector("isFolder")
	public native boolean isFolder();

	@Generated
	@Selector("isSubclassOfClass:")
	public static native boolean isSubclassOfClass(Class aClass);

	@Generated
	@Selector("keyPathsForValuesAffectingValueForKey:")
	public static native NSSet<String> keyPathsForValuesAffectingValueForKey(
			String key);

	@Generated
	@Selector("md5Hash")
	public native String md5Hash();

	@Generated
	@Selector("metageneration")
	public native long metageneration();

	@Generated
	@Selector("name")
	public native String name();

	@Generated
	@Owned
	@Selector("new")
	@MappedReturn(ObjCObjectMapper.class)
	public static native Object new_objc();

	@Generated
	@Selector("path")
	public native String path();

	@Generated
	@Selector("resolveClassMethod:")
	public static native boolean resolveClassMethod(SEL sel);

	@Generated
	@Selector("resolveInstanceMethod:")
	public static native boolean resolveInstanceMethod(SEL sel);

	@Generated
	@Selector("setCacheControl:")
	public native void setCacheControl(String value);

	@Generated
	@Selector("setContentDisposition:")
	public native void setContentDisposition(String value);

	@Generated
	@Selector("setContentEncoding:")
	public native void setContentEncoding(String value);

	@Generated
	@Selector("setContentLanguage:")
	public native void setContentLanguage(String value);

	@Generated
	@Selector("setContentType:")
	public native void setContentType(String value);

	@Generated
	@Selector("setCustomMetadata:")
	public native void setCustomMetadata(NSDictionary<String, String> value);

	@Generated
	@Selector("setVersion:")
	public static native void setVersion(@NInt long aVersion);

	@Generated
	@Selector("size")
	public native long size();

	@Generated
	@Selector("storageReference")
	public native FIRStorageReference storageReference();

	@Generated
	@Selector("superclass")
	public static native Class superclass_static();

	@Generated
	@Selector("timeCreated")
	public native NSDate timeCreated();

	@Generated
	@Selector("updated")
	public native NSDate updated();

	@Generated
	@Selector("version")
	@NInt
	public static native long version_static();
}