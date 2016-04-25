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
		String savePath = "E:\\personal\\新浪视频\\" + f+"\\";
		//起始时间
		long begin_time=System.currentTimeMillis();
		while(startURL!=null&&!startURL.equals("")){
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("获取新浪NBA视频发生异常,再次请求.................，，当前线程"+Thread.currentThread().getName());
					System.out.println("地址是："+startURL);
					
					 
				}
				Elements elements1 = doc.select("div.result");
				Elements elements = elements1.select("div.box-result");
				for (Element element : elements) {
					String name = element.select("a[href]").first().ownText().replace("视频-", "");
					String source_href = element.select("div.r-img").select("a[href]")
							.attr("href");
					String source_img = element.select("img.left_img").attr("src");
					String source_time = element.select("span.fgray_time").html();
					source_time=source_time.replace("新浪体育", "");
					String description = element.select("p.content").html().replace("新浪体育", "");
					String mp4_href="";
					try {
						mp4_href = getSinaMp4Util.analyze(source_href);
					} catch (Exception e) {
						System.out.println(count+"---"+source_href+"---"+name);
					} //解析后的MP4格式视频链接(可以再IPAD等移动端兼容播放) 
					long current_time=System.currentTimeMillis();
					//下载图片
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
						System.out.println("主人,已下载:"+count+"个视频......我要休息2秒喔");
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
				if(next_page.indexOf("下一页")!=-1){
					startURL=ele.select("a[href]").last().attr("abs:href");
					//startURL="";
					System.out.println("下一页地址："+startURL);
				}else{
					startURL="";
				}
			 
		}
		//结束时间
		long end_time=System.currentTimeMillis();
		System.out.println("本次从新浪视频库下载"+(count-1)+"个视频,用时:"+(end_time-begin_time)/60000+"分钟");
		return list;

	}
	
	 
	
	
	
	//获取每天比赛视频(每日视频汇总 )http://roll.sports.sina.com.cn/s_nbavideo_video_big/index.shtml
	public  static List<JSONObject> getEveryDayMatchVideo(String startURL){
		System.out.println(Thread.currentThread().getName()+":线程正在执行录入视频任务");
		List<String> strs=new ArrayList<String>();
		
		
		JSONArray arr=new JSONArray();
		boolean status=true;
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		int count=1;
		int f = 1;
		String savePath = "E:\\personal\\新浪视频\\" + f+"\\";
		//起始时间
		long begin_time=System.currentTimeMillis();
		
		while(status){
			for(int i=1;i<=5;i++){  // 只抓取前3页数据，如果前三页中，有不是今天的数据，立即结束抓取，如果有某个视频已经录入，不会停止抓取，而是跳过该视频，继续判断下一个视频是否是今天的数据或已经录入。知道发现不是今日数据才结束抓取
				Document doc = null;
				try {
					doc = Jsoup.connect(startURL+i+".shtml").timeout(500000).get();
				}catch (IOException e) {
					System.out.println("获取新浪NBA视频发生异常");
					System.out.println("地址是："+startURL);
				}
				Elements elements = doc.select("div#videoList");
				Elements divs = elements.select("div.videoBox"); 	 
					for (Element div : divs) {
						String source_href = div.select("a[href]").first().attr("abs:href");
						String today=TimeUtil.getToday();
						
						
						if(source_href.indexOf(today)!=-1){
							
							String name = div.select("img").first().attr("alt");	
							if(name.indexOf("视频-")!=-1){
								name=name.replace("视频-", "");
							}
							String one_category="01";
							String two_category="0102";
							String sql="";
							
							String source_img = div.select("img").first().attr("src");
							String source_time = div.select("div.c_info").text();
							Integer c=0;
							//System.out.println(name);

							//当日最佳球,如果是十佳球则从lb_resource中查询
							if(name.indexOf("集锦")!=-1||name.indexOf("官方10佳球")!=-1||name.indexOf("官方十佳")!=-1||name.indexOf("官方最佳")!=-1||name.indexOf("官方10")!=-1||name.indexOf("五佳")!=-1||name.indexOf("5佳")!=-1){
								two_category=two_category+"11";  //010211表示视频集锦
								if(name.indexOf("视频集锦-")!=-1){
									name=name.replace("视频集锦-", "");
								}
								//当日最佳球
								c=serviceUtil.getCountBySql("select count(*) AS count from lb_resource where name='"+name+"'");
								sql="INSERT INTO lb_resource(name,video_href,sourceVideoHref,source_href,img,source_img,source_time,addTime,updateTime,one_category,two_category,resourceType) values(?,?,?,?,?,?,?,?,?,?,?,?)";
							}else{
								c=serviceUtil.getCountBySql("select count(*) AS count from lb_store where name='"+name+"'");
								sql="INSERT INTO lb_store(name,video_href,sourceVideoHref,source_href,img,source_img,source_time,addTime,updateTime,one_category,two_category,resourceType) values(?,?,?,?,?,?,?,?,?,?,?,?)";
							
							}
							if(c==0){
								//System.out.println(name+"----正在录入");
								source_time=source_time.replace("新浪体育", "");
								String mp4_href="";
								try {
									mp4_href = getSinaMp4Util.analyze(source_href);
								} catch (Exception e) {
									System.out.println("该视频未能解析"); //如果不能解析视频，则跳过该视频
								} //解析后的MP4格式视频链接(可以在IPAD等移动端兼容播放) 
								long current_time=System.currentTimeMillis();
								//下载图片
								String img_name=current_time+".jpg";
								String savepath= "D:/tomcat6/Tomcat 6.0/webapps/SinaMatchImg/"+img_name;
								//IoUtil.downloadImage(source_img, "",savepath);
								
								source_time=source_time.replace("月", "-").replace("日", "");
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
		 
		//结束时间
		long end_time=System.currentTimeMillis();
		System.out.println("本次从新浪视频库下载"+(count-1)+"个视频,用时:"+(end_time-begin_time)/1000+"秒");
		return list;

	}
	

	 
}
