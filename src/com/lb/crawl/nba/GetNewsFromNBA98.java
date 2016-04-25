package com.lb.crawl.nba;

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
public class GetNewsFromNBA98 {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//getNewsList("http://www.nba98.com/news/");
		getNewsDetail("http://www.nba98.com/news/111627.html");

	}
	/**
	 * 该新闻列表同时包含nba和cba，没有规律区分属于nba或cba，所以入库时并没有指定一级分类和二级分类
	 * 抓取规则：去掉原网站样式，图片单独剥离出来，并只抓取一张图。忽略包含视频的新闻。
	 */
	
	/**
	 * NBA98获取新闻（包含部分cba新闻）
	 * @param startURL
	 */
	public static void getNewsList(String startURL) {
		//System.out.println(Thread.currentThread().getName()+":线程正在执行");
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements as=doc.select("div.list_body").first().select("a");
			//Elements ps=div.select("p");
			int count=1;
			String name="";
			String sourceHref="";
			String content="";
			String img="";
			
			Integer c=0;
			 
				
				for(Element a:as){
					name=a.text();
					System.out.println(name);
					String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
					c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
					if(c==0){
						sourceHref=a.attr("abs:href");
						JSONObject json=getNewsDetail(sourceHref);
						content=json.getString("content").replace("分享到：", "");
						img=json.getString("img");
						String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,resourceType,source,one_category,two_category) values(?,?,?,?,?,?,?,?,?,?)";
						
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,1,"NBA98","01","0101"}; 
							baseDao.insertBy(sql, params);
							System.out.println("NBA98------"+name+"------"+content);
					 
					}else{
						System.out.println(name+"---该区新闻没有更新，－－NBA98");
						//break;
					}	
				}
				count++;
				
			 
			
			 
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject getNewsDetail(String startURL) {
		Document doc = null;
		String content="";
		String img="";
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Element div=doc.select("div.Content-body").first();
			//System.out.println(div);
			content=div.text();
			//System.out.println(div);
			if(div.outerHtml().indexOf("img")!=-1){//&&div.outerHtml().indexOf("document")==-1
				Elements i=div.select("img");
				if(i.size()>0){
					img=i.first().attr("abs:src");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JSONObject json=new JSONObject();
		 json.put("img", img);
		 json.put("content", content);
		return json;
	}
}
