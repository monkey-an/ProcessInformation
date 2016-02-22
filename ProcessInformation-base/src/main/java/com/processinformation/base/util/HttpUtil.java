package com.processinformation.base.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.util.StopWatch;

import com.processinformation.base.log.LogWriter;

public class HttpUtil
{

	public static String getPageHTML(String url)
	{
		return getPageHTML(url, Charset.defaultCharset(), true,30000);
	}
	
	public static String getPageHTML(String url,int timeOut)
	{
		return getPageHTML(url, Charset.defaultCharset(), true,timeOut);
	}

	public static String getPageHTML(String url, Charset charset, boolean writeAccessLog,int timeOut)
	{
		try
		{
			StopWatch sw = new StopWatch();
			sw.start();
			HttpClient httpclient = new DefaultHttpClient();
			//httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, timeOut);
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时�?
			httpget.setConfig(requestConfig);
			HttpResponse response = httpclient.execute(httpget);
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1)
			{
				baf.append((byte) current);
			}
			String result = new String(baf.toByteArray(), charset);
			is.close();
			sw.stop();
			LogWriter.writeInvokeApiMonitorLog(url, "", sw.getLastTaskTimeMillis());
			if (writeAccessLog == true)
			{
				LogWriter.writeInvokeApiAccessLog(url, "", result);
			}
			return result;
		} catch (Exception ex)
		{
			LogWriter.writeInvokeApiErrorLog(ex, url, "");
			return "";
		}
	}

	public static String getPageHTML(String url, Map<String, String> params, Charset charset, boolean writeAccessLog)
	{
		return getPageHTML(url, params, charset, writeAccessLog,30000);
	}
	
	public static String getPageHTML(String url, Map<String, String> params, Charset charset, boolean writeAccessLog,int timeOut)
	{
		String paramString = "";
		try
		{
			StopWatch sw = new StopWatch();
			sw.start();
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			Set set = params.entrySet();
			StringBuilder sb = new StringBuilder();
			for (Iterator iter = set.iterator(); iter.hasNext();)
			{
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				String value = (String) entry.getValue();
				nameValuePairs.add(new BasicNameValuePair(key, value));
				sb.append(String.format("%1$s=%2%s", key, value));
				sb.append("&");
			}
			paramString = sb.toString();
			if (paramString.endsWith("&"))
			{
				paramString.substring(0, paramString.length() - 1);
			}
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时�?
			httppost.setConfig(requestConfig);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs, charset));
			HttpResponse response = httpclient.execute(httppost);
			InputStream is = response.getEntity().getContent();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(20);
			int current = 0;
			while ((current = bis.read()) != -1)
			{
				baf.append((byte) current);
			}
			String result = new String(baf.toByteArray(), charset);
			is.close();
			sw.stop();
			LogWriter.writeInvokeApiMonitorLog(url, paramString, sw.getLastTaskTimeMillis());
			if (writeAccessLog)
			{
				LogWriter.writeInvokeApiAccessLog(url, paramString, result);
			}
			return result;
		} catch (Exception ex)
		{
			LogWriter.writeInvokeApiErrorLog(ex, url, paramString);
			return "";
		}
	}
}
