package com.lb.crawl.nba;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
 
import com.lb.dao.BaseDao;
 
@Component
public class GetNBACommentFromNBAChina {
	private static BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public static void main(String[] args) {
		String startURL = "http://sports.163.com/13/0626/22/92B1N94S00051CA1.html";
		getSpecial("http://china.nba.com/");

	}

	 
	
	/**
	 * 获取来自NBA中文网的专栏作家模块信息
	 * @param startURL
	 * @return
	 */
	public static void getSpecial(String startURL) {
		String content=null;
		Document doc = null;
		try {
			Connection conn  = Jsoup.connect(startURL);
			doc=conn.timeout(60000).get();
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
			Element element=doc.select("div.column-writer-i").first();
			String href=element.select("div.img").select("a[href]").first().attr("abs:href");
			String sourceCover=element.select("div.img").select("img").first().attr("src");
			String title=element.select("div.txt").select("strong").first().ownText();
			content = getSpecialContent(href).replace("null", "");
			String sql1="select count(*) AS count from lb_store where NAME='"+title+"'";
			Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
			//只有数据库不存在该条记录时才爬取数据
			if(c==0){
				String sql="INSERT  INTO  lb_store(img,source_img,name,content,addTime,resourceType,source,source_href) values(?,?,?,?,?,?,?,?)";
				Object[] params={sourceCover,sourceCover,title,content,System.currentTimeMillis(),4,"NBACHINA",href};
			 	baseDao.insertBy(sql, params);
			}
			 
			 
		 
		} catch (IOException e) {
			 
		}

		 

	}
	/**
	 * 获取专栏内容
	 * @param startURL
	 * @return
	 */
	public static String getSpecialContent(String startURL) {
		String content=null;
		System.out.println("爬虫正在启动…………");
		Document doc = null;
		try {
			Connection conn  = Jsoup.connect(startURL);
			doc=conn.timeout(60000).get();
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
			Elements ps=doc.select("div.passage1-cont").first().select("p");
			for(Element p:ps){
				content=content+p.outerHtml();
			}
			
			
		} catch (IOException e) {
		}
System.out.println(content);
		return content;

	}
}
