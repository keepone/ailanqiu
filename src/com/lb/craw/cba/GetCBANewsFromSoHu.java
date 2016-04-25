package com.lb.craw.cba;

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
public class GetCBANewsFromSoHu {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public static void main(String[] args) {
		String url = "http://cbachina.sports.sohu.com/s2011/2671/s309755607/";
		getNewsList_2(url);

	}

	
	
	public static void getNewsList_2	(String startURL) {
		int count=1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements lis = doc.select("div.f14list").select("ul").first().select("a");
			for (Element li : lis) {
				String name = li.text();
				//过滤掉含‘高清’的新闻
				if(name.indexOf("高清")==-1&&name.indexOf("视频")==-1){
					String href = li.attr("abs:href");
					//过滤掉链接含有‘group’的新闻
					if(href.indexOf("group")==-1&&href.indexOf("blog")==-1){
						String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
						Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
						//只有数据库不存在该条记录时才爬取数据
						if(c==0){
								JSONObject json=getNewsDetail(href);
								String img=json.getString("img");
								String content=json.getString("content");
								String sql="INSERT INTO lb_store(name,content,addTime,source,img,source_img,one_category,two_category,source_href,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
								Object[] params={name,content,System.currentTimeMillis(),"搜狐",img,img,"04","0401",href,1};
								baseDao.insertBy(sql, params);
								System.out.println(name+img);
						}else{
							//break;
						}
					}
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
