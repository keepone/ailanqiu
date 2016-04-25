package com.proxy;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;


public class NetworkAccess {

	final static String agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	
	/**
	 * 获得页面信息
	 * 
	 * @param getUrl
	 *            :请求的URL
	 * @param host
	 *            :主机IP
	 * @param port
	 *            :主机端口�?
	 * @param userAgent
	 * @return
	 * @throws Exception
	 */
	public static String getHttpRequest(String getUrl, String host, int port,String userAgent) throws Exception {
		StringBuffer result = new StringBuffer();

		/* 1 生成 HttpClinet 对象并设置参�?*/
		HttpClient httpClient = new HttpClient();

		/* 代理的主�?*/
		ProxyHost proxy = new ProxyHost(host, port);

		/* 使用代理 */
		httpClient.getHostConfiguration().setProxyHost(proxy);

		/* 添加 userAgent */
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);

		/* 链接超时*/
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(200); 
		//	读取超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(200);

		/* 2 生成 GetMethod 对象并设置参�?*/
		GetMethod getMethod = new GetMethod(getUrl);
		// 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 200);
		getMethod.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 200);


		// 设置 get 请求超时�?60 �?
		getMethod.getParams().setSoTimeout(200);
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception();
			}
			System.out.println(getMethod.getResponseBodyAsString());
		} catch (HttpException e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问�?
//			logger.error("Please check your provided http address!", e);
//			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			// 发生网络异常
//			logger.error("IOException", e);
//			e.printStackTrace();
			throw e;
		} finally {
			if(getMethod != null)
				getMethod.releaseConnection();
				/* 6 .释放连接 */
				
		}
		return result.toString();
	}
}
