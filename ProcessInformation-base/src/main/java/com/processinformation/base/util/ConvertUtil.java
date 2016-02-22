package com.processinformation.base.util;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.processinformation.base.log.LogWriter;

public class ConvertUtil {
	public static String GBK = "GBK";
	public static String UTF8 = "UTF-8";
	private static final Log log = LogFactory.getLog(ConvertUtil.class);
	private static Date dateMinValue = getDateMinValue();
	private static Date dateMaxValue = getDateMaxValue();

	/**
	 * urlencode鏂规�?
	 * 
	 * @param url
	 *            url鍦板�?
	 * @param encode
	 *            缂栫�?
	 * @return urlencode缁撴�?,鍑虹幇寮傚父杩斿洖绌哄瓧绗︿�?
	 */
	public static String urlEncode(String url, String encode) {
		return urlEncode(url, encode, "");
	}

	/**
	 * urldecode鏂规�?
	 * 
	 * @param url
	 *            url鍦板�?
	 * @param encode
	 *            缂栫�?
	 * @return urldecode缁撴�?,鍑虹幇寮傚父杩斿洖绌哄瓧绗︿�?
	 */
	public static String urlDecode(String url, String encode) {
		return urlDecode(url, encode, "");
	}

	/**
	 * urlencode鏂规�?
	 * 
	 * @param url
	 *            url url鍦板�?
	 * @param encode
	 *            encode 缂栫�?
	 * @param defaultValue
	 *            鍑虹幇寮傚父鏃剁殑杩斿洖鍊�
	 * @return urlencode缁撴�?
	 */
	public static String urlEncode(String url, String encode,
			String defaultValue) {
		try {
			return URLEncoder.encode(url, encode);
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return defaultValue;
		}
	}

	/**
	 * urldecode鏂规�?
	 * 
	 * @param url
	 *            url url鍦板�?
	 * @param encode
	 *            encode 缂栫�?
	 * @param defaultValue
	 *            鍑虹幇寮傚父鏃剁殑杩斿洖鍊�
	 * @return urldecode缁撴�?
	 */
	public static String urlDecode(String url, String encode,
			String defaultValue) {
		try {
			return URLDecoder.decode(url, encode);
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
			return defaultValue;
		}
	}

	/**
	 * Convert to String.
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o) {
		if (o == null)
			return null;
		return o.toString();
	}

	/**
	 * Convert to String. (with default value)
	 * 
	 * @param o
	 * @return
	 */
	public static String toString(Object o, String defaultValue) {
		if (o == null)
			return defaultValue;
		return o.toString();
	}

	/**
	 * Convert to short.
	 * 
	 * @param o
	 * @return
	 */
	public static short toShort(Object o) {
		if (o == null)
			return 0;
		try {
			return Short.parseShort(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}

	/**
	 * Convert to short. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static short toShort(Object o, short defaultValue) {
		if (o == null)
			return defaultValue;
		try {
			return Short.parseShort(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return defaultValue;
		}
	}

	/**
	 * Convert to int.
	 * 
	 * @param o
	 * @return
	 */
	public static int toInt(Object o) {
		if (o == null)
			return 0;
		try {
			return Integer.parseInt(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return 0;
		}
	}

	/**
	 * Convert to int. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static int toInt(Object o, int defaultValue) {
		if (o == null)
			return defaultValue;
		try {
			return Integer.parseInt(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return defaultValue;
		}
	}

	/**
	 * Convert to long.
	 * 
	 * @param o
	 * @return
	 */
	public static long toLong(Object o) {
		if (o == null)
			return 0L;
		try {
			return Long.parseLong(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return 0L;
		}
	}

	/**
	 * Convert to long. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static long toLong(Object o, long defaultValue) {
		if (o == null)
			return defaultValue;
		try {
			return Long.parseLong(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return defaultValue;
		}
	}

	/**
	 * Convert to float.
	 * 
	 * @param o
	 * @return
	 */
	public static float toFloat(Object o) {
		if (o == null)
			return 0.0f;
		try {
			return Float.parseFloat(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return 0.0f;
		}
	}

	/**
	 * Convert to float. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static float toFloat(Object o, float defaultValue) {
		if (o == null)
			return defaultValue;
		try {
			return Float.parseFloat(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return defaultValue;
		}
	}

	/**
	 * Convert to double.
	 * 
	 * @param o
	 * @return
	 */
	public static double toDouble(Object o) {
		if (o == null)
			return 0.0d;
		try {
			return Double.parseDouble(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return 0.0d;
		}
	}

	/**
	 * Convert to double. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static double toDouble(Object o, double defaultValue) {
		if (o == null)
			return defaultValue;
		try {
			return Double.parseDouble(o.toString());
		} catch (NumberFormatException e) {
			log.error(e);
			return defaultValue;
		}
	}

	/**
	 * Convert to BigDecimal.
	 * 
	 * @param o
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object o) {
		if (o == null)
			return BigDecimal.ZERO;
		try {
			return BigDecimal.valueOf(toDouble(o));
		} catch (NumberFormatException e) {
			log.error(e);
			return BigDecimal.ZERO;
		}
	}

	/**
	 * Convert to BigDecimal. (with default value)
	 * 
	 * @param o
	 * @param defaultValue
	 * @return
	 */
	public static BigDecimal toBigDecimal(Object o, double defaultValue) {
		if (o == null)
			return BigDecimal.valueOf(defaultValue);
		try {
			return BigDecimal.valueOf(toDouble(o));
		} catch (NumberFormatException e) {
			log.error(e);
			return BigDecimal.valueOf(defaultValue);
		}
	}

	/**
	 * Convert to boolean.
	 * 
	 * @param o
	 * @return
	 */
	public static boolean toBoolean(Object o) {
		if (o == null)
			return false;

		String s = o.toString();

		if (s.equals("") || s.equals("0") || s.equalsIgnoreCase("false"))
			return false;

		return true;
	}

	/**
	 * Convert to Date,only support the format "yyyy-MM-dd HH:mm:ss"
	 * 
	 * @param o
	 * @return
	 */
	public static Date toDate(Object o) {
		if (o == null)
			return null;

		String s = o.toString();
		Date retValue = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			retValue = sdf.parse(s);
		} catch (ParseException e) {
			log.error(e);
		}
		return retValue;
	}

	/**
	 * Convert to Date by your support date format string, such as
	 * "yyyy-MM-dd HH:mm:ss" "yyyyMMddHHmmss" "yyyy/MM/dd HH:mm:ss" etc.
	 * 
	 * @param o
	 * @param dateFormatStr
	 * @return
	 */
	public static Date toDate(Object o, String dateFormatStr) {
		if (o == null)
			return null;

		String s = o.toString();
		Date retValue = null;

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
			retValue = sdf.parse(s);
		} catch (ParseException e) {
			log.error(e);
		} catch (Exception exp) {
			log.error(exp);
		}
		return retValue;
	}

	/**
	 * format date to string
	 * 
	 * @param date
	 * @param dateFormatStr
	 * @return
	 */
	public static String toDateString(Object date, String dateFormatStr) {
		if (date == null)
			return null;

		String retValue = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormatStr);
			retValue = sdf.format(date);
		} catch (Exception exp) {
			log.error(exp);
		}
		return retValue;
	}

	/**
	 * 杞崲涓�?son string
	 * 
	 * @param o
	 *            寰呰浆鎹㈠璞�
	 * @return json string,鍙戠敓寮傚父杩斿洖绌哄瓧绗︿�?
	 */
	public static String toJsonString(Object o) {
		return toJsonString(o, "");
	}

	/**
	 * 杞崲涓�?son string
	 * 
	 * @param o
	 *            寰呰浆鎹㈠璞�
	 * @param defaultValue
	 *            榛樿杩斿洖鍊�
	 * @return json string,鍙戠敓寮傚父杩斿洖defaultValue
	 */
	public static String toJsonString(Object o, String defaultValue) {
		try {
			return JSON.toJSONString(o);
		} catch (Exception ex) {
			log.error(ex);
			return defaultValue;
		}
	}

	/**
	 * MD5鍔犲�?
	 * 
	 * @param text
	 *            寰呭姞�?�嗗瓧绗︿覆
	 * @return MD5鐮�
	 */
	public static byte[] toMD5(String text) {
		return MD5(text);
	}

	public final static byte[] MD5(String s) {
		try {
			byte[] btInput = s.getBytes();
			// 鑾峰緱MD5鎽樿绠楁硶鐨�MessageDigest 瀵硅�?
			MessageDigest mdInst = MessageDigest.getInstance("MD5");
			// 浣跨敤鎸囧畾鐨勫瓧鑺傛洿鏂版憳瑕�?
			mdInst.update(btInput);
			// 鑾峰緱�?�嗘�?
			byte[] md = mdInst.digest();
			// 鎶婂瘑鏂囪浆鎹㈡垚鍗佸叚杩涘埗鐨勫瓧绗︿覆褰㈠紡
			return md;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 鑾峰彇鏈�皬鏃堕�?
	 * 
	 * @return 1900-01-01 00:00:00
	 */
	public static Date getDateMinValue() {
		if (dateMinValue == null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				return sdf.parse("1900-01-01 00:00:00");
			} catch (Exception ex) {
				return new Date();
			}
		} else {
			return dateMinValue;
		}
	}

	/**
	 * 鑾峰彇鏈�ぇ鏃堕�?
	 * 
	 * @return 2099-12-31 00:00:00
	 */
	public static Date getDateMaxValue() {
		if (dateMaxValue == null) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				return sdf.parse("2099-12-31 00:00:00");
			} catch (Exception ex) {
				return new Date();
			}
		} else {
			return dateMaxValue;
		}
	}

	public static void main(String[] args) {
		System.out.println(toBigDecimal(10000.6666666666).toString());
	}
}
