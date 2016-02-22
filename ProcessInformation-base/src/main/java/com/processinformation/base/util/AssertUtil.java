package com.processinformation.base.util;

public class AssertUtil {

	public static boolean isTrue(boolean expression, String errorMsg) {
		if (!expression) {
			throw new IllegalArgumentException(errorMsg);

		}
		return true;
	}
}
