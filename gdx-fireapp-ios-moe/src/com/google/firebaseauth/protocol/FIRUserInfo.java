package com.google.firebaseauth.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

import apple.foundation.NSURL;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRUserInfo")
public interface FIRUserInfo {
    @Generated
    @Selector("displayName")
    String displayName();

    @Generated
    @Selector("email")
    String email();

    @Generated
    @Selector("phoneNumber")
    String phoneNumber();

    @Generated
    @Selector("photoURL")
    NSURL photoURL();

    @Generated
    @Selector("providerID")
    String providerID();

    @Generated
    @Selector("uid")
    String uid();
}