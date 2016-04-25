package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.jiutong.test.Log;

public class GetPlayerVideoByNBA98 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startURL = "http://www.nba98.com/streetball/list_184_";
		getVideoByNBA98(startURL);
		 

	}
	public static JSONArray getVideoByNBA98(String startURL) {
		JSONArray jsonArray=new JSONArray();
		String endURL = ".html";
		int count=1;
		int f = 1;
		System.out.println("爬虫正在启动…………");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("爬虫开始抓取…………");
		long start=System.currentTimeMillis();
		for (int i = 1; i <=45; i++) {
			String url=new String();
			url=startURL+i+endURL;
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				Elements elements=doc.select("div.picList").select("li");
				for(Element element:elements){
					JSONObject jsonObject=new JSONObject();
					String video_href=element.select("a[href]").first().attr("abs:href");
					video_href=getThirdVideo(video_href);
					if(video_href.length()>500){
						video_href="链接太长";
					}
					String video_name=element.select("a[href]").first().attr("title");
					String img_href=element.select("img").first().attr("abs:src");
					jsonObject.put("href", video_href);
					jsonObject.put("img", img_href);
					jsonObject.put("name", video_name);
					jsonArray.add(jsonObject);
					System.out.println(video_name);
					System.out.println("download-------"+i);
					count=count+1;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		long end=System.currentTimeMillis();
		System.out.println("user time--"+(end-start)/1000);
		System.out.println("download video--"+count);
		Logger logger = Logger.getLogger(Log.class);
		Logger log = Logger.getLogger("myTest1");  
    	log.info("爬虫抓取用时:--------"+(end-start)/1000);  
		return jsonArray;
	 

	}

	public static String getThirdVideo(String video_href) {
		String third_href=null;
		Document doc = null;
		try {
			doc = Jsoup.connect(video_href).timeout(60000).get();
			 third_href=doc.select("div.Content-body").select("a[href]").first().attr("href");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return third_href;
	}
}
