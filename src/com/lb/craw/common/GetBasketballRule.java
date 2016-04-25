package com.lb.craw.common;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Proxy.Type;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;

@Service("RuleForCrawl")
@Component
public class GetBasketballRule {
	private static BaseDao baseDao;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		 
		getRuleName("http://www.ilanqiu.com/rules/list_3_",1);
	}
	
	
	/**
	 * 从www.ilanqiu.com获取篮球规则
	 * @param startURL
	 * @param count
	 */
	public static void getRuleName(String startURL,int count){
		 Document doc=null;
		for(int i=1;i<=5;i++){
			System.out.println("current page"+i);
			String url=new String();
			url=startURL+i+".html";
			try {
				doc = Jsoup.connect(url).timeout(1000).get();
				Element div=doc.select("div.lists").first().select("ul").first();
				Elements lis=div.select("li");
				for(Element e:lis){
					String detaiHref=e.select("a[href]").first().attr("abs:href");
					String name=e.text();
					name=name.substring(5,name.length());
					String content=getRuleDetail(detaiHref,1);
					String sql="INSERT  INTO  lb_store(name,content,source_href,addTime,resourceType,source) values(?,?,?,?,?,?)";
					Object[] params={name,content,startURL,System.currentTimeMillis(),6,"爱篮球"};
					baseDao.insertBy(sql, params);
					
					System.out.println(name);
					System.out.println(content);
				}
			} catch (IOException e) {
				 
				
			}
		}
		 
	}
	
	
	public static String getRuleDetail(String URL,int count){
		
		String strs="";
		 Document doc=null;
		try {
			doc = Jsoup.connect(URL).timeout(2000).get();
			Elements els=doc.select("p");
			for(Element e:els){
				String str=e.outerHtml();
				strs=strs+str;
			}
		} catch (IOException e) {
			 
			
		}
		 
		 return strs;
	}
	}

