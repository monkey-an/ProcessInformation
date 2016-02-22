package com.processinformation.base.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class IPUtil {

	private static final Log log = LogFactory.getLog(IPUtil.class);

	private static final String LOCALHOST_FORMAT = "127.0.0.1";
	private static Pattern localhostPattern = Pattern.compile(LOCALHOST_FORMAT);

	/**
	 * 获取Ip4地址. Support Windows/Linux
	 * 
	 * @return ip4
	 */
	public static String getLocalIP4Address() {
		String localIP4Address = "";
		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (netInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = netInterfaces
						.nextElement();
				Enumeration<InetAddress> addresses = netInterface
						.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address
							&& ip.getHostAddress().indexOf(".") != -1) {
						localIP4Address = ip.getHostAddress().trim();
						if (!localhostPattern.matcher(localIP4Address).find()) {
							return localIP4Address;
						}
					}
				}// end while
			}// end while
		} catch (SocketException e) {
			log.error(e);
		}
		return localIP4Address;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(IPUtil.getLocalIP4Address());
	}

}
