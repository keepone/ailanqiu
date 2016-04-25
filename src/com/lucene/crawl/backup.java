package com.lucene.crawl;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class backup {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://roll.sports.sina.com.cn/s_cba_all/index.shtml";
		getEndVideoURL(url);

	}

	public static void getEndVideoURL(String startURL) {
		int count=1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis = doc.select("div.d_list_txt").select("li");
			for (Element li : lis) {
				String name = li.select("a[href]").first().text();
				if (name.indexOf("图文") == -1) {
					String href = li.select("a[href]").attr("href");
					if(href.indexOf("forum")!=-1||href.indexOf("blog")!=-1){
						//System.out.println(name);
					}else{
						getNewsDetail(href);
						count++;
					}
					
					
				}
			}
			System.out.println(count);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String getNewsDetail(String href){
		try {
			Document doc = Jsoup.connect(href).timeout(60000).get();
			//如果新闻包含视频
			if(doc.outerHtml().indexOf("url-data")!=-1){
				//System.out.println(doc.outerHtml());
				Element div=doc.select("div.a-p-s-item").first();
				String videoHref=div.attr("url-data");
				System.out.println(videoHref);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
