package com.processinformation.base.util;

import java.math.BigDecimal;
import java.util.Collection;

public class ValueCheckUtil {

	public static <T extends Object> boolean isNullorZeroLength(
			Collection<T> param) {
		if (param == null || param.size() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isNullorZeroLength(Object[] objs) {
		if (objs != null && objs.length != 0) {
			return false;
		}
		return true;
	}

	public static boolean isObjEqual(Object left, Object right) {
		if (left == null && right == null) {
			return true;
		} else if (left != null && right != null) {
			return left.equals(right);
		}
		return false;
	}

	public static boolean isStrEgnoreCaseEqual(String left, String right) {
		if (left == null && right == null) {
			return true;
		} else if (left != null && right != null) {
			return left.equalsIgnoreCase(right);
		}
		return false;
	}

	public static boolean isLessThanZero(BigDecimal value) {
		if (value == null) {
			return false;
		}
		if (value.signum() < 0) {
			return true;
		}
		return false;
	}

}
