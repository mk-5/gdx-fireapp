package com.google.firebasemessaging.enums;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.NUInt;

@Generated
public final class FIRMessagingError {
	@Generated
	private FIRMessagingError() {
	}

	@Generated
	@NUInt
	public static final long Unknown = 0x0000000000000000L;
	@Generated
	@NUInt
	public static final long Authentication = 0x0000000000000001L;
	@Generated
	@NUInt
	public static final long NoAccess = 0x0000000000000002L;
	@Generated
	@NUInt
	public static final long Timeout = 0x0000000000000003L;
	@Generated
	@NUInt
	public static final long Network = 0x0000000000000004L;
	@Generated
	@NUInt
	public static final long OperationInProgress = 0x0000000000000005L;
	@Generated
	@NUInt
	public static final long InvalidRequest = 0x0000000000000007L;
}