package com.processinformation.base.util;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 订单组签名或验证签名方法
 */
public class EncryptUtil {
	/**
	 * 创建md5摘要,规则�?:按参数名称a-z排序,遇到空�?�的参数不参加签名�??
	 * 
	 * @param key
	 *            签名key
	 * @param parameters
	 *            SortedMap表示的待签名键�?�对
	 * @param charset
	 *            String表示的字符编�?
	 * @return 返回签名
	 */
	public static String createSign(String key, SortedMap parameters,
			String charset) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		String sign = mD5Encode(sb.toString(), charset).toLowerCase();
		return sign;
	}

	/**
	 * 是否签名正确,规则�?:按参数名称a-z排序,遇到空�?�的参数不参加签名�??
	 * 
	 * @param key
	 *            签名key
	 * @param parameters
	 *            SortedMap表示的待验证签名键�?�对
	 * @param charset
	 *            String表示的字符编�?
	 * @return 返回验证签名是否正确�?
	 */
	public static boolean checkSign(String key, SortedMap parameters,
			String charset) {
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + key);
		// 算出摘要
		String newSign = mD5Encode(sb.toString(), charset).toLowerCase();
		String sourceSign = getSign(parameters).toLowerCase();
		return sourceSign.equals(newSign);
	}

	/**
	 * 获取键为sign的�??
	 * 
	 * @param parameters
	 *            SortedMap表示的键值对
	 * @return 签名
	 */
	public static String getSign(SortedMap parameters) {
		String s = (String) parameters.get("sign");
		return (null == s) ? "" : s;
	}

	private static String byteArrayToHexString(byte b[]) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++)
			resultSb.append(byteToHexString(b[i]));

		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n += 256;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	private static String mD5Encode(String origin, String charset) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charset == null)
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes()));
			else
				resultString = byteArrayToHexString(md.digest(resultString
						.getBytes(charset)));
		} catch (Exception exception) {
		}
		return resultString;
	}

	private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };
	
	public static String encryptDes(String src){
		try {
			SecretKey deskey = new SecretKeySpec(create3DesKey(CRYPT_KEY), KEY_ALGORITHM);
			Cipher c1 = Cipher.getInstance(CIPHER_ALGORITHM);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return new BASE64Encoder().encode(c1.doFinal(src.getBytes("UTF-8")));
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	public static String decryptDes(String src){
		try {
			SecretKey deskey = new SecretKeySpec(create3DesKey(CRYPT_KEY), KEY_ALGORITHM);
			Cipher c1 = Cipher.getInstance(CIPHER_ALGORITHM);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(new BASE64Decoder().decodeBuffer(src)), "UTF-8");
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	private static byte[] create3DesKey(String keyStr){
		try {
			byte[] key = new byte[24];
			byte[] t_str = keyStr.getBytes("UTF-8");
			if(key.length > t_str.length){
				System.arraycopy(t_str, 0, key, 0, t_str.length);
			} else {
				System.arraycopy(t_str, 0, key, 0, key.length);
			}
			return key;
			
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
	
	private static final String KEY_ALGORITHM = "DESede";
	private static final String CIPHER_ALGORITHM = "DESede/ECB/PKCS5Padding";
	private static final String CRYPT_KEY = "@cHUnBo.COm_2014ENCRYPTutil.jAvaXUqiang@ChunBo.Com97796267";
	
}
