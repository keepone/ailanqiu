package com.lb.crawl.utils;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class JsoupModel {

	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	public static void main(String[] args) {
		for(int i=1;i<=1;i++){
			String url="http://www.xici.net.co/";
			get_2(url);
		}
		//useUsereAgent();
	}
	
	public static void get(){
		try {
			Document doc=Jsoup.connect("http://weibo.com/verylb/profile?rightmod=1&wvr=6&mod=personnumber").userAgent(USERAGENT).timeout(10000).get();
			System.out.println(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void get_2(String url){
		try {
			Document doc=Jsoup.connect(url).timeout(10000).get();
			System.out.println(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void useUsereAgent(){
		try {
			Document doc=Jsoup.connect("http://tv.sohu.com/20141223/n407219178.shtml").userAgent(phoneUserAgent).timeout(10000).get();
			System.out.println(doc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
