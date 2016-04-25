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
import com.lb.utils.StrUtil;


@Component
public class GetNBANewsFromZhiBoBa {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getNewsList("http://news.zhibo8.cc/nba");
		//getNewsDetail("http://sports.qq.com/a/20141028/058599.htm");

	}
	/**
	 * 抓取规则：去掉原网站样式，图片单独剥离出来，并只抓取一张图。忽略包含视频的新闻。
	 */
	
	/**
	 * 从直播吧获取新闻（包含部分cba新闻）
	 * @param startURL
	 */
	public static void getNewsList(String startURL) {
		System.out.println(Thread.currentThread().getName()+":线程正在执行");
		Document doc = null;
		try {
			
			doc = Jsoup.connect(startURL).timeout(60000).get();
			
			
			Element div=doc.select("div.topleftbox").first();
			
			
			Elements ps=div.select("p");
			int count=1;
			String name="";
			String sourceHref="";
			String content="";
			String img="";
			
			Integer c=0;
			for(Element p:ps ){
				Elements as=p.select("a");
				for(Element a:as){
					name=a.text();
					String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
					c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
					if(c==0){
						sourceHref=a.attr("href");
						JSONObject json=getNewsDetail(sourceHref);
						content=json.getString("content");
						content=content+"<p></p>";
						img=json.getString("img");
						String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
						if(count<=2){
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"01","0101",1,"篮球热"};  //11 表示数据来源于直播吧
							baseDao.insertBy(sql, params);
							System.out.println("NBA------"+name+"------"+content);
						}else{
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"02","0201",1,"篮球热"};
							baseDao.insertBy(sql, params);
							System.out.println("CBA------"+name+"------"+content);
						}
					}else{
						System.out.println(name+"---,已经录入，－－来自直播吧");
						//break;
					}	
				}
				count++;
				
			}
			
			//获取头条新闻 
			
			Elements h2s=div.select("h2");
			int sum=1;
			
			for(Element h2:h2s){
				Elements as=h2.select("a");
				for(Element a:as){
					name=a.text();
					String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
					c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
					if(c==0){
						sourceHref=a.attr("href");
						JSONObject json=getNewsDetail(sourceHref);
						content=json.getString("content");
						content=content+"<p></p>";
						img=json.getString("img");
						String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
						if(sum<=2){
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"01","0101",1,"直播吧"};
							baseDao.insertBy(sql, params);
							System.out.println("NBA------"+name+"------"+content);
						}else{
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"02","0201",1,"直播吧"};
							baseDao.insertBy(sql, params);
							System.out.println("CBA------"+name+"------"+content);
						}
					}else{
						System.out.println(name+"---,已经录入，－－来自直播吧");
						//break;
					}
				}
				sum++;
				
			}
			
			
			
			//其他模块新闻 
			
			Elements h2s2=doc.select("div[style=height:500px;]");
			 System.out.println(h2s2.size());
			for(Element h2:h2s2){
				Elements as=h2.select("a");
				System.out.println(as.size());
				System.out.println(as);
				for(Element a:as){
					name=a.text();
					String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
					c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
					if(c==0){
						sourceHref=a.attr("href");
						JSONObject json=getNewsDetail(sourceHref);
						content=json.getString("content");
						content=content+"<p></p>";
						img=json.getString("img");
						String one_category="01";
						String two_category="0101";
						String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";
						 if(name.indexOf("WCBA")!=-1){
							 one_category="03";
							 two_category="0301";
						 }else  if(name.indexOf("CBA")!=-1){
							 one_category="02";
							 two_category="0201";
						 }else{
							 
						 }
							Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,one_category,two_category,1,"直播吧"};
							baseDao.insertBy(sql, params);
							System.out.println("NBA------"+name+"------"+content);
						 
					}else{
						System.out.println(name+"---,已经录入，－－来自直播吧");
						//break;
					}
				}
				sum++;
				
			}
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public static JSONObject getNewsDetail(String startURL) {
		Document doc = null;
		String content="";
		String img="";
		String str="";
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Element div=doc.select("div.content").first();
			Elements ps=div.select("p");
			for(Element p:ps){
				 content=p.text();
				content=StrUtil.getStrByRemoveFistStr(content, "日讯");
				content=StrUtil.getStrByRemoveFistStr(content, "追究法律责任。");
				content=StrUtil.replaceStrForCraw(content);
				if(content.length()<2){
					 
				}else{
					
					content=StrUtil.filterStr(content);
					str=str+content;
				}
				
			 
			}
		 
			if(div.outerHtml().indexOf("img")!=-1){
				img=div.select("img").first().attr("abs:src");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 JSONObject json=new JSONObject();
		 json.put("img", img);
		 json.put("content", str);
		return json;
	}
}
