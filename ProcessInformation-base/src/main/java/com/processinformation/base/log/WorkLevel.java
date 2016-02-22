package com.processinformation.base.log;


import org.apache.log4j.Level;
/**
 * @date 2013-5-28 下午1:21:27
 * 
 */
public class WorkLevel extends Level {
	public WorkLevel(int level, String name, int sysLogLevel) {
		super(level, name, sysLogLevel);
	}
}
