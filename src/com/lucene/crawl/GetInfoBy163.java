package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
 
 
@Component
public class GetInfoBy163 {
	private static BaseDao baseDao;
	
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startURL = "http://sports.163.com/special/nbalegendhz";
		getPersonal(startURL);

	}

	 

	public static void getPersonal(String startURL) {
		 
		int count=1;
		int f = 1;
		String name="";
		Integer special_id=0;
		for (int i = 1; i <= 3; i++) {
			String url=new String();
			if(i==1){
				url=startURL;
			}else{
				url=startURL+"_0"+i+"/";
			}
			 
			 
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				Elements lis = doc.select("div.col2").select("li");
				for (Element li : lis) {
					if(count==1){
						
					}else{	
						if(li.outerHtml().indexOf("期")!=-1){
//							name=e.select("img").first().attr("alt");
//							String sourceCover=e.select("img").first().attr("src");
//							//String description=e.select("div.imgCite").first().select("p.info").text();
//							String href=e.select("a[href]").first().attr("href");
							
							String href = li.select("table").first().select("a[href]").first().attr("href");
							String sourceCover = li.select("table").first().select("img[src]").first().attr("src");
							name = li.select("h4.tit").first().text();
							String description = li.select("p").first().ownText();
							
							doc = Jsoup.connect(href).timeout(60000).get();
							Elements ee=doc.select("a[href^=http://sports.163.com/photoview]");
							String detail_href=ee.select("a[href]").first().attr("href");
							System.out.println("----"+name);
							
							String sql="INSERT into lb_store(name,img,source_img,href,description,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?)";
							long current_time=System.currentTimeMillis();
							//下载图片
//							String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory/"+current_time+".jpg";;
//							IoUtil.downloadImage(cover, "",savepath);
							Object []params={name,sourceCover,sourceCover,detail_href,description,current_time,"07","0701","网易",3};
							special_id=baseDao.insertBy(sql, params);
							
							doc = Jsoup.connect(detail_href).timeout(60000).get();
							//页面有2种显示规则。
							if(doc.outerHtml().indexOf("gallery-data")==-1){
								String elements = doc.select("textarea[class=hidden]").first().text();
								//通过正则表达式截取信息
								while(elements.indexOf("h2")!=-1){
									JSONObject json=new JSONObject();
									Integer h2_begin_index=elements.indexOf("h2");
									Integer h2_end_index=elements.indexOf("<p>");
									Integer img_begin_index=elements.indexOf("title=\"img\"");
									Integer img_end_index=elements.indexOf("title=\"timg\"");
									//获取大图（共有3中图片格式）
									String sourceImg=elements.substring(img_begin_index+12, img_end_index-7);
					
									//获取文字介绍
									String content=elements.substring(h2_begin_index+3,h2_end_index-5);
									elements=elements.substring(img_end_index+10,elements.length());
									
									String sql2="INSERT into lb_special_detail(special_id,content,sourceImg,addTime) VALUES(?,?,?,?)";
									current_time=System.currentTimeMillis();
									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";;
//									IoUtil.downloadImage(img, "",savepath);
									Object [] params2={special_id,content,sourceImg,current_time};
									baseDao.insertBy(sql2, params2);
									 
									System.out.println(content);
									 
								}
							}else{
								String elements = doc.select("textarea[name=gallery-data]").first().text();
								elements=elements.substring(elements.indexOf("list"),elements.length());
								//通过正则表达式截取信息
								while(elements.indexOf("note")!=-1){
									JSONObject json=new JSONObject();
									Integer img_index=elements.indexOf("img");
									Integer timg_index=elements.indexOf("timg");
									//获取大图（共有3中图片格式）
									String sourceImg=elements.substring(img_index+7, timg_index-4);
									Integer note_index=elements.indexOf("note");
									Integer newsurl_index=elements.indexOf("newsurl");
									//获取文字介绍
									String content=elements.substring(note_index+8,newsurl_index-4);
									elements=elements.substring(newsurl_index+10,elements.length());
									
									String sql2="INSERT into lb_special_detail(special_id,content,sourceImg,addTime) VALUES(?,?,?,?)";
									current_time=System.currentTimeMillis();
									//下载图片
//									String savepath= "D:/tomcat6/Tomcat 6.0/webapps/NBAHistory2/"+current_time+".jpg";;
//									IoUtil.downloadImage(img, "",savepath);
									Object [] params2={special_id,content,sourceImg,current_time};
									baseDao.insertBy(sql2, params2);
									System.out.println(content);
								}
							}
						}
					}
					count++;
//					System.out.println("");
//					System.out.println("");
//					System.out.println("");
					//System.out.println(count+"------------"+name);
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		//System.out.println("have download"+count);
	 

	}

	 
}
