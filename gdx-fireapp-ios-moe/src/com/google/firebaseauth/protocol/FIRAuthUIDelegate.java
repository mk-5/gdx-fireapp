package com.google.firebaseauth.protocol;


import apple.uikit.UIViewController;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;

@Generated
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("FIRAuthUIDelegate")
public interface FIRAuthUIDelegate {
	@Generated
	@Selector("dismissViewControllerAnimated:completion:")
	void dismissViewControllerAnimatedCompletion(
			boolean flag,
			@ObjCBlock(name = "call_dismissViewControllerAnimatedCompletion") Block_dismissViewControllerAnimatedCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_dismissViewControllerAnimatedCompletion {
		@Generated
		void call_dismissViewControllerAnimatedCompletion();
	}

	@Generated
	@Selector("presentViewController:animated:completion:")
	void presentViewControllerAnimatedCompletion(
			UIViewController viewControllerToPresent,
			boolean flag,
			@ObjCBlock(name = "call_presentViewControllerAnimatedCompletion") Block_presentViewControllerAnimatedCompletion completion);

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_presentViewControllerAnimatedCompletion {
		@Generated
		void call_presentViewControllerAnimatedCompletion();
	}
}