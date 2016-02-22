package com.processinformation.base.dto;

import com.processinformation.base.util.ConvertUtil;
/**
 * xml杈撳嚭鏁版嵁瀵硅�?,缁ф壙DTO
 *
 */
public class ResultObject extends DTO {
	/**
	 * 浼犲叆xml鏍硅妭鐐瑰悕绉扮殑鏋勶拷?鍑芥�?
	 * 
	 * @param rootName
	 *            xml鏍硅妭鐐瑰悕锟�
	 */
	public ResultObject(String rootName) {
		super(rootName);
		this.m_content.put("errorCode", 0);
		this.m_content.put("statusCode", 0);
	}

	/**
	 * 鏃犲弬鏁扮殑鏋勶�??鍑芥�?,璁剧疆xml鏍硅妭鐐逛负resultObject
	 */
	public ResultObject() {
		super("resultObject");
		this.m_content.put("errorCode", 0);
		this.m_content.put("statusCode", 0);
	}

	/**
	 * 浼犲叆rows鍜宺ow鑺傜偣鍚嶇О鐨勬�?�閫犲嚱锟�
	 * 
	 * @param rows
	 *            rows鑺傜偣鍚嶇�?
	 * @param row
	 *            row鑺傜偣鍚嶇�?
	 */
	public ResultObject(String rows, String row) {
		super("resultObject", rows, row);
		this.m_content.put("errorCode", 0);
		this.m_content.put("statusCode", 0);
	}

	/**
	 * 浼犲叆xml鏍硅妭鐐瑰悕锟�rows鍜宺ow鑺傜偣鍚嶇О鐨勬�?�閫犲嚱锟�
	 * 
	 * @param rootName
	 *            xml鏍硅妭鐐瑰悕锟�
	 * @param rows
	 *            rows鑺傜偣鍚嶇�?
	 * @param row
	 *            row鑺傜偣鍚嶇�?
	 */
	public ResultObject(String rootName, String rows, String row) {
		super(rootName, rows, row);
		this.m_content.put("errorCode", 0);
		this.m_content.put("statusCode", 0);
	}

	/**
	 * 鑾峰彇statusCode
	 * 
	 * @return statusCode
	 */
	public int getStatusCode() {
		return ConvertUtil.toInt(this.m_content.get("statusCode"));
	}

	/**
	 * 璁剧疆statusCdoe
	 * 
	 * @param statusCode
	 */
	public void setStatusCode(int statusCode) {
		this.m_content.put("statusCode", statusCode);
	}

	/**
	 * 鑾峰彇errorCode
	 * 
	 * @return errorCode
	 */
	public int getErrorCode() {
		return ConvertUtil.toInt(this.m_content.get("errorCode"));
	}

	/**
	 * 璁剧疆errorCode
	 * 
	 * @param errorCode
	 */
	public void setErrorCode(int errorCode) {
		this.m_content.put("errorCode", errorCode);
	}
}
