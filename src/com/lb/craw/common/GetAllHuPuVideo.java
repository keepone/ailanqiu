package com.lb.craw.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jiutong.test.Log;
import com.lb.utils.IoUtil;
@Component
public class GetAllHuPuVideo {
private static int sum=0;
	public static void main(String []args){
		//go();
		getPersonalVideoByCategory("bisai", "http://v.opahnet.com/nba");
	}
	//@Scheduled(cron="0 0 5 * * ?")  
//	public static void main(String[] args) {
	public static void go(){
		String categoryStart="http://v.hupu.com/nba";
		//String init_path="E:"+File.separator+"personal"+File.separator+"������Ƶ"+File.separator;
		JSONArray jsonArray=getCategoryByHuPu(categoryStart);
		int count=1;
		System.out.println("��������������������");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("���濪ʼץȡ��������");
		long start=System.currentTimeMillis();
	  for (int i = 0; i <jsonArray.size(); i++) {
			JSONObject json = (JSONObject) jsonArray.get(i);
			String category_name=json.get("name").toString();
			String category_url=json.get("url").toString();
			String ifAll=ifAll(category_url);
			if(!ifAll.equals("")){
				category_url=ifAll;
			}
			
			//��¼��cba����
			if(category_name.indexOf("CBA")==-1&&category_name.indexOf("����")==-1){
				getPersonalVideoByCategory(category_name,category_url);
			}
			
		} 
		//ifAll("http://v.hupu.com/nba/hot-day-1");
		long end=System.currentTimeMillis();
		System.out.println("�ܹ�ץȡ"+sum+"����Ƶ����ʱ"+(end-start)/1000);
		//String startURL="http://v.hupu.com/nba/new/teams";
		//getPersonalByHuPu(startURL);
		// getCategoryByHuPu(categoryStart);
	}
	/**
	 * ��ȡ������������Ƶ
	 * @param startURL
	 * @return
	 */
	public static void getPersonalVideoByCategory(String categoryName,String startURL){
		 
		boolean mark=true;
		Document doc = null;
		int count=0;
		long begin=System.currentTimeMillis();
		try {
			 while(mark){
				 doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("div.p_video");
					 Elements elements2=elements.select("div.img_outer");
					
					 for(Element element:elements2){
						 Element e=element.select("a[href]").first();
						 Element img=element.select("img[src]").first();
						 String title=e.attr("title");
						 String href=e.attr("href");
						 String image=img.attr("src");
						 if(title!=null&&!title.equals("")){
							 if(title.indexOf("?")!=-1){
								 title=title.replace("?", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("\"")!=-1){
								 title=title.replace("\"", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("\\:")!=-1){
								 title=title.replace("\\:", "");
								 System.out.println(title);
							 }
							 if(title.indexOf("/")!=-1){
								 title= title.replace("/", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("<")!=-1){
								 title= title.replace("<", "");
								 System.out.println(title);
							 }
							 if(title.indexOf(">")!=-1){
								 title= title.replace(">", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("*")!=-1){
								 title= title.replace("*", "");
								 System.out.println(title);
							 } 
							 if(title.indexOf("|")!=-1){
								 title= title.replace("|", "");
								 System.out.println(title);
							 } 
							 
							 String detailHref=getEndVideoURL(href);
							 String source="δȷ��";
							 if(detailHref.indexOf("youku")!=-1){
								 source="�ſ�";
							 }else if(detailHref.indexOf("qq")!=-1){
								 source="��Ѷ";
							 }else if(detailHref.indexOf("56")!=-1){
								 source="56";
							 }else if(detailHref.indexOf("letv")!=-1){
								 source="LETV";
							 }else if(detailHref.indexOf("sina")!=-1){
								 source="����";
							 }else if(detailHref.indexOf("ku6")!=-1){
								 source="ku6";
							 }else if(detailHref.indexOf("tudou")!=-1){
								 source="����";
							 }else if(detailHref.indexOf("souhu")!=-1){
								 source="�Ѻ�";
							 }else if(detailHref.indexOf("cntv")!=-1){
								 source="CNTV";
							 }else if(detailHref.indexOf("pptv")!=-1){
								 source="PPTV";
							 }else{
								 
							 }
				
							 if(source.equals("δȷ��")){
								 //System.out.println(title+"---"+"---"+image+"-------------------------"+source+"-----------"+detailHref);
							 }else{
								 System.out.println(title+"---"+"---"+image+"---"+source+"---"+detailHref);
							 }
							 count=count+1;
							 sum=sum+1;
						 }
						 
					 }
					 System.out.println("��Ƶ������"+elements2.size()+",�Ѿ����أ� "+sum);
					 Elements page=doc.select("div.page").select("a.next");
					 String nextPage=page.attr("abs:href");
					 if(nextPage!=null&&!nextPage.trim().equals("")){
						 startURL=nextPage;
					 }else{
						 mark=false;
					 }
			 }
			 long end=System.currentTimeMillis();
			 System.out.println(categoryName+":����������Ƶ������������"+count+",��ʱ��"+(end-begin)/1000);
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}

	
	
	public static JSONArray getCategoryByHuPu(String startURL){
		List list=new ArrayList();
		JSONArray jsonArray=new JSONArray();
		boolean mark=true;
		int count=0;
		 
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).get();
					 Elements elements=doc.select("ul.hp-threeNav-item").select("li.hp-dropDownMenu");
					 for(Element element:elements){
						 Element e=element.select("a[href]").first();
						 String title=e.attr("title");
						 String href=e.attr("href");
						 count=count+1;
						 if(title!=null&&!title.equals("")){
							 JSONObject jsonObject=new JSONObject();
							 jsonObject.put("name", title);
							 jsonObject.put("url", href);
							 jsonArray.add(jsonObject);
						 }
						 System.out.println(title+"---"+href+"----------"+count);  
					 }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonArray;
	}
	
	public static String getEndVideoURL(String startURL){
		String href="";
		Document doc = null;
		try {
					doc = Jsoup.connect(startURL).timeout(60000).get();
					 
						Element element=doc.select("div#play_box").select("a[href]").first();
						if(element==null){
							element=doc.select("div#flashcontent").select("object#videoobj").select("param").first();
							 href=element.attr("value");
						}else{
							 href=element.attr("abs:href");
						}
						 
					 
					
				 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return href;
	}
	
	/**
	 * �ж�����ҳ���Ƿ��� ����  ����  ����  ȫ��     �������ȫ�����ȡȫ����URL
	 * @param url
	 * @return
	 */
	public static String ifAll(String url){
		 String href="";
		Document doc = null;
		try {
					doc = Jsoup.connect(url).get();
					Element element=doc.select("div.left").first();
					if(element.html().indexOf("ȫ��")!=-1){
						Element e=doc.select("div.left").select("a[href]").get(3);
						href=e.attr("abs:href");	
					}
						 //abs��ȡ����·��
				
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return href;
	}
	
	
	
	 
}
