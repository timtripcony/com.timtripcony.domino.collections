package com.timtripcony.util;

import java.util.Stack;

public class LogUtil {
	private static class TimedOperation {
		private String description_;
		private Long startTime_;
		private Long endTime_;

		public TimedOperation(String description) {
			setDescription(description);
			setStartTime(System.currentTimeMillis());
		}

		public String getDescription() {
			return description_;
		}

		public long getDuration() {
			return getEndTime() - getStartTime();
		}

		public long getEndTime() {
			if (endTime_ == null) {
				return System.currentTimeMillis();
			}
			return endTime_;
		}

		public long getStartTime() {
			return startTime_;
		}

		public void setDescription(String description) {
			description_ = description;
		}

		public void setEndTime(long endTime) {
			endTime_ = endTime;
		}

		public void setStartTime(long startTime) {
			startTime_ = startTime;
		}
	}

	private static class Timer extends Stack<TimedOperation> {
		private static final long serialVersionUID = 1L;

		public void start(String description) {
			TimedOperation op = new TimedOperation(description);
			push(op);
		}

		public void stop() {
			TimedOperation op = pop();
			op.setEndTime(System.currentTimeMillis());
			log(op.getDescription() + " - " + op.getDuration() + " ms");
		}
	}

	public static final boolean LOG_ENABLED = true;
	public static final boolean TIMER_ENABLED = true;
	public static final boolean TRACE_ENABLED = true;
	private static final Timer timer_ = new Timer();

	public static void log(StackTraceElement lastOp, String message) {
		if (LOG_ENABLED) {
			String klass = lastOp.getClassName();
			String crystal = lastOp.getMethodName();
			int line = lastOp.getLineNumber();
			System.out.println(klass + "." + crystal + "()[" + line + "]: " + message);
		}
	}

	public static void log(String message) {
		log(new Throwable().getStackTrace()[1], message);
	}

	public static void log(Throwable t, String message) {
		log(t.getStackTrace()[0], message);
	}

	public static void startTimedOperation(String description) {
		if (TIMER_ENABLED) {
			timer_.start(description);
		}
	}

	public static void stopTimedOperation() {
		if (TIMER_ENABLED) {
			timer_.stop();
		}
	}

	public static void trace(Throwable t, String message) {
		log(t, message);
		if (TRACE_ENABLED) {
			t.printStackTrace();
		}
	}
}
