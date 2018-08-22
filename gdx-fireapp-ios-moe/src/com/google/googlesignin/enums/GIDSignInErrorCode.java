package com.google.googlesignin.enums;


import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.NInt;

@Generated
public final class GIDSignInErrorCode {
	@Generated
	private GIDSignInErrorCode() {
	}

	@Generated
	@NInt
	public static final long Unknown = 0xFFFFFFFFFFFFFFFFL;
	@Generated
	@NInt
	public static final long Keychain = 0xFFFFFFFFFFFFFFFEL;
	@Generated
	@NInt
	public static final long NoSignInHandlersInstalled = 0xFFFFFFFFFFFFFFFDL;
	@Generated
	@NInt
	public static final long HasNoAuthInKeychain = 0xFFFFFFFFFFFFFFFCL;
	@Generated
	@NInt
	public static final long Canceled = 0xFFFFFFFFFFFFFFFBL;
}