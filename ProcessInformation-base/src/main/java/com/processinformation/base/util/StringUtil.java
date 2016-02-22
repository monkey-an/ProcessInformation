package com.processinformation.base.util;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

	private static final String FORMAT_SPECIFIER = "\\{\\d+\\}";
	private static final String DATE_FORMAT_SPECIFIER = "\\{\\d+\\:[\\w\\s\\:\\-\\/\\.]+\\}";

	private static Pattern fsPattern = Pattern.compile(FORMAT_SPECIFIER);
	private static Pattern dfsPattern = Pattern.compile(DATE_FORMAT_SPECIFIER);

	/**
	 * format string <br/>
	 * example:StringFormat.format("a={0},b={1:yyyy-MM-dd HH:mm:ss.SSS},c={2}",
	 * "a", new Date(), "c"))
	 * 
	 * @param f
	 * @param args
	 *            参数
	 * @return
	 */
	public static String format(String f, Object... args) {
		if (null != f && null != args) {
			String result = doFormat(f, args);
			return doDateFormat(result, args);
		} else {
			return f;
		}
	}

	/**
	 * 
	 * @param f
	 * @param args
	 * @return
	 */
	private static String doFormat(String f, Object... args) {
		String result = f;
		Matcher matcher = fsPattern.matcher(result);
		while (matcher.find()) {
			String m_g = matcher.group();
			int f_index = ConvertUtil.toInt(m_g.substring(1, m_g.length() - 1));

			result = result.substring(0, matcher.start())
					+ ConvertUtil.toString(args[f_index])
					+ result.substring(matcher.end(), result.length());

			matcher = fsPattern.matcher(result);
		}

		return result;
	}

	/**
	 * 
	 * @param f
	 * @param args
	 * @return
	 */
	private static String doDateFormat(String f, Object... args) {
		String result = f;
		Matcher matcher = dfsPattern.matcher(result);
		while (matcher.find()) {
			String m_g = matcher.group();
			int special_char_index = m_g.indexOf(":");
			int f_index = ConvertUtil.toInt(m_g
					.substring(1, special_char_index));
			String dateFormat = m_g.substring(special_char_index + 1,
					m_g.length() - 1);

			result = result.substring(0, matcher.start())
					+ ConvertUtil.toDateString(args[f_index], dateFormat)
					+ result.substring(matcher.end(), result.length());

			matcher = dfsPattern.matcher(result);
		}

		return result;
	}

	/**
	 * compare string.<br>
	 * example:<br>
	 * a=null b=null return:true<br>
	 * a="e" b=null return:false<br>
	 * a="e" b="E" return:false<br>
	 * a="e" b="e" return:true<br>
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean compare(String source, String target) {
		if (null == source && null == target) {
			return true;
		}
		if ((null == source && null != target)
				|| (null != source && null == target)) {
			return false;
		}
		return source.equals(target);
	}

	/**
	 * compare string.<br>
	 * 
	 * @param source
	 * @param target
	 * @param ignoreCase
	 * @return
	 */
	public static boolean compare(String source, String target,
			boolean ignoreCase) {
		if (null == source && null == target) {
			return true;
		}
		if ((null == source && null != target)
				|| (null != source && null == target)) {
			return false;
		}
		if (!ignoreCase)
			return source.equals(target);
		else {
			return source.toUpperCase().equals(target.toUpperCase());
		}
	}

	public static void main(String[] args) {
		try {
			// print cost 31ms
			System.out.println(ConvertUtil.toDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss.SSS"));
			// 100000 times loop
			for (int i = 0; i < 100000; i++) {
				// cost 172ms
				// format("a={0},b={1},c={2}", "a", "b", "c");
				// cost 359ms
				String.format("a=%s,b=%s,c=%s", "a", "b", "c");
			}
			System.out.println(ConvertUtil.toDateString(new Date(),
					"yyyy-MM-dd HH:mm:ss.SSS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
