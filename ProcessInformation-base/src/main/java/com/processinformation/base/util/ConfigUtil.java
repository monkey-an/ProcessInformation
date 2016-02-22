package com.processinformation.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.processinformation.base.log.LogWriter;

/**
 * 鐢ㄤ簬鑾峰彇椤圭洰閰嶇疆鏂囦欢鍙婄▼搴忔牴璺緞
 *
 */
public class ConfigUtil {
	private static String webRootPath;
	private static Properties props = new Properties();
	static {
		webRootPath = getWebrootPath();
		initAllConfig();
	}

	private static void initAllConfig() {
		try {
			Properties tempProps = new Properties();
			String path = (new ConfigUtil()).getClass().getClassLoader()
					.getResource("").getPath();
			path = path.concat("configs");
			File configFolder = new File(path);
			FilenameFilter filter = new PropertiesFileFilter();
			File[] propertiesFiles = configFolder.listFiles(filter);
			for (File propertiesFile : propertiesFiles) {
				if (propertiesFile.getName().toLowerCase().contains("ds")
						|| propertiesFile.getName().toLowerCase()
								.contains("datasource")) {
					continue;
				}
				tempProps = new Properties();
				InputStream in = new BufferedInputStream(new FileInputStream(
						propertiesFile));
				tempProps.load(in);
				props.putAll(tempProps);
			}
		} catch (Exception ex) {
			LogWriter.writeErrorLog(ex);
		}
	}

	/**
	 * 浠巆lsspath涓媍onfigs鐩綍涓嬬殑鎵�湁properties閰嶇疆鏂囦欢涓幏鍙栭厤缃�
	 * 
	 * @param key
	 *            key
	 * @return value,鎵句笉鍒扮粨鏋滆繑鍥炵┖瀛楃涓�?
	 */
	public static String getSettings(String key) {
		try {
			String result = props.getProperty(key);
			if (result == null) {
				return "";
			}
			return result;
		} catch (Exception ex) {
			return "";
		}
	}

	public static String getWebRootPath() {
		return webRootPath;
	}

	public static void setWebRootPath(String webRootPath) {
		ConfigUtil.webRootPath = webRootPath;
	}

	/**
	 * 鑾峰彇椤圭洰鏍硅矾寰�?
	 * 
	 * @return
	 */
	public final static String getWebrootPath() {
		String root = ConfigUtil.class.getResource("/").getFile();
		try {
			root = new File(root).getParentFile().getParentFile()
					.getCanonicalPath();
			root += File.separator;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return root;
	}
}

class PropertiesFileFilter implements FilenameFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
	 */
	@Override
	public boolean accept(File dir, String name) {
		return name.toLowerCase().endsWith("properties");
	}

}
