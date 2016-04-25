package com.lb.crawl.utils;

import java.util.concurrent.TimeUnit;

import org.jsoup.nodes.Document;

public class IPTest {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";

	public static void main(String [] args){
		String host="183.221.160.110";
		String port="8123";
		try {
			Document doc=GetJsoupDocUtil.getDoc("http://nba.sports.sina.com.cn/match_result.php", host,Integer.parseInt(port), USERAGENT);
			if(doc!=null){
				System.out.println(doc);
			}else{
				System.out.println("本次抓取失败");
				return;
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	 

}
