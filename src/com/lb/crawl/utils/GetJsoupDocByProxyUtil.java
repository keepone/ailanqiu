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

import com.lb.utils.GetParameterUtil;
import com.lucene.crawl.GetProxyIpUtil;


public class GetJsoupDocByProxyUtil {
	public  Integer   count=1;
	public   Integer sum=1;
	private static final String CHARSET = "UTF-8";
	final static String agent = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public static void main(String []args) throws Exception{
	 
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
	public  Document getDoc(String getUrl, String host, int port,String userAgent) throws Exception {
		StringBuffer result = new StringBuffer();
		Document doc=null;
		System.out.println("我又进来了，现在sum为："+sum);
//		  int   count=1;
//		  int sum=1;
		/* 1 生成 HttpClinet 对象并设置参�?*/
		HttpClient httpClient = new HttpClient();
	 
		/* 代理的主�?*/
		ProxyHost proxy = new ProxyHost(host, port);

		/* 使用代理 */
		httpClient.getHostConfiguration().setProxyHost(proxy);

		/* 添加 userAgent */
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT, userAgent);

		/* 读取超时 */
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(3000); 
		//	链接超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(3000);

		/* 2 生成 GetMethod 对象并设置参�?*/
		GetMethod getMethod = new GetMethod(getUrl);
		// 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 3000);
		getMethod.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 3000);


		// 设置 get 请求超时�?60 �?
		getMethod.getParams().setSoTimeout(12000);  //时间太短很可能报： Read timed out
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
			//str=new String(str.getBytes("ISO-8859-1"),"UTF-8");
			 
			doc=Jsoup.parse(str);
			 
			
			
		} catch (Exception e) {		
			if(sum<=5){
				System.out.println("当前为1级错误,当前循环第几次："+sum);
				JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		doc=getDoc(getUrl,ip2,port2,userAgent);
        		sum++;
			}else if(sum>5&&sum<=10){
				System.out.println("当前为2级错误,停止20秒，当前循环第几次："+sum);
				Thread.sleep(20000); //停止20秒
				JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		doc=getDoc(getUrl,ip2,port2,userAgent);
        		sum++;
			}else if(sum>10&&sum<=12){
				System.out.println("当前为3级错误,停止1分钟，当前循环第几次："+sum);
				Thread.sleep(60000);  //停止1分钟
				String proxy_url = "http://www.xici.net.co/";
        		GetProxyIpUtil.getProxyIps(proxy_url);
        		JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		doc=getDoc(getUrl,ip2,port2,userAgent);
        		sum++;
			}else{
				System.out.println("当前为4级错误,停止2分钟，当前循环第几次："+sum);
				Thread.sleep(120000);  //停止2分钟
				String proxy_url = "http://www.xici.net.co/";
        		GetProxyIpUtil.getProxyIps(proxy_url);
        		JSONObject json=GetProxyIpUtil.readOneProxyIP();
        		String ip2=json.getString("ip");
        		int port2=json.getInt("port");
        		doc=getDoc(getUrl,ip2,port2,userAgent);
        		sum=new Integer(0);
        		System.out.println("从头开始："+sum);;  //从头开始
			}
			
		}finally {
			if(getMethod != null)
				getMethod.releaseConnection();
				/* 6 .释放连接 */
			 
				
		}
		return doc;
	}
}
