package com.lb.craw.cba;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
 
import com.lb.dao.BaseDao;
 
@Component
public class GetCBANewsByXinHua {
	private static BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
 

	public static void main(String[] args) {
		String url = "http://roll.sports.sina.com.cn/s_cba_all/index.shtml";
		getNewsList(url);

	}
/**
 * 从新华网获取cba新闻
 * @param startURL
 */
	public static void getNewsList(String startURL) {
		int count=1;
		Document doc = null;
		String two_category="0201"; //CBA新闻
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis = doc.select("div#m3").select("section.topics").select("a");
			for (Element li : lis) {
				String name = li.select("a[href]").first().text();//新闻标题
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				if(c==0){
					String href = li.select("a[href]").first().attr("href");//新闻详情页链接
					JSONObject json=new JSONObject();
					if(name.indexOf("高清")==-1){
						json=getNewsDetail(href);
					}else{
						json=getNewsDetail_2(href);
					}
					String content=json.getString("content").replace("?", "").replace(" ", "");    //新闻内容
					String img=json.getString("img");    //新闻图片
					//String sql="INSERT INTO cba_news(name,content,addTime) values(?,?,?)";
					String sql="INSERT INTO lb_store(name,img,source_img,content,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,img,img,content,System.currentTimeMillis(),href,"02",two_category,1,"新华网"};
					baseDao.insertBy(sql, params);
					System.out.println(name+"----"+content);
				}else{
					System.out.println(name+"------之前已经录入");
					//break;
				}
				
			}
			
			
			Elements tds = doc.select("div#m5").select("h3.topic").select("a");
			for (Element li : tds) {
				String name = li.select("a[href]").first().text();//新闻标题
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				if(c==0){
					String href = li.select("a[href]").first().attr("href");//新闻详情页链接
					JSONObject json=new JSONObject();
					if(name.indexOf("高清")==-1){
						json=getNewsDetail(href);
					}else{
						json=getNewsDetail_2(href);
					}
					if(json!=null){
						String content=json.getString("content").replace("?", "").replace(" ", "");    //新闻内容
						String img=json.getString("img");    //新闻图片
						//String sql="INSERT INTO cba_news(name,content,addTime) values(?,?,?)";
						String sql="INSERT INTO lb_store(name,img,source_img,content,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
						Object[] params={name,img,img,content,System.currentTimeMillis(),href,"02",two_category,1,"新华网"};
						baseDao.insertBy(sql, params);
						System.out.println("新华网"+name+"----"+content);
					}else{
						System.out.println("---数据格式错误"+name);
					}
				}else{
					System.out.println(name+"------之前已经录入");
					//break;
				}
				
				
			}
			
			 

		} catch (IOException e) {
			 System.out.println("获取新华网cba新闻列表页出错....");
			 e.printStackTrace();
		}

	}
	
	public static JSONObject getNewsDetail(String href){
		String img="";
		 String str="";
		 JSONObject json=null;
		try {
			Document doc = Jsoup.connect(href).timeout(600000).get();
				Element div=doc.select("div#content").first();
				if(div!=null){
					//如果新闻中包含图片，则提取图片
					if(div.outerHtml().indexOf("img")!=-1){
						img=div.select("img").first().attr("abs:src");
					}
					Elements ps=div.select("p");
					for(Element p:ps){
						str=str+p.text();
					}
					 
					json=new JSONObject();
					json.put("img", img);
					json.put("content", str);
				}
		} catch (IOException e) {
			 System.out.println("获取新华网cba新闻详情页1出错....");
			 e.printStackTrace();
		}
		return json;
	}
	
	public static JSONObject getNewsDetail_2(String href){
		String img="";
		 String str="";
		 JSONObject json=null;
		try {
			Document doc = Jsoup.connect(href).timeout(60000).get();
				Element div=doc.select("span#content").first();
				//如果新闻中包含图片，则提取图片
				if(div.outerHtml().indexOf("img")!=-1){
					img=div.select("img").first().attr("abs:src");
				}
				Elements ps=div.select("p");
				for(Element p:ps){
					str=str+p.text();
				}
				 
				json=new JSONObject();
				json.put("img", img);
				json.put("content", str);
		} catch (IOException e) {
			 System.out.println("获取新华网cba新闻详情页2出错....");
			 e.printStackTrace();
		}
		return json;
	}
}
