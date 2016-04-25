package com.lucene.crawl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
 
@Component
public class GetEuropeBasketball {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public static void main(String[] args) {
		String url = "http://sports.sohu.com/s2014/1565/s395217589/";
		getNewsList(url);

	}

	public static void getNewsList(String startURL) {
		int count=1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis = doc.select("div.main").select("ul").first().select("a");
			for (Element li : lis) {
				String name = li.text();
				
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				//只有数据库不存在该条记录时才爬取数据
				if(c==0){
					String href = li.attr("abs:href");
					if(href.indexOf("group")==-1){
						JSONObject json=getNewsDetail(href);
						String img=json.getString("img");
						String content=json.getString("content");
						String sql="INSERT INTO lb_store(name,content,addTime,source,img,source_img,one_category,two_category,source_href,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
						Object[] params={name,content,System.currentTimeMillis(),"搜狐",img,img,"04","0401",href,1};
						baseDao.insertBy(sql, params);
						System.out.println(name);
					}
				}else{
					System.out.println(name+"－－－已经录入(EUROPE)");
					//break;
				}
				
				
				
			}
			

		} catch (IOException e) {
			System.out.println("ouzhou列表页出错");
			e.printStackTrace();
		}

	}
	
	public static JSONObject getNewsDetail(String href){
		String img="";
		String str="";
		JSONObject json=new JSONObject();
		try {
			Document doc = Jsoup.connect(href).timeout(60000).get();
				Element div=doc.select("div#contentText").first();
				Element e=div.select("tbody").first();
				if(e!=null&&e.outerHtml().indexOf("img")!=-1){
					img=e.select("img").first().attr("abs:src");
				}
				Elements ps=div.select("p");
				for(Element p:ps){
					str=str+p.text();
				}
				
				//System.out.println(str);
		
		} catch (IOException e) {
			System.out.println("出错名称："+href);
			e.printStackTrace();
		}
		json.put("img", img);
		json.put("content", str);
		return json;
	}
}
