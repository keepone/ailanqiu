package com.lb.crawl.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.lucene.crawl.GetProxyIpUtil;


public class GetJsoupDocUtil {
	private static int   count=1;
	private static int sum=1;
	private static final String CHARSET = "gbk";
	final static String agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public static void main(String []args) throws Exception{
		getDoc("http://sports.qq.com/l/basket/nba/newsmore.htm", "14.18.16.67", 80, agent);
	}
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
	public static Document getDoc(String getUrl, String host, int port,String userAgent) throws Exception {
		StringBuffer result = new StringBuffer();
		Document doc=null;
		/* 1 生成 HttpClinet 对象并设置参�?*/
		HttpClient httpClient = new HttpClient();
	 
		/* 代理的主�?*/
		ProxyHost proxy = new ProxyHost("183.136.152.26", 80);

		/* 使用代理 */
		httpClient.getHostConfiguration().setProxyHost(proxy);

		/* 添加 userAgent */
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);

		/* 链接超时*/
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000); 
		//	读取超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);

		/* 2 生成 GetMethod 对象并设置参�?*/
		GetMethod getMethod = new GetMethod(getUrl);
		// 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 10000);
		getMethod.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 10000);


		// 设置 get 请求超时�?60 �?
		getMethod.getParams().setSoTimeout(2000);  //时间太短很可能报： Read timed out
		// 设置请求重试处理，用的是默认的重试处理：请求三次
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
		/* 3 执行 HTTP GET 请求 */
		try {
			int statusCode = httpClient.executeMethod(getMethod);
			if (statusCode != HttpStatus.SC_OK) {
				throw new Exception();
			}
			 
		
			 
			InputStream ins = getMethod.getResponseBodyAsStream();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader(ins,CHARSET));
			StringBuffer sbf = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null)
			{
			sbf.append(line);
			}

			br.close();
			String str=sbf.toString();
			//System.out.println(sbf.toString());
			doc=Jsoup.parse(str);
			System.out.println(doc);
			
			
		} catch (Exception e) {
			// 发生致命的异常，可能是协议不对或者返回的内容有问�?
			if(sum<=5){
        		if(count<=3){
        			System.out.println("网络故障，再次发起链接当前count："+count+"当前sum："+sum);
            		count++;
            		sum++;
            		
            		getDoc(getUrl,host,port,userAgent);
            		
            	}else{
            		//从本地读取ip
            		System.out.println("IP故障，读取最新ip,当前count："+count+"当前sum："+sum);
            		count=0;
//            		JSONObject json=GetProxyIpUtil.readOneProxyIP();
//            		String ip2=json.getString("ip");
//            		int port2=json.getInt("port");
            		sum++;
            		
            		getDoc(getUrl,host,port,userAgent);
            		
            	}
        	
        	}else if(sum==7){
        		System.out.println("放弃本次操作");
        		System.out.println("爬虫重新抓取最新ip,再次发起链接当前count："+count+"当前sum："+sum);
        		return doc;
        	}else{
        		//重新抓取最新的代理ip
        		System.out.println("爬虫重新抓取最新ip,再次发起链接当前count："+count+"当前sum："+sum);
//        		GetProxyIpUtil.getProxyIps("http://www.xici.net.co/");
//        		JSONObject json=GetProxyIpUtil.readOneProxyIP();
//        		String ip2=json.getString("ip");
//        		int port2=json.getInt("port");
        		sum++;
        		
        		getDoc(getUrl,host,port,userAgent);
        	}
		}finally {
			if(getMethod != null)
				getMethod.releaseConnection();
				/* 6 .释放连接 */
				
		}
		return doc;
	}
}
