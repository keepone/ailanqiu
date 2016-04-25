package com.lucene.crawl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.ProxyHost;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.lb.utils.IoUtil;
 

public class GetProxyIpUtil {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://www.xici.net.co/";
		// String str=getProxyIps(url);
		// IoUtil.writeFile("/Users/Allen/tomcat7/", "ip", str);
		// System.out.println(str);

//		List<String> ips = IoUtil
//				.readTxtFileAsList("/Users/Allen/tomcat7/ips.txt");
//		System.out.println(ips.size());
//		System.out.println(ips.get(19));
//		
		//getProxyIps(url);
		readOneProxyIP();

	}

	public static String getProxyIps(String startURL) {
		String Str = "";
		String goodIp="";
		String goodPort="";
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).userAgent("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)").timeout(60000).get();
			// System.out.println(doc);
			int count = 1;
			Elements trs = doc.select("tr");
			for (Element tr : trs) {
				if (count <= 10) {
					if (tr.outerHtml().indexOf("高匿") != -1
							&& tr.outerHtml().indexOf("国内高匿") == -1) {
						Elements tds = tr.select("td");
						int td_count = 1;
						String ip="";
						String port="";
						for (Element td : tds) {
							if (td_count == 2) {
								  ip = td.ownText();
								
								// System.out.println(ip);
							}
							if (td_count == 3) {
								  port = td.ownText();
								
								// System.out.println(ip);
							}
							
							td_count++;
						}
						int po=Integer.parseInt(port);
						try {
							
							boolean status=ifGoodIp("http://www.dianping.com/", ip, po,USERAGENT);
							if(status){
								System.out.println("好"+ip);
								Str = Str + ip;
								Str = Str + ":" + port;
								Str = Str + "\r\n";
								//System.out.println(Str);
								count++;
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}

				} else {
					break;
				}
			}

			System.out.println("下载ip：" + (count - 1));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		IoUtil.writeFile("/Users/Allen/tomcat7/", "ips", "");
		IoUtil.writeFile("/Users/Allen/tomcat7/", "ips", Str);
		return Str;
	}

	
	public static void getProxyIps_2(String startURL) {

 
		  HttpClient client = new HttpClient();
	      // 设置代理服务器地址(URL)和端口
	      client.getHostConfiguration().setHost( "http://www.xici.net.co/" , 80);
	      
	      GetMethod method=new GetMethod( "http://www.xici.net.co/");
	      
	      
	    //  System.out.println(method.getStatusText());
	      try {
	      	  method.getParams().setSoTimeout(1000);
	            method.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 1000);


	    		// 设置 get 请求超时�?60 �?
	            method.getParams().setSoTimeout(200);
	    		// 设置请求重试处理，用的是默认的重试处理：请求三次
	            method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
	    		/* 3 执行 HTTP GET 请求 */
	    		
	    			int statusCode = client.executeMethod(method);
	    			if (statusCode != HttpStatus.SC_OK) {
	    				throw new HttpException();
	    			}
	    		 
	    			System.out.println(method.getResponseBodyAsString());
	    			
	          client.executeMethod(method);
	          
	        
	  			
	          
	          //15674975284
	          //System.out.println(method.getStatusLine());   //打印结果页面
	           String response=new String(method.getResponseBodyAsString());

//	         System.out.println(response);
	           //释放连接
	           method.releaseConnection();

	           //解析页面内容
	           Document doc= Jsoup.parse(response); //从字符串中加载
	      }catch (IOException e) {
	        	 
	        	}
	           // e.printStackTrace();
	        }
	
	
	
           
 
   private static HttpMethod getGetMethod(){
      return new GetMethod("/index.php?simcard=1330227");
   }
    
	public static JSONObject readOneProxyIP() {
		String proxy_url = "http://www.xici.net.co/";
		String path = "/Users/Allen/tomcat7/ips.txt";
		
		String str ="";
		 
		List<String> strs = IoUtil.readTxtFileAsList(path);
		if (strs.size() == 0) {
			 str = GetProxyIpUtil.getProxyIps(proxy_url);
			IoUtil.writeFile("/Users/Allen/tomcat7/", "ips", str);
			strs = IoUtil.readTxtFileAsList(path);
		}
		int i = (int) (Math.random() * 10);
		 str = strs.get(i);
		int index = str.indexOf(":");
		String ip = str.substring(0, index);
		String port = str.substring(index + 1, str.length());
		JSONObject json = new JSONObject();
		System.out.println("本次读取IP---"+ip+":"+port);
		json.put("ip", ip);
		json.put("port", port);
		return json;
	}
	
	
	
	public static boolean ifGoodIp(String getUrl, String host, int port,String userAgent) throws Exception {
		StringBuffer result = new StringBuffer();
		boolean flag=true;
		Document doc=null;
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

		/* 链接超时*/
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(2000); 
		//	读取超时
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(2000);

		/* 2 生成 GetMethod 对象并设置参�?*/
		GetMethod getMethod = new GetMethod(getUrl);
		// 请求超时
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 2000);
		getMethod.getParams().setParameter(HttpMethodParams.HEAD_BODY_CHECK_TIMEOUT, 2000);


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
			 
		
			 
			
			
		} catch (Exception e) {
			 flag=false;
			 System.out.println(host);
		}finally {
			if(getMethod != null)
				getMethod.releaseConnection();
				/* 6 .释放连接 */
			 
				
		}
		return flag;
	}
}
