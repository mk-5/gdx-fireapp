package bindings.google.crashlytics.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CFunction;
import org.moe.natj.c.ann.Variadic;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.BytePtr;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Library("Crashlytics")
@Runtime(CRuntime.class)
public final class Crashlytics {
	static {
		NatJ.register();
	}

	@Generated
	private Crashlytics() {
	}

	@Generated
	@Variadic()
	@CFunction
	public static native void CLSLog(
			@Mapped(ObjCStringMapper.class) String format, Object... varargs);

	@Generated
	@CFunction
	public static native void CLSLogv(
			@Mapped(ObjCStringMapper.class) String format, BytePtr ap);

	@Generated
	@Variadic()
	@CFunction
	public static native void CLSNSLog(
			@Mapped(ObjCStringMapper.class) String format, Object... varargs);

	@Generated
	@CFunction
	public static native void CLSNSLogv(
			@Mapped(ObjCStringMapper.class) String format, BytePtr ap);
}