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
public class GetNewsFromTBBA {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getNewsList("http://www.tbba.com.cn/");
		//getNewsDetail("http://sports.qq.com/a/20141028/058599.htm");

	}
	/**
	 * �������б�ͬʱ����nba��cba��û�й�����������nba��cba���������ʱ��û��ָ��һ������Ͷ�������
	 * ץȡ����ȥ��ԭ��վ��ʽ��ͼƬ���������������ֻץȡһ��ͼ�����԰�����Ƶ�����š�
	 */
	
	/**
	 * NBA98��ȡ���ţ���������cba���ţ�
	 * @param startURL
	 */
	public static void getNewsList(String startURL) {
		//System.out.println(Thread.currentThread().getName()+":�߳�����ִ��");
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements as=doc.select("div.focus").first().select("a");
			//Elements ps=div.select("p");
			int count=1;
			String name="";
			String sourceHref="";
			String content="";
			String img="";
			
			Integer c=0;
			 
				
				for(Element a:as){
					name=a.text();
					String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
					c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
					if(c==0){
						sourceHref=a.attr("abs:href");
						JSONObject json=getNewsDetail(sourceHref);
						content=json.getString("content").replace("\u00A0", "");
						img=json.getString("img");
							if(sourceHref.indexOf("nba")!=-1){
								String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";							
								Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"01","0101",1,"TBBA"};  
								baseDao.insertBy(sql, params);
								System.out.println("NBA------"+name+"------"+content);
							}else{
								String sql="INSERT INTO lb_store(name,content,img,source_img,addTime,source_href,one_category,two_category,resourceType,source) values(?,?,?,?,?,?,?,?,?,?)";								
								Object[] params={name,content,img,img,System.currentTimeMillis(),sourceHref,"02","0201",1,"TBBA"}; 
								baseDao.insertBy(sql, params);
								System.out.println("CBA------"+name+"------"+content);
							}
							
					 
					}else{
						System.out.println("��������û�и��£���������TBBA");
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
			Element div=doc.select("font#zoom").first();
			content=div.text();
			if(div.outerHtml().indexOf("img")!=-1&&div.outerHtml().indexOf("document")==-1){
				img=div.select("img").first().attr("abs:src");
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
