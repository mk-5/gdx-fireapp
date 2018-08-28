package bindings.google.crashlytics.protocol;


import bindings.google.crashlytics.CLSReport;
import bindings.google.crashlytics.Crashlytics;
import org.moe.natj.general.ann.Generated;
import org.moe.natj.general.ann.Library;
import org.moe.natj.general.ann.Mapped;
import org.moe.natj.general.ann.Runtime;
import org.moe.natj.objc.ObjCRuntime;
import org.moe.natj.objc.ann.IsOptional;
import org.moe.natj.objc.ann.ObjCBlock;
import org.moe.natj.objc.ann.ObjCProtocolName;
import org.moe.natj.objc.ann.Selector;
import org.moe.natj.objc.map.ObjCObjectMapper;

@Generated
@Library("Crashlytics")
@Runtime(ObjCRuntime.class)
@ObjCProtocolName("CrashlyticsDelegate")
public interface CrashlyticsDelegate {
	@Generated
	@IsOptional
	@Deprecated
	@Selector("crashlytics:didDetectCrashDuringPreviousExecution:")
	default void crashlyticsDidDetectCrashDuringPreviousExecution(
			Crashlytics crashlytics,
			@Mapped(ObjCObjectMapper.class) Object crash) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("crashlyticsCanUseBackgroundSessions:")
	default boolean crashlyticsCanUseBackgroundSessions(Crashlytics crashlytics) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Deprecated
	@Selector("crashlyticsDidDetectCrashDuringPreviousExecution:")
	default void crashlyticsDidDetectCrashDuringPreviousExecution(
			Crashlytics crashlytics) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("crashlyticsDidDetectReportForLastExecution:")
	default void crashlyticsDidDetectReportForLastExecution(CLSReport report) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Generated
	@IsOptional
	@Selector("crashlyticsDidDetectReportForLastExecution:completionHandler:")
	default void crashlyticsDidDetectReportForLastExecutionCompletionHandler(
			CLSReport report,
			@ObjCBlock(name = "call_crashlyticsDidDetectReportForLastExecutionCompletionHandler") Block_crashlyticsDidDetectReportForLastExecutionCompletionHandler completionHandler) {
		throw new java.lang.UnsupportedOperationException();
	}

	@Runtime(ObjCRuntime.class)
	@Generated
	public interface Block_crashlyticsDidDetectReportForLastExecutionCompletionHandler {
		@Generated
		void call_crashlyticsDidDetectReportForLastExecutionCompletionHandler(
				boolean arg0);
	}
}