package com.lb.crawl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class FilterIP {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public static void main(String[] args) {
		readTxtFileAsList("/Users/Allen/Downloads/ipLibrary.txt");

	}
	/**
	 * 读取文件
	 * @param filePath
	 */
	public static List<String> readTxtFileAsList(String filePath) {
		File file = new File(filePath);
		List<String> ips=new ArrayList<String>();
		BufferedReader reader = null;
		
			try {
				reader = new BufferedReader(new FileReader(file));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String line = null;
			try {
				int count=1;
				long start_time=System.currentTimeMillis();
				while((line = reader.readLine())!=null){
					if(!line.equals("")){	
						System.out.println("当前正在扫描第：－－－－－－"+count+"个ip");
						count++;
						String[] strUrl = line.split("@");
						String ip_port=strUrl[0];
						String [] ipurl=ip_port.split(":");
						String ip=ipurl[0];
						String port=ipurl[1];
						
						try {
							String result=getHttpRequest("http://m.1688.com", ip, Integer.parseInt(port), USERAGENT);
							if(result.equals("ok")){
								ips.add(line);
								System.out.println(ip+":"+port);
								long end=System.currentTimeMillis();
								System.out.println("用时："+(end-start_time)/1000);
								break;
							}
						} catch (NumberFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (Exception e) {
							System.out.println("无效ip，当前count为－－－－－－－－"+(count-1));
						}
						
						
						
						//System.out.println(line);
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		return ips;

	}
	
	
	public static String getHttpRequest(String getUrl, String host, int port,String userAgent) throws Exception {
		String result = new String();

		/* 1 生成 HttpClinet 对象并设置参�?*/
		HttpClient httpClient = new HttpClient();

		/* 代理的主�?*/
		ProxyHost proxy = new ProxyHost(host, port);

		/* 使用代理 */
		httpClient.getHostConfiguration().setProxyHost(proxy);

		/* 添加 userAgent */
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);

		/* 链接超时*/
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(1000); 
		//	读取超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(1000);

		/* 2 生成 GetMethod 对象并设置参�?*/
		GetMethod getMethod = new GetMethod(getUrl);
		// 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 1000);
		getMethod.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 1000);


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
		return "ok";
	}
}
