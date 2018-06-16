package com.google.firebaseauth.enums;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.NInt;

@Generated
public final class FIRActionCodeOperation {
	@Generated
	private FIRActionCodeOperation() {
	}

	@Generated
	@NInt
	public static final long Unknown = 0x0000000000000000L;
	@Generated
	@NInt
	public static final long PasswordReset = 0x0000000000000001L;
	@Generated
	@NInt
	public static final long VerifyEmail = 0x0000000000000002L;
	@Generated
	@NInt
	public static final long RecoverEmail = 0x0000000000000003L;
	@Generated
	@NInt
	public static final long EmailLink = 0x0000000000000004L;
}