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
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.StrUtil;
 
@Component
public class GetNBANewsFromTencent {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//getNBANews("http://sports.qq.com/l/cba/CBAlist.htm");
		getNewsDetail("http://sports.qq.com/a/20151120/043588.htm");

	}

	public static void getNBANews(String startURL) {

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
					 
					Object[] params={name,content,System.currentTimeMillis(),"腾讯","01","0101",detailHref,1,sourceImg,sourceImg};
					baseDao.insertBy(sql, params);
					count = count + 1;
					System.out.println(name);
				}else{
					System.out.println(name+"－－－已经录入(nBA)");
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
		AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
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
		
		Elements ps=doc.select("p[style=text-indent: 2em;]");
		if(ps!=null&&ps.size()>0&&!ps.equals("")){
			
		}else{
//			ps=doc.select("p[style=TEXT-INDENT: 2em]");
			ps=doc.select("div.bd").first().select("p");
		}
		String video="";
		if(doc.html().indexOf("vid=")!=-1){
			
			video=doc.select("iframe").first().attr("abs:src");
			if(video!=null&&video!=""){
				try {
					video=analyzeVideoUtil.analyzeFromTencent2(video);
					video="<iframe src=\""+video+"\""+" frameborder=\"0\" style=\"height:200px;\"></iframe>";
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		int i=1;
		for(Element p:ps){
			String content=p.text();
			content=StrUtil.getStrByRemoveFistStr(content, "日讯");
			content=StrUtil.getStrByRemoveFistStr(content, "追究法律责任。");
			content=StrUtil.replaceStrForCraw(content);
			if(content.length()<2){
				 content=p.select("img").attr("src");
				 if(!content.equals("")){
					 content="<p><img onclick=\"loadURL('gap://imgUrl="+content+"')\" src=\""+content+"\"></p>";
					 str=str+content;
				 }
			}else{
				
				content=StrUtil.filterStr(content);
				str=str+content;
			}
			
			//默认在第一个段落后面加上视频
			if(i==2){
				str=str+video;
			} 
			
			i=i+1;
		}
		json.put("img", img);
		json.put("content", str);
		 
		return json;

	}
}
