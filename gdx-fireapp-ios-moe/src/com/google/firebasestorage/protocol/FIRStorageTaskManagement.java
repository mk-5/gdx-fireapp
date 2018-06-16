package com.google.firebasestorage.protocol;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRStorageTaskManagement")
public interface FIRStorageTaskManagement {
    @Generated
    @IsOptional
    @Selector("cancel")
    default void cancel() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Generated
    @Selector("enqueue")
    void enqueue();

    @Generated
    @IsOptional
    @Selector("pause")
    default void pause() {
        throw new java.lang.UnsupportedOperationException();
    }

    @Generated
    @IsOptional
    @Selector("resume")
    default void resume() {
        throw new java.lang.UnsupportedOperationException();
    }
}