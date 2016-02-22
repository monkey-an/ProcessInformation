package com.processinformation.base.log;

import org.apache.log4j.Level;

public class MonitorLevel extends Level {
	public MonitorLevel(int level, String name, int sysLogLevel) {
		super(level, name, sysLogLevel);
	}
}
