package com.lb.crawl.utils;

import org.jsoup.Jsoup;

import net.sf.json.JSONObject;

import com.lucene.crawl.GetProxyIpUtil;

public class Test {

	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		JSONObject json=GetProxyIpUtil.readOneProxyIP();
		String host=json.getString("ip");
		int port=json.getInt("port");
		
		Jsoup.connect("")
		
		String url="http://www.dianping.com/search/keyword/217/0_%E7%AF%AE%E7%90%83%E5%9C%BA";
		 
		try {
			//GetJsoupDocByProxyUtil.getDoc(url, host, port, USERAGENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
