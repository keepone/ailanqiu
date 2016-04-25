package com.lucene.crawl;

import java.io.IOException;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
 
@Component
public class GetBasketballTactics {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getAllTactics("http://www.172lanqiu.com/tuwenjiaoxue/list_8_");

	}
	public static void getTacticsDetail(String startURL) {
		 
		int count = 1;
		int f = 1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			String title = doc.select("div.left").select("H1").first().ownText();
		 
			String content = doc.select("div.left").select("div.wzbody").first().outerHtml();
			Integer begin_index=content.indexOf("center");
			Integer end_index=content.indexOf("viewbox");
			content=content.substring(begin_index+19,end_index-28);
			content=content.replace("src=\"/", "src=\"http://www.172lanqiu.com/");
			String sql="INSERT  INTO  lb_tactics(name,content,source_content,source_href,addTime) values(?,?,?,?,?)";
			Object[] params={title,content,content,startURL,System.currentTimeMillis()};
			baseDao.insertBy(sql, params);
			System.out.println(title);
			//System.out.println(content);
				count = count + 1;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void getAllTactics(String startURL) { 
		int count = 1;
		int f = 1;
		String endUrl=f+".html";
		Document doc = null;
		while(!endUrl.equals("")){
			try {
				doc = Jsoup.connect(startURL+endUrl).timeout(60000).get();
				Elements lis = doc.select("div.lists3").select("li");
				for(Element li:lis){
					String href=li.select("span").first().select("a[href]").first().attr("abs:href");
					getTacticsDetail(href); 
					count = count + 1;
				}
				
			String nextPage=doc.select("UL.pagelist").outerHtml();
			if(nextPage.indexOf("下一页")!=-1){
				f+=1;
				endUrl=f+".html";
			}else{
				endUrl="";
			}
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("一共下载："+f+"页，共"+(count-1)+"套篮球战术");
	}
}
