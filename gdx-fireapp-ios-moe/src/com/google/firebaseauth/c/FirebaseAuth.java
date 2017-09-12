package com.google.firebaseauth.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.c.ann.CVariable;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.MappedReturn;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.general.ann.UncertainReturn;
import org.moe.natj.objc.map.ObjCStringMapper;

@Generated
@Library("FirebaseAuth")
@Runtime(CRuntime.class)
public final class FirebaseAuth {
	static {
		NatJ.register();
	}

	@Generated
	private FirebaseAuth() {
	}

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIREmailPasswordAuthProviderID();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRFacebookAuthProviderID();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRGitHubAuthProviderID();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRGoogleAuthProviderID();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRTwitterAuthProviderID();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRAuthErrorDomain();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRAuthErrorNameKey();

	@Generated
	@CVariable()
	@MappedReturn(ObjCStringMapper.class)
	public static native String FIRAuthStateDidChangeNotification();

	@Generated
	@CVariable()
	public static native double FirebaseAuthVersionNumber();

	@Generated
	@CVariable()
	@UncertainReturn("Options: java.string, c.const-byte-ptr Fallback: java.string")
	public static native String FirebaseAuthVersionString();
}