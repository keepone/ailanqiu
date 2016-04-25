package com.lucene.crawl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiutong.test.Log;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.IoUtil;
@Component
public class GetPlayerVideoByHupu {

	//@Scheduled(cron="0 46 16 * * ?")  
	public static void main(String[] args){
		go();
	}
	public static void go(){
		Integer faultCount=1;
		
		String categoryStart="http://v.hupu.com/nba";
		String init_path="E:"+File.separator+"personal"+File.separator+"虎扑视频"+File.separator;
		String img_savePath="D:/tomcat6/Tomcat 6.0/webapps/ROOT/video_img/";
		JSONArray jsonArray=getCategoryByHuPu(categoryStart);
		int count=1;
		System.out.println("爬虫正在启动…………");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("爬虫开始抓取…………");
		long start=System.currentTimeMillis();
	  for (int i = 0; i <jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			String category_name=json.get("name").toString();
			String category_url=json.get("url").toString();
			String directory=init_path+category_name+File.separator;
			//创建分类对应的文件夹
			IoUtil.createDirectory(directory);
			String ifAll=ifAll(category_url);
			if(!ifAll.equals("")){
				category_url=ifAll;
			}
			JSONArray jsonArray2=getPersonalVideoByCategory(category_url);
			System.out.println(jsonArray2.size());
			for(int j = 0; j < jsonArray2.size(); j++) {
				JSONObject json2 = (JSONObject) jsonArray2.get(j);
				String video_name=json2.get("name").toString();
				String video_url=json2.get("url").toString();
				video_url=getEndVideoURL(video_url);
				AnalyzeVideoUtil any=new AnalyzeVideoUtil();
				//List list=any.analyze(video_url);
				String video_img=json2.get("image").toString();
				long time=System.currentTimeMillis(); 
				IoUtil.downloadImage(video_img, "", img_savePath+time+".jpg"); //
				//视频解析，得到标清和高清视频
//				if(list!=null&&list.size()>0){
//					for(int k=0;k<list.size();k++){
//						video_url=video_url+"\r\n"+list.get(k).toString();
//					}
//					String content=video_url+"\r\n"+time+".jpg"+"\r\n"+count;
//					IoUtil.writeFile(directory, video_name, content);  //将数据写入文件
//					System.out.println(video_name+"---"+video_url+"--"+count);
//					count=count+1;
//				}else{
//					String content=video_url+"\r\n"+time+".jpg"+"\r\n"+faultCount;
//					IoUtil.writeFile("E:\\personal\\有广告虎扑视频\\", video_name, content);  //将数据写入文件
//					Logger logger = Logger.getLogger(Log.class);
//					Logger log = Logger.getLogger("myTest1");  
//		        	log.info("   名称:--------"+video_name);  
//		            log.info("   链接:--------"+video_url); 
//		            faultCount=faultCount+1;
//				}
//				
				
			} 
		} 
		//ifAll("http://v.hupu.com/nba/hot-day-1");
		long end=System.currentTimeMillis();
		System.out.println("总共抓取"+count+"个视频，用时"+(end-start)/1000);
		System.out.println("错误"+faultCount+"个视频");
		//String startURL="http://v.hupu.com/nba/new/teams";
		//getPersonalByHuPu(startURL);
		// getCategoryByHuPu(categoryStart);
	}
	public static JSONArray getPersonalVideoByCategory(String startURL){
		JSONArray jsonArray=new JSONArray();
		boolean mark=true;
		Document doc = null;
		try {
			 while(mark){
				 doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("div.p_video");
					 Elements elements2=elements.select("div.img_outer");
					 for(Element element:elements2){
						 Element e=element.select("a[href]").first();
						 Element img=element.select("img[src]").first();
						 String title=e.attr("title");
						 String href=e.attr("href");

						 String image=img.attr("src");
						 if(title!=null&&!title.equals("")){
							 if(title.indexOf("?")!=-1){
								 title=title.replace("?", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("\"")!=-1){
								 title=title.replace("\"", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("\\:")!=-1){
								 title=title.replace("\\:", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("/")!=-1){
								 title= title.replace("/", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("<")!=-1){
								 title= title.replace("<", "");
								 System.out.println(title);
							 }
							 if(title.indexOf(">")!=-1){
								 title= title.replace(">", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("*")!=-1){
								 title= title.replace("*", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("|")!=-1){
								 title= title.replace("|", "");
								 System.out.println(title);
							 } 
							 JSONObject jsonObject=new JSONObject();
							 jsonObject.put("name", title);
							 jsonObject.put("url", href);
							 jsonObject.put("image", image);
							 jsonArray.add(jsonObject);
						 }
						 
					 }
					 Elements page=doc.select("div.page").select("a.next");
					 String nextPage=page.attr("abs:href");
					 if(nextPage!=null&&!nextPage.trim().equals("")){
						 startURL=nextPage;
					 }else{
						 mark=false;
					 }
			 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}

	
	
	public static JSONArray getCategoryByHuPu(String startURL){
		List list=new ArrayList();
		JSONArray jsonArray=new JSONArray();
		boolean mark=true;
		int count=0;
		 
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("ul.hp-threeNav-item").select("li.hp-dropDownMenu");
					 for(Element element:elements){
						 Element e=element.select("a[href]").first();
						 String title=e.attr("title");
						 String href=e.attr("href");
						 count=count+1;
						 if(title!=null&&!title.equals("")){
							 JSONObject jsonObject=new JSONObject();
							 jsonObject.put("name", title);
							 jsonObject.put("url", href);
							 jsonArray.add(jsonObject);
						 }
						 System.out.println(title+"---"+href+"----------"+count);  
					 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public static String getEndVideoURL(String startURL){
		String href="";
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).timeout(60000).get();
					 
						Element element=doc.select("div#play_box").select("a[href]").first();
						if(element==null){
							element=doc.select("div#flashcontent").select("object#videoobj").select("param").first();
							 href=element.attr("value");
						}else{
							 href=element.attr("href");
						}
						 
					 
					
				 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return href;
	}
	
	/**
	 * 判断请求页面是否有 今天  本周  本月  全部     ，如果有全部则获取全部的URL
	 * @param url
	 * @return
	 */
	public static String ifAll(String url){
		 String href="";
		Document doc = null;
		try {
					doc = Jsoup.connect(url).get();
					Element element=doc.select("div.left").first();
					if(element.html().indexOf("全部")!=-1){
						Element e=doc.select("div.left").select("a[href]").get(3);
						href=e.attr("abs:href");	
					}
						 //abs获取绝对路径
				
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return href;
	}
	
	
	
	 
}
