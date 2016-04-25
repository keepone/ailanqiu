package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
 
@Component
public class GetNBANewsBy {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String startURL = "http://sports.163.com/nba/";
		//getNewsBy163(startURL);
		 
		 //getSinaNewsVideoContent("http://video.sina.com.cn/z/sports/nba/officialbest/2014-11-10/#136260756");
		  //getSinaNewsTxtContent("http://sports.sina.com.cn/nba/2014-11-10/06497404134.shtml");
		  JSONArray jsonArray=new JSONArray();
//		jsonArray=getNewsBySina("http://sports.sina.com.cn/nba/");
//		 for(int i=0;i<jsonArray.size();i++){
//			JSONObject json=(JSONObject) jsonArray.get(i);
//			String name=json.get("news_name").toString();
//			String img=json.get("img_href").toString();
//			String content=json.get("news_content").toString();
//			String video=json.get("video_href").toString();
//			String newsId=json.get("newsId").toString();
//			System.out.println(name+img+content+video+newsId);
//		 } 
		 //getNewsBy163_history("http://sports.163.com/special/archaeology/");
//		  jsonArray=getNewsBy163_history_detail_2("http://sports.163.com/photoview/0AI90005/100511.html#p=93SGBB1S0AI90005");
//		  for(int i=0;i<jsonArray.size();i++){
//				JSONObject json=(JSONObject) jsonArray.get(i);
//				String img=json.get("img").toString();
//				String content=json.get("content").toString();
//				 
//				System.out.println(img+"-----"+content);
//			 } 
//	
	
	}

	public static void getNewsBy163(String startURL) {
		int count = 1;
		int f = 1;

	 
			String url = new String();
			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(60000).get();
				Elements elements = doc.select("div.topnews_cell").select(
						"a[href]");
				for (Element element : elements) {
					String href = element.attr("href");
					String name = element.ownText();
					System.out.println(name + "----" + href);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("have download" + count);

	}

//	 
	
	/**
	 * ��ȡNBA����ϵ��(����)����ר��ҳ����ʾ��2����ʽ���������2��ץȡ���򣬴˷���Ϊ������1��
	 * @param startURL
	 * @return
	 */
	public static JSONArray getNewsBy163_history_detail(String startURL) {
		int count = 1;
		int f = 1;
		JSONArray arr=new JSONArray();
	 
			String url = new String();
			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(90000).get();
				String elements = doc.select("textarea[name=gallery-data]").first().text();
				elements=elements.substring(elements.indexOf("list"),elements.length());
				//ͨ��������ʽ��ȡ��Ϣ
				while(elements.indexOf("note")!=-1){
					JSONObject json=new JSONObject();
					Integer img_index=elements.indexOf("img");
					Integer timg_index=elements.indexOf("timg");
					//��ȡ��ͼ������3��ͼƬ��ʽ��
					String img=elements.substring(img_index+7, timg_index-4);
					Integer note_index=elements.indexOf("note");
					Integer newsurl_index=elements.indexOf("newsurl");
					//��ȡ���ֽ���
					String content=elements.substring(note_index+8,newsurl_index-4);
					elements=elements.substring(newsurl_index+10,elements.length());
					json.put("img", img);
					json.put("content", content);
					arr.add(json);
					System.out.println(img);
					System.out.println(content);
					count+=1;
				}
				//System.out.println(elements);
			 
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("NBA����ϵ����ר�����"+(count-1)+"��ͼƬ");
		return arr;
	}
	
	/**
	 * ��ȡNBA����ϵ��(����)����ר��ҳ����ʾ��2����ʽ���������2��ץȡ���򣬴˷���Ϊ������2��
	 * @param startURL
	 * @return
	 */
	public static JSONArray getNewsBy163_history_detail_2(String startURL) {
		int count = 1;
		int f = 1;
		JSONArray arr=new JSONArray();
	 
			String url = new String();
			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(90000).get();
				String elements = doc.select("textarea[class=hidden]").first().text();
//				elements=elements.substring(elements.indexOf("list"),elements.length());
				//ͨ��������ʽ��ȡ��Ϣ
				while(elements.indexOf("h2")!=-1){
					JSONObject json=new JSONObject();
					Integer h2_begin_index=elements.indexOf("h2");
					Integer h2_end_index=elements.indexOf("<p>");
					Integer img_begin_index=elements.indexOf("title=\"img\"");
					Integer img_end_index=elements.indexOf("title=\"timg\"");
					//��ȡ��ͼ������3��ͼƬ��ʽ��
					String img=elements.substring(img_begin_index+12, img_end_index-7);
	
					//��ȡ���ֽ���
					String content=elements.substring(h2_begin_index+3,h2_end_index-5);
					elements=elements.substring(img_end_index+10,elements.length());
					json.put("img", img);
					json.put("content", content);
					arr.add(json);
					System.out.println(img);
					System.out.println(content);
					count+=1;
				}
				//System.out.println(elements);
			 
			} catch (IOException e) {
				e.printStackTrace();
			}
		System.out.println("NBA����ϵ����ר�����"+(count-1)+"��ͼƬ");
		return arr;
	}
	
	/**
	 * �����˻�ȡnba����
	 * @param startURL
	 * @return
	 */
	public static JSONArray getNewsBySina(String startURL) {
		int count = 1;
		int f = 1;
		JSONArray news_arr=new JSONArray();
			Document doc = null;
			 
				try {
					doc = Jsoup.connect(startURL).timeout(60000).get();
				} catch (IOException e1) {
					System.out.println("�����쳣............");
					getNewsBySina(startURL);
					e1.printStackTrace();
				}
				
				JSONObject json=null;
				String name =null;
				Elements toutiao = doc.select("h4").first().select("a[href]");
				for (Element e : toutiao) {
					 json=new JSONObject();
					  name = e.ownText();
					if(name.indexOf("�ֲ�")==-1&&name.indexOf("ֱ��")==-1&&name.indexOf("��Ƶ��")==-1){
						String href = e.attr("href");
						JSONObject video=getSinaNewsVideoContent(href);
						if(video!=null&&video.size()>0){
							System.out.println(count+"-------------"+name);
							String video_href=video.get("video_href").toString();
							String video_name=video.get("video_name").toString();
							String video_intro=video.get("video_intro").toString();
							json.put("video_href",video_href);
							json.put("news_name",name);
							json.put("news_content",video_intro);
							json.put("img_href","");
							json.put("newsId", href); //�����ŵ�URL��ַ��Ϊ�����������Ϳ��Ա����ظ�����ֵ
							
							news_arr.add(json);
						}else{
							
							JSONObject txt=getSinaNewsTxtContent(href);
							
							if(txt.size()>0){
								//System.out.println(count+"-------------"+name);
								String txtContent=txt.get("content").toString().replace("����ͨ��������ҳ���� ������ �鿴�����ղع������¡�</p><p>֪����</p>", "").replace("<p>���ղ�!</p>", "");
								String img_href=txt.get("img").toString();
								json.put("news_name",name);
								json.put("img_href",img_href);
								json.put("news_content",txtContent);
								json.put("video_href","");
								json.put("newsId", href); //�����ŵ�URL��ַ��Ϊ�����������Ϳ��Ա����ظ�����ֵ
								
								
								String sql="INSERT into lb_store(NAME,content,img,source_img,addTime,one_category,two_category,resourceType,source) VALUES(?,?,?,?,?,?,?,?,?)";
								long current_time=System.currentTimeMillis();
								Object []params={name,txtContent,img_href,img_href,current_time,"01","0101",1,"����"};
								
								String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
								Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
								if(c==0){
									baseDao.insertBy(sql, params);
									 
								}
								news_arr.add(json);
							}else{
								System.out.println("---------------------------------------------------"+count+"---"+name);
							}
						}
						
						
					}else{
						System.out.println("---------------------------------------------------"+count+"---"+name);
					}
				}
					
					
				Elements elements = doc.select("div.tou_c1").first().select("[href]");
				for (Element element : elements) {
					  json=new JSONObject();
					 name = element.ownText();
					 if(name.indexOf("�ֲ�")==-1&&name.indexOf("ֱ��")==-1){
							String href = element.attr("href");
							JSONObject video=getSinaNewsVideoContent(href);
							if(video!=null&&video.size()>0){
								System.out.println(count+"-------------"+name);
								String video_href=video.get("video_href").toString();
								String video_name=video.get("video_name").toString();
								String video_intro=video.get("video_intro").toString();
								json.put("video_href",video_href);
								json.put("news_name",name);
								json.put("news_content",video_intro);
								json.put("img_href","");
								json.put("newsId", href); //�����ŵ�URL��ַ��Ϊ�����������Ϳ��Ա����ظ�����ֵ
								
								news_arr.add(json);
							}else{
								System.out.println(count+"-------------"+name);
								JSONObject txt=getSinaNewsTxtContent(href);
								
								if(txt.size()>0){
									String txtContent=txt.get("content").toString();
									String img_href=txt.get("img").toString();
									json.put("news_name",name);
									json.put("img_href",img_href);
									json.put("news_content",txtContent);
									json.put("video_href","");
									json.put("newsId", href); //�����ŵ�URL��ַ��Ϊ�����������Ϳ��Ա����ظ�����ֵ

									String sql="INSERT into lb_store(NAME,content,img,source_img,addTime,one_category,two_category,resourceType,source) VALUES(?,?,?,?,?,?,?,?,?)";
									long current_time=System.currentTimeMillis();
									Object []params={name,txtContent,img_href,img_href,current_time,"01","0101",1,"����"};
									
									String sql1="select count(*) AS count from lb_store where NAME='"+name+"'";
									Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
									if(c==0){
										baseDao.insertBy(sql, params);
										 
									}
									news_arr.add(json);
								}
							}
							
							
						}else{
							System.out.println("---------------------------------------------------"+count+"---"+name);
						}
					
					count=count+1;
				}
		 
		System.out.println("have scan" + (count-1));
		return news_arr;
	}
	
	public static JSONObject getSinaNewsTxtContent(String url) {
		String result="";
		JSONObject jsonObject=new JSONObject();
		Document doc = null;
		try {
			doc = Jsoup.connect(url).timeout(60000).get();
			Element e = doc.select("div.blkContainerPblk").first();
			if(e!=null){
				Elements ee=e.select("div.img_wrapper");
				String img=null;
				if((ee.size()>0)){
					 img=ee.select("img").first().attr("src");
				}else{
					
					 img=e.select("div.blk_hd_pic").select("img").first().attr("src");
				}
				 
				Elements elements=e.select("p");
				String content="";
				for(Element element:elements){
					content=content+"<p>"+element.ownText().trim()+"</p>";
				}
				jsonObject.put("content", content);
				jsonObject.put("img", img);
			}
			//System.out.println(img);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
}
	
	public static JSONObject getSinaNewsVideoContent(String url) {
			String result="";
			JSONObject jsonObject=new JSONObject();
			Document doc = null;
			try {
				doc = Jsoup.connect(url).timeout(60000).get();
				Element e = doc.select("meta[name=description]").first();
				if(e!=null){
					String description=e.attr("content");
					if(description.indexOf("��Ƶ")!=-1){
						result=description;
						if(doc.html().indexOf("    <!--�����ƵLs-->")!=-1){
							Element ee=doc.select("dd.vedio_des").first();
							String result_video_href=ee.select("a[href]").attr("node-url");
							String video_intro=doc.select("em[task$=oldinfor]").select("p").first().ownText();
							 
							jsonObject.put("video_name", description);
							jsonObject.put("video_href", result_video_href);
							jsonObject.put("video_intro", video_intro.trim());
						}else if(doc.html().indexOf("�Ƽ���Ƶ")!=-1){
							//ʮ������ʱֻѡĬ�ϵ�ʮ����������ʮ����ȡ��
							description=doc.select("div.slide01_items").first().attr("data-title");
							String result_video_href=doc.select("div.slide01_items").first().attr("data-url");
							String video_intro=doc.select("div.slide01_items").select("p").first().ownText(); 
							//System.out.println(video_intro); 
							jsonObject.put("video_name", description);
							jsonObject.put("video_href", result_video_href);
							jsonObject.put("video_intro", video_intro.trim());		
						}
						
						
					}
				}
				 
			} catch (IOException e) {
				e.printStackTrace();
			}
			return jsonObject;
	}
	
}
