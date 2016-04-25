package com.lucene.crawl;

import java.io.IOException;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
import org.jsoup.Connection;
import org.jsoup.Jsoup;
 
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.TimeUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
 
@Component
public class GetPlayerVideoBySina extends ActionSupport {
	private static BaseDao baseDao;
	private static ServiceUtil serviceUtil=null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Resource(name = "serviceUtil")
	public  void setServiceUtil(ServiceUtil serviceUtil) {
		GetPlayerVideoBySina.serviceUtil = serviceUtil;
	}


	private static List<JSONObject> list=new ArrayList<JSONObject>();
	public static void main(String[] args) throws InterruptedException {
		

	}

  
	public static List<JSONObject> getSinaVideo(String startURL){
		JSONArray arr=new JSONArray();
	
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		int count=1;
		int f = 1;
		String savePath = "E:\\personal\\������Ƶ\\" + f+"\\";
		//��ʼʱ��
		long begin_time=System.currentTimeMillis();
		while(startURL!=null&&!startURL.equals("")){
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("��ȡ����NBA��Ƶ�����쳣,�ٴ�����.................������ǰ�߳�"+Thread.currentThread().getName());
					System.out.println("��ַ�ǣ�"+startURL);
					
					 
				}
				Elements elements1 = doc.select("div.result");
				Elements elements = elements1.select("div.box-result");
				for (Element element : elements) {
					String name = element.select("a[href]").first().ownText().replace("��Ƶ-", "");
					String source_href = element.select("div.r-img").select("a[href]")
							.attr("href");
					String source_img = element.select("img.left_img").attr("src");
					String source_time = element.select("span.fgray_time").html();
					source_time=source_time.replace("��������", "");
					String description = element.select("p.content").html().replace("��������", "");
					String mp4_href="";
					try {
						mp4_href = getSinaMp4Util.analyze(source_href);
					} catch (Exception e) {
						System.out.println(count+"---"+source_href+"---"+name);
					} //�������MP4��ʽ��Ƶ����(������IPAD���ƶ��˼��ݲ���) 
					long current_time=System.currentTimeMillis();
					//����ͼƬ
					//String img_name=current_time+".jpg";
					//String savepath= "D:/tomcat6/Tomcat 6.0/webapps/SinaStarPlayerImg/"+img_name;
					//IoUtil.downloadImage(source_img, "",savepath);
					//long source_time2=TimeUtil.dateTo_2(source_time);
					String one_category="01";
					String two_category="010213";
					String sql="INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,content,one_category,two_category,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,mp4_href,source_img,current_time,source_img,source_href,description,one_category,two_category,2};
					baseDao.insertBy(sql, params);
//					System.out.println(count+"---"+source_time+"---"+name+"---"+source_img+"---"+source_href+"---"+description);
					count=count+1;
					if(count%200==0){
						System.out.println("����,������:"+count+"����Ƶ......��Ҫ��Ϣ2���");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException eee) {
							// TODO Auto-generated catch block
							eee.printStackTrace();
						}
					}
				}
				Element ele=doc.select("div.pagebox").last();
				String next_page=ele.text();
				if(next_page.indexOf("��һҳ")!=-1){
					startURL=ele.select("a[href]").last().attr("abs:href");
					//startURL="";
					System.out.println("��һҳ��ַ��"+startURL);
				}else{
					startURL="";
				}
			 
		}
		//����ʱ��
		long end_time=System.currentTimeMillis();
		System.out.println("���δ�������Ƶ������"+(count-1)+"����Ƶ,��ʱ:"+(end_time-begin_time)/60000+"����");
		return list;

	}
	
	 
	
	
	
	//��ȡÿ�������Ƶ(ÿ����Ƶ���� )http://roll.sports.sina.com.cn/s_nbavideo_video_big/index.shtml
	public  static List<JSONObject> getEveryDayMatchVideo(String startURL){
		System.out.println(Thread.currentThread().getName()+":�߳�����ִ��¼����Ƶ����");
		List<String> strs=new ArrayList<String>();
		
		
		JSONArray arr=new JSONArray();
		boolean status=true;
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		int count=1;
		int f = 1;
		String savePath = "E:\\personal\\������Ƶ\\" + f+"\\";
		//��ʼʱ��
		long begin_time=System.currentTimeMillis();
		
		while(status){
			for(int i=1;i<=5;i++){  // ֻץȡǰ3ҳ���ݣ����ǰ��ҳ�У��в��ǽ�������ݣ���������ץȡ�������ĳ����Ƶ�Ѿ�¼�룬����ֹͣץȡ��������������Ƶ�������ж���һ����Ƶ�Ƿ��ǽ�������ݻ��Ѿ�¼�롣֪�����ֲ��ǽ������ݲŽ���ץȡ
				Document doc = null;
				try {
					doc = Jsoup.connect(startURL+i+".shtml").timeout(500000).get();
				}catch (IOException e) {
					System.out.println("��ȡ����NBA��Ƶ�����쳣");
					System.out.println("��ַ�ǣ�"+startURL);
				}
				Elements elements = doc.select("div#videoList");
				Elements divs = elements.select("div.videoBox"); 	 
					for (Element div : divs) {
						String source_href = div.select("a[href]").first().attr("abs:href");
						String today=TimeUtil.getToday();
						
						
						if(source_href.indexOf(today)!=-1){
							
							String name = div.select("img").first().attr("alt");	
							if(name.indexOf("��Ƶ-")!=-1){
								name=name.replace("��Ƶ-", "");
							}
							String one_category="01";
							String two_category="0102";
							String sql="";
							
							String source_img = div.select("img").first().attr("src");
							String source_time = div.select("div.c_info").text();
							Integer c=0;
							//System.out.println(name);

							//���������,�����ʮ�������lb_resource�в�ѯ
							if(name.indexOf("����")!=-1||name.indexOf("�ٷ�10����")!=-1||name.indexOf("�ٷ�ʮ��")!=-1||name.indexOf("�ٷ����")!=-1||name.indexOf("�ٷ�10")!=-1||name.indexOf("���")!=-1||name.indexOf("5��")!=-1){
								two_category=two_category+"11";  //010211��ʾ��Ƶ����
								if(name.indexOf("��Ƶ����-")!=-1){
									name=name.replace("��Ƶ����-", "");
								}
								//���������
								c=serviceUtil.getCountBySql("select count(*) AS count from lb_resource where name='"+name+"'");
								sql="INSERT INTO lb_resource(name,video_href,sourceVideoHref,source_href,img,source_img,source_time,addTime,updateTime,one_category,two_category,resourceType) values(?,?,?,?,?,?,?,?,?,?,?,?)";
							}else{
								c=serviceUtil.getCountBySql("select count(*) AS count from lb_store where name='"+name+"'");
								sql="INSERT INTO lb_store(name,video_href,sourceVideoHref,source_href,img,source_img,source_time,addTime,updateTime,one_category,two_category,resourceType) values(?,?,?,?,?,?,?,?,?,?,?,?)";
							
							}
							if(c==0){
								//System.out.println(name+"----����¼��");
								source_time=source_time.replace("��������", "");
								String mp4_href="";
								try {
									mp4_href = getSinaMp4Util.analyze(source_href);
								} catch (Exception e) {
									System.out.println("����Ƶδ�ܽ���"); //������ܽ�����Ƶ������������Ƶ
								} //�������MP4��ʽ��Ƶ����(������IPAD���ƶ��˼��ݲ���) 
								long current_time=System.currentTimeMillis();
								//����ͼƬ
								String img_name=current_time+".jpg";
								String savepath= "D:/tomcat6/Tomcat 6.0/webapps/SinaMatchImg/"+img_name;
								//IoUtil.downloadImage(source_img, "",savepath);
								
								source_time=source_time.replace("��", "-").replace("��", "");
								source_time=TimeUtil.getCurrentYear()+"-"+source_time;
								long source_time2=TimeUtil.dateTo_3(source_time);
								
							
								Object[] params={name,mp4_href,source_href,source_href,source_img,source_img,source_time2,current_time,current_time,one_category,two_category,2};
								
								//								String videoHref=json.getString("videoHref");
								
								baseDao.insertBy(sql, params);
								System.out.println(count+"---"+source_time+"---"+name+"---"+source_img+"---"+source_href+"---");
								count=count+1;
							}else{
//								status=false;
//								break;
							}
							
						}else{
							status=false;
							break;
						}
					}
				 
				 if(!status){
					 status=false;
					 break;
				 }
			}
			 
		}
		 
		//����ʱ��
		long end_time=System.currentTimeMillis();
		System.out.println("���δ�������Ƶ������"+(count-1)+"����Ƶ,��ʱ:"+(end_time-begin_time)/1000+"��");
		return list;

	}
	

	 
}
