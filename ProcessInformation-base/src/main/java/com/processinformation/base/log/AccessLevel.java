package com.processinformation.base.log;

import org.apache.log4j.Level;

public class AccessLevel extends Level {
	public AccessLevel(int level, String name, int sysLogLevel) {
		super(level, name, sysLogLevel);
	}
}
