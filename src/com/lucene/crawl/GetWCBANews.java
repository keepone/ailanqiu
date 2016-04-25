package com.lucene.crawl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
 
@Component
public class GetWCBANews {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getNewsList("http://sports.qq.com/wcba/");
		//getNewsDetail("http://sports.qq.com/a/20141028/058599.htm");

	}

	public static void getNewsList(String startURL) {

		int count = 1;
		int f = 1;
		Document doc = null;
		try {
			//获取wcba新闻
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis=doc.select("div.leftList").first().select("ul").select("li");
			for(Element li:lis){
				String name=li.select("a[href]").first().ownText();
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				//只有数据库不存在该条记录时才爬取数据
				if(c==0){
					String detailHref=li.select("a[href]").first().attr("abs:href");
					JSONObject json=getNewsDetail(detailHref);
					String content=json.getString("content");
					String sourceImg=json.getString("img");
					 
					String sql="INSERT INTO lb_store(name,content,addTime,source,one_category,two_category,source_href,resourceType,img,source_img) values(?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,content,System.currentTimeMillis(),"腾讯","03","0301",detailHref,1,sourceImg,sourceImg};
					baseDao.insertBy(sql, params);
					count = count + 1;
					System.out.println(name);
				}else{
					System.out.println(name+"－－－已经录入(WCBA)");
					//break;
				}
			}

			
			//获取中国女篮新闻
			doc = Jsoup.connect("http://sports.qq.com/l/cba/zgnul/hotnews/list20110816173151.htm").timeout(60000).get();
			Elements lis2=doc.select("div.leftList").first().select("ul").select("li");
			for(Element li:lis2){
				String name=li.select("a[href]").first().ownText();
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				//只有数据库不存在该条记录时才爬取数据
				if(c==0){
					String detailHref=li.select("a[href]").first().attr("href");
					JSONObject json=getNewsDetail(detailHref);
					String content=json.getString("content");
					String sourceImg=json.getString("img");
					
					String sql="INSERT INTO lb_store(name,content,addTime,resourceType,source,one_category,two_category,img,source_img) values(?,?,?,?,?,?,?,?,?)";
					Object[] params={name,content,System.currentTimeMillis(),1,"腾讯","03","030111",sourceImg,sourceImg};
					baseDao.insertBy(sql, params);
					count = count + 1;
					System.out.println(name);
				}else{
					System.out.println(name+"－－－已经录入(WCBA)");
					//break;
				}
			}
			
			
			//获取头条新闻（5条左右）
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements as = doc.select("div.mod_A").select("div.news_list")
					.select("a[href]");
			for (Element a : as) {
				String detailHref = a.attr("abs:href");
				String name = a.text();
				String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
				Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
				//只有数据库不存在该条记录时才爬取数据
				if(c==0){
					JSONObject json=getNewsDetail(detailHref);
					String content=json.getString("content");
					String sourceImg=json.getString("img");
					
					String sql="INSERT INTO lb_store(name,content,addTime,resourceType,source,one_category,two_category,img,source_img) values(?,?,?,?,?,?,?,?,?)";
					Object[] params={name,content,System.currentTimeMillis(),1,"腾讯","03","030104",sourceImg,sourceImg};
					baseDao.insertBy(sql, params);
					count = count + 1;
					System.out.println(name);
				}else{
					//break;
				}
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getNewsDetail(String startURL) {
		int count = 1;
		int f = 1;
		String img = "";
		String str = "";
		JSONObject json=new JSONObject();
		Document doc = null;

		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements lis = doc.select("div[accesskey=3]");
		if (lis != null) {
			if (lis.outerHtml().indexOf("src") != -1) {
				img = lis.select("IMG").first().attr("src");
			}
		}
		Elements ps=doc.select("p[style=TEXT-INDENT: 2em]");
		for(Element p:ps){
			str=str+p.text();
		}
		json.put("img", img);
		json.put("content", str);
		 
		return json;

	}
}
