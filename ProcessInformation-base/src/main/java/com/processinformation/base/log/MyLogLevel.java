package com.processinformation.base.log;

import org.apache.log4j.Level;
import org.apache.log4j.net.SyslogAppender;

public interface MyLogLevel {
	public static final Level ACCESS_LEVEL = new AccessLevel(35000, "ACCESS",
			SyslogAppender.LOG_LOCAL0);
	public static final Level MONITOR_LEVEL = new MonitorLevel(36000,
			"MONITOR", SyslogAppender.LOG_LOCAL0);
	public static final Level WORK_LEVEL = new WorkLevel(34000, "WORK",
			SyslogAppender.LOG_LOCAL0);
}
