package com.google.firebaseanalytics.c;


import org.moe.natj.c.CRuntime;
import org.moe.natj.general.NatJ;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Runtime;

@Generated
@Library("FirebaseAnalytics")
@Runtime(CRuntime.class)
public final class FirebaseAnalytics {
	static {
		NatJ.register();
	}

	@Generated
	private FirebaseAnalytics() {
	}
}