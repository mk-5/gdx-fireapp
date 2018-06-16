package com.google.firebaseauth.c;


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
    public static native String FIRAuthErrorDomain();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRAuthUpdatedCredentialKey();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRAuthErrorNameKey();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRAuthErrorUserInfoEmailKey();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRAuthStateDidChangeNotification();

    @Generated
    @CVariable()
    public static native double FirebaseAuthVersionNum();

    @Generated
    @CVariable()
    @UncertainReturn("Options: java.string, c.const-byte-ptr Fallback: java.string")
    public static native String FirebaseAuthVersionStr();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIREmailAuthProviderID();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIREmailLinkAuthSignInMethod();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIREmailPasswordAuthSignInMethod();

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
    public static native String FIRFacebookAuthSignInMethod();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRGitHubAuthProviderID();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRGitHubAuthSignInMethod();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRGoogleAuthProviderID();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRGoogleAuthSignInMethod();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRTwitterAuthProviderID();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRTwitterAuthSignInMethod();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRPhoneAuthProviderID();

    @Generated
    @CVariable()
    @MappedReturn(ObjCStringMapper.class)
    public static native String FIRPhoneAuthSignInMethod();
}