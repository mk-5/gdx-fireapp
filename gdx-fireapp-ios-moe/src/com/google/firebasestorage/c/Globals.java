package com.google.firebasestorage.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CVariable;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ann.UncertainReturn;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Runtime(CRuntime.class)
public final class Globals {
	static {
		NatJ.register();
	}

	@Generated
	private Globals() {
	}

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRStorageErrorDomain();

	@Generated
	@CVariable()
	@UncertainReturn("Options: java.string, c.const-byte-ptr Fallback: java.string")
	public static native String FIRStorageVersionString();
}