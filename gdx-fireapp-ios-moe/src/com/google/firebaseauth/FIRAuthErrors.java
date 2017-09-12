package com.google.firebaseauth;


import org.moe.natj.general.NatJ;
import org.moe.natj.general.Pointer;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Owned;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCObject;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCClassBinding;
import org.moe.natj.objc.ann.Selector;

@Generated
@Library("FirebaseAuth")
@Runtime(ObjCRuntime.class)
@ObjCClassBinding
public class FIRAuthErrors extends ObjCObject {
	static {
		NatJ.register();
	}

	@Generated
	protected FIRAuthErrors(Pointer peer) {
		super(peer);
	}

	@Generated
	@Owned
	@Selector("alloc")
	public static native FIRAuthErrors alloc();
}