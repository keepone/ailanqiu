package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 

public class GetNBATeamInfoBy163 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startURL = "http://nba.sports.163.com/2014/team/home/";
		// get("http://search.sina.com.cn/?q=&range=title&c=news&sort=time&SL2=nbavideo&PID=");
		getEndVideoURL(startURL);
		/*
		 * String ZZ="马云"; try{ String str = "\u53d6"; byte [] A
		 * =str.getBytes("GB2312"); System.out.println(A.toString());
		 * }catch(java.io.UnsupportedEncodingException e){ }
		 */

	}

	 

	public static void getEndVideoURL(String startURL) {
		String endURL = ".html";
		int count=1;
		int f = 1;

		for (int i = 1; i <= 30; i++) {
			String url=new String();
			url=startURL+i+endURL;
			String savePath = "E:\\personal\\新浪视频\\" + f+"\\";
			
			
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				String img = doc.select("img.img2").attr("abs:src");
				System.out.println(img);
				String teamName=doc.select("span.f-teamc").first().ownText();
				String teamInroduce=doc.select("div.intr-con").last().ownText();
				String area=doc.select("span.f14px").first().ownText();
				if(area.indexOf("西部赛区")!=-1){
					area="西部";
				}else{
					area="东部";
				}
				String text=doc.select("span.nor-dblue").first().ownText().trim();
				int index_coach=text.indexOf("主教练");
				int index_boss=text.indexOf("大老板");
				int index_manage=text.indexOf("总经理");
				int index_address=text.indexOf(" 球队地址");
				int index_areaDetail=text.indexOf("赛区");
				String home_court=text.substring(0,index_coach).replace("主 场：", "");
				String coach=text.substring(index_coach,index_boss).replace("主教练：", "");
				String boss=text.substring(index_boss,index_manage).replace("大老板：", "");;
				String manage=text.substring(index_manage,index_address).replace("总经理：", "");
				String address=text.substring(index_address,index_areaDetail).replace("球队地址：", "");
				String area_detail=text.substring(index_areaDetail,text.length()).replace("赛区", "");
				System.out.println(text);
				 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		System.out.println("have download"+count);
	 

	}

	public static void get(String LOGIN_URL) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "iverson");
		Connection conn = Jsoup.connect(LOGIN_URL);
		conn
				.header("(Request-Line)",
						"POST /cgi-bin/login?lang=zh_CN HTTP/1.1");
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate");
		conn.header("Accept-Language", "zh-cn");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "Keep-Alive");
		conn.header("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		conn.header("Host", "mp.weixin.qq.com");
		conn.header("Referer", "https://sina.com.cn/");
		conn
				.header("User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
		try {
			Response response = conn.execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
