package com.processinformation.base.log;

import org.apache.log4j.Logger;

import com.processinformation.base.util.StringUtil;
/**
 * 鏂囨湰log宸ュ叿绫�?
 *
 */
public class LogWriter {
	private static Logger logger = Logger.getLogger(LogWriter.class);

	/**
	 * 鍐檈rror log,灏唌sg鍐欏叆error log
	 * 
	 * @param msg
	 *            String绫诲瀷鐨勫弬鏁�
	 */
	public static void writeErrorLog(String msg) {
		try {
			logger.error(msg.trim());
		} catch (Exception ex) {
			logger.error("Write Error Log encount exception:" + ex.getMessage());
		}
	}

	/**
	 * 鍐檈rror log,灏嗗紓甯哥殑淇℃伅鍜屾渶澶�0灞傚爢鏍堜俊鎭啓鍏rror log
	 * 
	 * @param ex
	 *            Exception绫诲瀷鐨勫弬鏁�
	 */
	public static void writeErrorLog(Exception ex) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("exception - ");
			sb.append(ex.getMessage());
			sb.append(System.getProperty("line.separator"));
			StackTraceElement[] steList = ex.getStackTrace();
			for (int i = 0; i < steList.length; i++) {
				sb.append("\tat ");
				sb.append(steList[i].getClassName());
				sb.append(".");
				sb.append(steList[i].getMethodName());
				sb.append("(");
				sb.append(steList[i].getFileName());
				sb.append(":");
				sb.append(steList[i].getLineNumber());
				sb.append(")");
				sb.append(System.getProperty("line.separator"));
			}
			sb.append("---------------");
			logger.error(sb.toString().trim());
		} catch (Exception logEx) {
			logger.error("write error Log encount exception:"
					+ logEx.getMessage());
		}
	}

	/**
	 * 灏嗘暟鎹簱鐩稿叧寮傚父鍐欏叆error log
	 * 
	 * @param functionName
	 *            DangAspect涓娇鐢ㄦ椂涓篋AO灞傛柟娉曞悕,DBInstance涓娇鐢ㄦ椂涓簃apper statement id
	 * @param ex
	 *            鎹曡幏鐨勫紓甯�
	 */
	public static void writeDbErrorLog(String functionName, Throwable ex) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("db exception - ");
			sb.append(functionName);
			sb.append(" - ");
			sb.append(ex.getMessage());
			sb.append(System.getProperty("line.separator"));
			StackTraceElement[] steList = ex.getStackTrace();
			for (int i = 0; i < steList.length; i++) {
				sb.append("\tat ");
				sb.append(steList[i].getClassName());
				sb.append(".");
				sb.append(steList[i].getMethodName());
				sb.append("(");
				sb.append(steList[i].getFileName());
				sb.append(":");
				sb.append(steList[i].getLineNumber());
				sb.append(")");
				sb.append(System.getProperty("line.separator"));
			}
			sb.append("---------------");
			logger.error(sb.toString().trim());
		} catch (Exception logEx) {
			logger.error("write error Log encount exception:"
					+ logEx.getMessage());
		}
	}

	/**
	 * 灏嗚皟鐢╝pi鏃舵姏鍑虹殑寮傚父淇℃伅鍐欏叆error log
	 * 
	 * @param ex
	 *            鎹曡幏鐨勫紓甯镐俊鎭�?
	 * @param url
	 *            api鐨勮皟鐢ㄥ湴鍧�
	 * @param params
	 *            api鐨勮皟鐢ㄥ弬鏁�
	 */
	public static void writeInvokeApiErrorLog(Exception ex, String url,
			String params) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append("api exception - ");
			sb.append(url);
			sb.append(" - ");
			sb.append(params);
			sb.append(" - ");
			sb.append(ex.getMessage());
			sb.append(System.getProperty("line.separator"));
			StackTraceElement[] steList = ex.getStackTrace();
			for (int i = 0; i < steList.length; i++) {
				sb.append("\tat ");
				sb.append(steList[i].getClassName());
				sb.append(".");
				sb.append(steList[i].getMethodName());
				sb.append("(");
				sb.append(steList[i].getFileName());
				sb.append(":");
				sb.append(steList[i].getLineNumber());
				sb.append(")");
				sb.append(System.getProperty("line.separator"));
			}
			sb.append("---------------");
			logger.error(sb.toString().trim());
		} catch (Exception logEx) {
			logger.error("write error Log encount exception:"
					+ logEx.getMessage());
		}
	}

	private static void writeWorkLogMethod(String msg) {
		try {
			logger.log(MyLogLevel.WORK_LEVEL, msg);
		} catch (Exception ex) {
			logger.error("write work Log encount exception:" + ex.getMessage());
		}
	}

	private static void writeAccessLogMethod(String msg) {
		try {
			logger.log(MyLogLevel.ACCESS_LEVEL, msg);
		} catch (Exception ex) {
			logger.error("write access Log encount exception:"
					+ ex.getMessage());
		}
	}

	/**
	 * 鍐檃ccess log鏂规�?
	 * 
	 * @param msg
	 *            寰呭啓鍏og鐨勬枃鏈�?
	 */
	public static void writeAccessLog(String msg) {
		// writeAccessLogMethod(StringUtil.format("other - %1$s", msg));
		writeAccessLogMethod(StringUtil.format("other - {0}", msg));
	}

	/**
	 * 灏唓uartz璋冨害log鍐欏叆access log
	 * 
	 * @param msg
	 *            寰呭啓鍏og鐨勬枃鏈�?
	 */
	public static void writeQuartzAccessLog(String msg) {
		// writeAccessLogMethod(StringUtil.format("quartz - %1$s", msg));
		writeAccessLogMethod(StringUtil.format("quartz - {0}", msg));
	}

	/**
	 * 灏哸pi璋冪敤淇℃伅鍐欏叆access log
	 * 
	 * @param url
	 *            api鐨勫湴鍧�?
	 * @param paramString
	 *            api鍙傛暟缁勬垚鐨勯敭鍊煎瀛楃涓�?
	 * @param returnString
	 *            api璋冪敤鐨勮繑鍥炲�?
	 */
	public static void writeInvokeApiAccessLog(String url, String paramString,
			String returnString) {
		// writeAccessLogMethod(StringUtil.format("invokeapi - %1$s - %2$s - %3$s",
		// url, paramString, returnString));
		writeAccessLogMethod(StringUtil.format("invokeapi - {0} - {1} - {2}",
				url, paramString, returnString));
	}

	/**
	 * 閽堝API椤圭�?,璁板綍API璇锋�?
	 * 
	 * @param apiName
	 *            api鐨勫悕�?��
	 * @param msg
	 *            鍐欏叆log鐨勪俊鎭�?
	 */
	public static void writeApiRequestWorkLog(String apiName, String msg) {
		// writeWorkLogMethod(StringUtil.format("apirequest - %1$s - %2$s",
		// apiName,msg));
		writeWorkLogMethod(StringUtil.format("apirequest - {0} - {1}", apiName,
				msg));
	}

	/**
	 * 閽堝API椤圭�?,璁板綍API杩斿洖鍊�?
	 * 
	 * @param apiName
	 *            api鐨勫悕�?��
	 * @param msg
	 *            鍐欏叆log鐨勪俊鎭�?
	 * @param errorCode
	 *            errorCode杩斿洖鍊�?
	 * @param statusCode
	 *            statusCode杩斿洖鍊�?
	 */
	public static void writeApiResponseWorkLog(String apiName, String msg,
			int errorCode, int statusCode) {
		// writeWorkLogMethod(StringUtil.format("apiresponse - %1$s - errorcode:%2$s - statuscode:%3$s - %4$s",
		// apiName,errorCode,statusCode,msg));
		writeWorkLogMethod(StringUtil.format(
				"apiresponse - {0} - errorcode:{1} - statuscode:{2} - {3}",
				apiName, errorCode, statusCode, msg));
	}

	/**
	 * 閽堝浣�?笟椤圭洰,璁板綍浣�?笟寮�
	 * 
	 * @param jobName
	 *            浣滀笟鍚嶇�?
	 */
	public static void writeJobStartWorkLog(String jobName) {
		// writeWorkLogMethod(StringUtil.format("jobstart - %1$s", jobName));
		writeWorkLogMethod(StringUtil.format("jobstart - {0}", jobName));
	}

	/**
	 * 閽堝浣�?笟椤圭洰,璁板綍浣�?笟缁撴潫
	 * 
	 * @param jobName
	 *            浣滀笟鍚嶇�?
	 * @param total
	 *            浣滀笟澶勭悊鎬绘�?
	 * @param success
	 *            浣滀笟澶勭悊鎴愬姛鏁伴噺
	 * @param fail
	 *            浣滀笟澶勭悊澶辫触鏁伴噺
	 */
	public static void writeJobEndWorkLog(String jobName, int total,
			int success, int fail) {
		// writeWorkLogMethod(StringUtil.format("jobend - %1$s - total:%2$s - success:%3$s - fail:%4$s",
		// jobName,total,success,fail));
		writeWorkLogMethod(StringUtil.format(
				"jobend - {0} - total:{1} - success:{2} - fail:{3}", jobName,
				total, success, fail));
	}

	private static void writeMonitorLog(String msg) {
		try {
			logger.log(MyLogLevel.MONITOR_LEVEL, msg);
		} catch (Exception ex) {
			logger.error("write monitor Log encount exception:"
					+ ex.getMessage());
		}
	}

	/**
	 * 璁板綍鏁版嵁搴撹闂�鑳芥暟鎹�?
	 * 
	 * @param functionName
	 *            鍦�?�angAspect涓娇鐢ㄦ椂涓篋AO灞傚嚱鏁板悕,鍦�?�BInstance涓椂涓簃apper statement id
	 * @param elapse
	 *            鏁版嵁搴撴搷浣滆�鏃�?
	 */
	public static void writeDbMonitorLog(String functionName, long elapse) {
		// writeMonitorLog(StringUtil.format("dbelapsed %1$s %2$s",functionName,
		// elapse));
		writeMonitorLog(StringUtil.format("dbelapsed {0} {1}", functionName,
				elapse));
	}

	/**
	 * 璁板綍api璁块棶鎬ц兘鏁版嵁
	 * 
	 * @param url
	 *            api鍦板�?
	 * @param params
	 *            api鐨勫弬鏁�?
	 * @param elapse
	 *            api璁块棶鑰楁椂
	 */
	public static void writeInvokeApiMonitorLog(String url, String params,
			long elapse) {
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}
		// writeMonitorLog(StringUtil.format("apielapsed %1$s %2$s",url,
		// elapse));
		writeMonitorLog(StringUtil.format("apielapsed {0} {1}", url, elapse));
	}
}
