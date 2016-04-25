package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.TimeUtil;
 
@Component
public class GetPlayerVideoByLetv {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	@Autowired
	ServiceUtil serviceUtil=null;
	
	private static List<JSONObject> list=new ArrayList<JSONObject>();
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		//http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=
		String startURL = "http://list.letv.com/listn/c4_t30232_vt-1_md_o1_d2_p.html";
		// get("http://search.sina.com.cn/?q=&range=title&c=news&sort=time&SL2=nbavideo&PID=");
		//getLetvVideo2(startURL);
		 

	}

 

	public static List<JSONObject> getLetvVideo(String startURL){
		JSONArray arr=new JSONArray();
	
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		int count=1;
		int f = 1;
		String savePath = "E:\\personal\\新浪视频\\" + f+"\\";
		//起始时间
		long begin_time=System.currentTimeMillis();
	 
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("发生异常,再次请求.................");
					try {
						Thread.sleep(5000);
						getLetvVideo(startURL);
						System.out.println("重写请求的地址是："+startURL);
					} catch (InterruptedException e1) {
						System.out.println("线程出现问题");
						 
					}
					
					 
				}
				Elements dls = doc.select("dl.w120");
				
				for (Element dl : dls) {
					String source_href = dl.select("a[href]").first().attr("href");
					String name = dl.select("img").first().attr("alt");
					String source_img = dl.select("img").first().attr("src");
						 
					 
					/*String mp4_href="";
					try {
						mp4_href = getSinaMp4Util.analyze(source_href);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} //解析后的MP4格式视频链接(可以再IPAD等移动端兼容播放) 
					long current_time=System.currentTimeMillis();
					//下载图片
					String img_name=current_time+".jpg";
					String savepath= "D:/tomcat6/Tomcat 6.0/webapps/SinaImg/"+img_name;
					IoUtil.downloadImage(source_img, "",savepath);
					long source_time2=TimeUtil.dateTo_2(source_time);
					String one_category="04";
					String two_category="0400";
					String sql="INSERT  INTO  nba_video(name,href,img,addTime,source_img,source_time,source_href,description,one_category,two_category) values(?,?,?,?,?,?,?,?,?,?)";
					Object[] params={name,mp4_href,img_name,current_time,source_img,source_time2,source_href,description,one_category,two_category};
					baseDao.insertBy(sql, params);
					System.out.println(count+"---"+source_time+"---"+name+"---"+source_img+"---"+source_href+"---"+description);
					count=count+1;
					if(count%200==0){
						System.out.println("主人,已下载:"+count+"个视频......我要休息2秒喔");
						try {
							Thread.sleep(2000);
						} catch (InterruptedException eee) {
							// TODO Auto-generated catch block
							eee.printStackTrace();
						}
					}*/
				}
			/*	Element ele=doc.select("div.pagebox").last();
				String next_page=ele.text();
				if(next_page.indexOf("下一页")!=-1){
					startURL=ele.select("a[href]").last().attr("abs:href");
					//startURL="";
					System.out.println("下一页地址："+startURL);
				}else{
					startURL="";
				}*/
			 
		 
		//结束时间
		long end_time=System.currentTimeMillis();
		System.out.println("本次从新浪视频库下载"+(count-1)+"个视频,用时:"+(end_time-begin_time)/60000+"分钟");
		return null;

	}
	
	//http://list.letv.com/listn/c4_t30232_vt-1_md_o1_d2_p.html
	public void getLetvVideo2(String startURL){
			Document doc = null;
				try {
					doc = Jsoup.connect(startURL).timeout(30000).get();
				} catch (IOException e) {
					System.out.println("发生异常,再次请求.................");
					 
				}
				Elements dls = doc.select("dt.hd_pic");
				
				for (Element dl : dls) {
					String name = dl.select("img").first().attr("alt");
					Integer sum=0;
					String sql1 = "select count(*) AS count from lb_store where NAME='"
							+ name + "'";
					sum = Integer
							.parseInt(((Map) baseDao.findBy(sql1, null)
									.get(0)).get("count").toString());
					if(sum==0){
						String source_href = dl.select("a[href]").first().attr("href");
						
						String source_img = dl.select("img").first().attr("src");
							System.out.println(name);
						 AnalyzeVideoUtil analyze=new AnalyzeVideoUtil();
							String one_category = "01";
							String two_category = "0102";
							String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
							String mp4_href=analyze.analyzeFromLetv(source_href);
							Object[] params = { name, mp4_href, source_img,
									System.currentTimeMillis(), source_img, source_href,
									one_category, two_category, "乐视",2}; 
							baseDao.insertBy(sql, params);
						 
					}
					
		 
				}
		 
	}


	//http://list.letv.com/listn/c4_t30232_vt-1_md_o1_d2_p.html
		public void getLetvZhuanJi(String startURL){
				Document doc = null;
					try {
						doc = Jsoup.connect(startURL).timeout(30000).get();
					} catch (IOException e) {
						System.out.println("发生异常,再次请求.................");
						 
					}
					Elements dls = doc.select("div.relatedvideo-list-con").select("li");
					
					for (Element dl : dls) {
						String name = dl.select("p").first().attr("title");
						Integer sum=0;
						String sql1 = "select count(*) AS count from lb_store where NAME='"
								+ name + "'";
						sum = Integer
								.parseInt(((Map) baseDao.findBy(sql1, null)
										.get(0)).get("count").toString());
						if(sum==0){
							String source_href = dl.select("li").first().attr("data-url");
							
							String source_img = dl.select("li").first().attr("data-img");
								System.out.println(name);
							 AnalyzeVideoUtil analyze=new AnalyzeVideoUtil();
								String one_category = "01";
								String two_category = "0102";
								String sql = "INSERT  INTO  lb_store(name,video_href,img,addTime,source_img,source_href,one_category,two_category,source,resourceType) values(?,?,?,?,?,?,?,?,?,?)";
								String mp4_href=analyze.analyzeFromLetv(source_href);
								Object[] params = { name, mp4_href, source_img,
										System.currentTimeMillis(), source_img, source_href,
										one_category, two_category, "乐视",2}; 
								baseDao.insertBy(sql, params);
							 
						}
						
			 
					}
			 
		}
		
	public static void get(String LOGIN_URL) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("q", "iverson");
		Connection conn = Jsoup.connect(LOGIN_URL);
		conn
				.header("(Request-Line)",
						"POST /cgi-bin/login?lang=zh_CN HTTP/1.1");
		conn.header("Accept", "application/json, text/javascript, */*; q=0.01");
		conn.header("Accept-Encoding", "gzip, deflate");
		conn.header("Accept-Language", "zh-cn");
		conn.header("Cache-Control", "no-cache");
		conn.header("Connection", "Keep-Alive");
		conn.header("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		conn.header("Host", "mp.weixin.qq.com");
		conn.header("Referer", "https://sina.com.cn/");
		conn
				.header("User-Agent",
						"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; MALC)");
		try {
			Response response = conn.execute();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
