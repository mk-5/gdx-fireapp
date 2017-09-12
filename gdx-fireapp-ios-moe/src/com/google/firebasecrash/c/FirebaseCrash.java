package com.google.firebasecrash.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CFunction;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ptr.BytePtr;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Library("FirebaseCrash")
@Runtime(CRuntime.class)
public final class FirebaseCrash {
	static {
		NatJ.register();
	}

	@Generated
	private FirebaseCrash() {
	}

	@Generated
	@CFunction
	public static native void FIRCrashLogv(
			@Mapped(ObjCStringMapper.class) String format, BytePtr ap);
}