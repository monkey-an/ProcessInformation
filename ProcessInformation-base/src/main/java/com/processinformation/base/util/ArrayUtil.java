package com.processinformation.base.util;

import java.util.ArrayList;

public class ArrayUtil {

	/**
	 * 
	 * 分割数字类型的数组（仅支持整型）
	 * 
	 * @param list
	 * @param split_count
	 * @return
	 */
	public static ArrayList<? extends Number>[] SplitValueList(
			ArrayList<? extends Number> list, int split_count) {
		@SuppressWarnings("unchecked")
		ArrayList<Number>[] aryList = new ArrayList[split_count];

		int len = list.size() / split_count;// 预计长度
		for (int i = 0; i < split_count; i++) {
			aryList[i] = new ArrayList<Number>(len);
		}
		int mod = 0;

		for (int i = 0; i < list.size(); i++) {

			Number _value = list.get(i);

			mod = (int) (Long.parseLong(_value.toString()) % split_count);

			aryList[mod].add(_value);
		}

		return aryList;
	}

}
