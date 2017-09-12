package com.google.firebasestorage.enums;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.NInt;

@Generated
public final class FIRStorageTaskStatus {
	@Generated
	private FIRStorageTaskStatus() {
	}

	@Generated
	@NInt
	public static final long Unknown = 0x0000000000000000L;
	@Generated
	@NInt
	public static final long Resume = 0x0000000000000001L;
	@Generated
	@NInt
	public static final long Progress = 0x0000000000000002L;
	@Generated
	@NInt
	public static final long Pause = 0x0000000000000003L;
	@Generated
	@NInt
	public static final long Success = 0x0000000000000004L;
	@Generated
	@NInt
	public static final long Failure = 0x0000000000000005L;
}