package com.processinformation.base.cache;


import org.springframework.beans.factory.InitializingBean;

import com.processinformation.base.util.ConvertUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;
/**
 * Redis瀹㈡埛锟�涓烘敮鎸佸绾跨▼鎯呭�?. 鐩墠浠呮敮鎸丼tring绫诲瀷鏁版嵁鐨勫瓨锟�?
 * Spring娉ㄥ叆锟�锟�锟斤拷缃甴ost,port,password,timeOut鍙傛�?,timeOut浠ユ绉掍负鍗曚�?
 * 
 * @date 2013-11-13 涓婂�?11:00:06
 * 
 */
public class RedisCache implements InitializingBean {
	private JedisPool pool;

	public String get(String key) {
		Jedis jedis = pool.getResource();
		try {
			return jedis.get(key);
		} catch (JedisConnectionException e) {
			pool.returnBrokenResource(jedis);
			return "";
		} finally {
			pool.returnResource(jedis);
		}
	}

	public boolean set(String key, String value) {
		Jedis jedis = pool.getResource();
		try {
			jedis.set(key, value);
			return true;
		} catch (JedisConnectionException e) {
			pool.returnBrokenResource(jedis);
			return false;
		} finally {
			pool.returnResource(jedis);
		}
	}

	private String host;
	private int port;
	private String password;
	private int timetOut;
	private int dbIndex;

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host
	 *            the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the timetOut
	 */
	public int getTimetOut() {
		return timetOut;
	}

	/**
	 * @param timetOut
	 *            the timetOut to set
	 */
	public void setTimetOut(int timetOut) {
		this.timetOut = timetOut;
	}
	
	public int getDbIndex()
	{
		return ConvertUtil.toInt(dbIndex,0);
	}

	public void setDbIndex(int dbIndex)
	{
		this.dbIndex = dbIndex;
	}
	
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port
	 *            the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if("".equals(password))
		{
			pool = new JedisPool(new JedisPoolConfig(), host, port, timetOut,null,getDbIndex());
		}
		else
		{
			pool = new JedisPool(new JedisPoolConfig(), host, port, timetOut,password,getDbIndex());
		}
	}
}
