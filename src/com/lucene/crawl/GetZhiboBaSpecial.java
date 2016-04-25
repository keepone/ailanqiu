package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.TimeUtil;
 
@Component
public class GetZhiboBaSpecial {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	ServiceUtil serviceUtil=null;
	
	private static List<JSONObject> list=new ArrayList<JSONObject>();
 
	public static void main(String[] args) throws InterruptedException {
		addSpeicialFromZhiBoBa("http://tu.zhibo8.cc/home/album/18135/");

	}

 
 
	
	public static void addSpeicialFromZhiBoBa(String startURL){
			Document doc = null;
			String url="";
			Integer id=0;
			Integer count=1;
			boolean flag=true;
			while(flag){
				 
					try {
						url=new String();
						url=startURL+count+".html";
						doc = Jsoup.connect(url).timeout(30000).get();
					} catch (IOException e) {
						System.out.println("发生异常,再次请求.................");
						 
					}
					String html=doc.outerHtml();
					if(html.indexOf("图集已浏览完毕")==-1){
						Elements es=doc.select("div#image_wrap");
						String img =es.select("img").attr("abs:src");
						Integer sum=0;
//						String sql1 = "select count(*) AS count from lb_store where NAME='"
//								+ name + "'";
//						sum = Integer
//								.parseInt(((Map) baseDao.findBy(sql1, null)
//										.get(0)).get("count").toString());
						if(sum==0){
							String source_href = url;
							
							String content =doc.select("div#imginfo").first().ownText();
								System.out.println(content);
							 
								if(count==1){
									String sql="INSERT INTO lb_store(name,img,source_img,content,source_href,addTime,one_category,two_category,source,resourceType) VALUES(?,?,?,?,?,?,?,?,?,?);";
									
									int index=html.indexOf("<title>");
									int index2=html.indexOf("</title");
									String title=html.substring(index+7,index2);
									Object [] params={title,img,img,content,url,System.currentTimeMillis(),"07","0701","网易",3};
									
									id=baseDao.insertBy(sql, params);
								}
								
								
								
								String sql_ditail="INSERT into lb_special_detail(special_id,content,img,sourceImg,addTime) VALUES(?,?,?,?,?)";
								long current_time=System.currentTimeMillis();
								
								Object []params_detail={id,content,img,img,current_time};
								baseDao.insertBy(sql_ditail, params_detail);
						}
						count++;
					}else{
						flag=false;
					}
					
					
		 
				 
			}
		 
	}


 
}
