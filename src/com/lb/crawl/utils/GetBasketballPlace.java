package com.lb.crawl.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
 
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.UploadPhotoToQiNiu;
import com.lucene.crawl.GetProxyIpUtil;
@Component
public class GetBasketballPlace {
	private static final  double EARTH_RADIUS = 6378137;//赤道半径(单位m)  
	private static BaseDao baseDao=null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	private static ServiceUtil serviceUtil=null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	public static void main(String[] args) {
		
//		Object [] obj={2.33,4.1,1.0,2.5};
//		List<Double> list=new ArrayList<Double>();
//		list.add(2.33);
//		list.add(1.0);
//		 Collections.sort(list);
//		//Arrays.sort(obj);
//		// Arrays.sort(obj);
//		 for(int i=0;i<list.size();i++){
//			 System.out.println(list.get(i));
//		 }
		double a=2458.0226578825423;
		System.out.println(a/1000);
		for(int i=1;i<=1;i++){
			//get(); 121.53571659963-----31.304510479542
			JSONObject json1=getLngAndLat("上海浦东新区浦东新区 江东路(张江农工商附近 镇政府对面)((张江农工商附近 镇政府对面))");
			JSONObject json2=getLngAndLat("上海财经大学篮球场");
			distanceByLngLat(json1.getDouble("lng"), json1.getDouble("lat"), json2.getDouble("lng"), json2.getDouble("lat"));
			distanceByLngLat(121.5143684780406, 31.30881354550348, json2.getDouble("lng"), json2.getDouble("lat"));
		//1,2,3,11,5,6,
			Object [] p={13,21,22,10,160,18,19,79,4,7,8,9,
					17,14,15,16,110,344,94,98,104,105,101,145,148,149,219,
					92,99,108,150,152,155,213,385,35,93,208,24,102,416,12,70,
					258,267,151,62,26,23,146,27,162,206,220,345,147,59,80,44,299,180,
					179,196,194,111,46,167,103,209,224,25,71,97,58,106,107,95,
					42,184,260,313,225,226,47,33,134,242,217,210,119,137,153,120,
					143,112,161,192,1009,292,197,218,113,321,211,116,133,96,
					325,129,207,127,29,166,163,84,241,115,109,277,130,200,114,157,132};
			
			System.out.println("uu"+p[3]);
		}
		//useUsereAgent();
		
	}
	
//	public static void get(){
//		int sum=0;
//		try {
//			
//			Object [] p={1,2,3,11,5,6,13,21,22,10,160,18,19,79,4,7,8,9,17,14,15,16,110,344,94,98,104,105,101,145,148,149,219,92,99,108,150,152,155,213,385,35,93,208,24,102,416,12,70,258,267,151,62,26,23,146,27,162,206,220,345,147,59,80,44,299,180,
//					179,196,194,111,46,167,103,209,224,25,71,97,58,106,107,95,42,184,260,313,225,226,47,33,134,242,217,210,119,137,153,120,143,112,161,192,1009,292,197,218,113,321,211,116,133,96,325,129,207,127,29,166,163,84,241,115,109,277,130,200,114,157,132};
//			
//			
//			for(int i=1;i<=18;i++){
//				Document doc=Jsoup.connect("http://www.dianping.com/search/keyword/1/0_%E7%AF%AE%E7%90%83%E5%9C%BA/p"+i).userAgent(USERAGENT).timeout(100000).get();
//				Elements divs=doc.select("div.pic");
//				for(Element div:divs){
//					String href=div.select("a[href]").first().attr("abs:href");
//					String name=div.select("img").first().attr("title");
//					String img=div.select("img").first().attr("data-src");
//					if(name.indexOf("篮球")!=-1){
//						getAreadDetail(href, name, img,city);
//						sum=sum+1;
//					}else if(name.indexOf("体育")!=-1){
//						getAreadDetail(href, name, img);
//						sum=sum+1;
//						
//					}
//					
//				}
//			}
//			
//			System.out.println("发现球馆：  "+sum);
//		} catch (Exception e) {
//			 
//			e.printStackTrace();
//		}
//	}
	
	
	public static void addCourtByCraw(){
		int sum=0;
		try {
			
			//1,2,3,11,5,6,
			Object [] p={13,21,22,10,160,18,19,79,4,7,8,9,
					17,14,15,16,110,344,94,98,104,105,101,145,148,149,219,
					92,99,108,150,152,155,213,385,35,93,208,24,102,416,12,70,
					258,267,151,62,26,23,146,27,162,206,220,345,147,59,80,44,299,180,
					179,196,194,111,46,167,103,209,224,25,71,97,58,106,107,95,
					42,184,260,313,225,226,47,33,134,242,217,210,119,137,153,120,
					143,112,161,192,1009,292,197,218,113,321,211,116,133,96,
					325,129,207,127,29,166,163,84,241,115,109,277,130,200,114,157,132};
			//http://www.dianping.com/search/keyword/1/0_%E7%AF%AE%E7%90%83%E5%9C%BA/p1
			String url="http://www.dianping.com/search/keyword/";
			String cons=url;
			for(int i=0;i<p.length;i++){
				int num=(Integer) p[i];
				url=cons+""+num+"/0_%E7%AF%AE%E7%90%83%E5%9C%BA";
				String constant=url;
				boolean flag=true;
				int pageNum=1;
				Document doc=null;
				
				while(flag){
					int count=0;
					url=constant+"/p"+pageNum;
					String host="";
					Integer port=0;
 				
					//doc=Jsoup.connect(url).userAgent(USERAGENT).timeout(100000).get();
					
					JSONObject json=GetProxyIpUtil.readOneProxyIP();
					 host=json.getString("ip");
					 port=json.getInt("port");
					 GetJsoupDocByProxyUtil getJsoupDocByProxyUtil=new GetJsoupDocByProxyUtil();
					doc=getJsoupDocByProxyUtil.getDoc(url, host, port, USERAGENT);
					if(doc!=null&&!doc.outerHtml().equals("")){
						
						String city=doc.select("a.city").first().text();
						System.out.println("当前城市:"+city);
						if(doc.outerHtml().indexOf("没有找到相应的商户")!=-1){   //该城市没有篮球场
							flag=false;  //结束当前城市
							String con=IoUtil.readTxtFileAsList("/Users/Allen/tomcat7/court.txt").toString();
							IoUtil.writeFile("/Users/Allen/tomcat7/", "court", con+"\r\n"+city+"-------------------:   "+count);
						}else{
							
							Elements divs=doc.select("div.pic");
							for(Element div:divs){
								String href=div.select("a[href]").first().attr("href");
								href="http://www.dianping.com"+href;
								String name=div.select("img").first().attr("title");
								String img=div.select("img").first().attr("data-src");
								if(name.indexOf("篮球")!=-1){
									getAreadDetail(href,host,port, name, img,city);
									sum=sum+1;
									count=count+1;
								}else if(name.indexOf("体育")!=-1){
									getAreadDetail(href,host,port, name, img,city);
									sum=sum+1;
									count=count+1;
								}
								
							}
							String pages=doc.select("div.page").outerHtml();
							if(pages.indexOf("下一页")!=-1){
								String con=IoUtil.readTxtFileAsList("/Users/Allen/tomcat7/courtlog.txt").toString();
								con=con+"\r\n"+url;
								IoUtil.writeFile("/Users/Allen/tomcat7/", "courtlog", con);
								pageNum++;  //页数加1
							}else{
								flag=false;  //已经没有下一页，结束当前城市
								String con=IoUtil.readTxtFileAsList("/Users/Allen/tomcat7/court.txt").toString();
								IoUtil.writeFile("/Users/Allen/tomcat7/", "court", con+"\r\n"+city+":   "+count);
							}
						}
					
						
					}
					else{
						
						Thread.sleep(1000);
//						JSONObject json1=GetProxyIpUtil.readOneProxyIP();
//						String ip=json1.getString("ip");
//						Integer port2=json1.getInt("port");
						//doc=GetJsoupDocByProxyUtil.getDoc(url, ip,port2, USERAGENT);
					}
					
					
				}
			
			}
			
			System.out.println("发现球馆：  "+sum);
		} catch (Exception e) {
			 
			e.printStackTrace();
		}
	}
	
	
	public static void getAreadDetail(String href,String host,Integer port,String name,String img,String city){
		try {
			 //Document doc=Jsoup.connect(href).userAgent(USERAGENT).timeout(10000).post();
//			
			 //JSONObject json2=GetProxyIpUtil.readOneProxyIP();
//			 host=json2.getString("ip");
//			 port=json2.getInt("port");
			GetJsoupDocByProxyUtil getJsoupDocByProxyUtil=new GetJsoupDocByProxyUtil();
			Document doc=getJsoupDocByProxyUtil.getDoc(href, host, port, USERAGENT);
			 
			
			if(doc!=null&&!doc.ownerDocument().equals("")){
				Element areaElement=doc.select("div.expand-info").first();
				 
				String area=doc.select("span[itemprop=locality region]").first().text();
				String areaDetail=doc.select("div.expand-info").first().select("span").last().ownText();
				String telephone="";
				String mobilephone="";
				String workTime="";
				Element phone=doc.select("p.expand-info").first();
				if(phone.outerHtml().indexOf("无")!=-1){
					
				}else{
					  telephone=phone.select("span.item").first().ownText();
					  mobilephone=phone.select("span.item").last().ownText();
				}
				
				Elements time=doc.select("div.other");
				if(time.outerHtml().indexOf("无")!=-1){
					
				}else{
					workTime=time.select("span.item").last().ownText();	
				}
				
				
				String sql="INSERT INTO lb_basketball_court (name,img,telephone,mobilephone,worktime,area,areaDetail,lng,lat,city) VALUES(?,?,?,?,?,?,?,?,?,?)";
				if(!telephone.equals("")&&telephone.length()==11){
					String param="";
					param=telephone;
					telephone=mobilephone;
					mobilephone=param;
				}else{
					
				}
				
				String ad=city+area+areaDetail;
				JSONObject json=getLngAndLat(ad);
				double lng=json.getDouble("lng");
			    double lat=json.getDouble("lat");
				Object [] params={name,img,telephone,mobilephone,workTime,area,areaDetail,lng,lat,city};
				String have_sql="SELECT COUNT(*) AS count FROM lb_basketball_court WHERE name='"+name+"'";
				int c=serviceUtil.getCountBySql(have_sql);
				if(c==0){
					baseDao.insertBy(sql, params);
					System.out.println(name+"----成功录入");
				}else{
					System.out.println(name+"|||||||之前已经录入");
				}
				
			} 
			
		} catch (Exception e) {
			System.out.println(name+"--出现异常");
			e.printStackTrace();
		}
	}
	
	
	public static Integer addCourtByUser(String name,String img,String telephone,String workTime,String province,String city,String area,String areaDetail){
		Integer status=1;
		try {		 
			String mobilephone="";
			String sql="INSERT INTO lb_basketball_court (name,img,telephone,mobilephone,worktime,province,city,area,areaDetail) VALUES(?,?,?,?,?,?,?,?,?)";
			if(!telephone.equals("")&&telephone.length()==11){
				String param="";
				param=telephone;
				telephone=mobilephone;
				mobilephone=param;
			}else{
				
			}
			
//			String ad="上海市"+area+areaDetail;
//			JSONObject json=getLngAndLat(ad);
//			double lng=json.getDouble("lng");
//		    double lat=json.getDouble("lat");
			
			String imgName=UploadPhotoToQiNiu.uploadByIO(img);
			
			Object [] params={name,imgName,telephone,mobilephone,workTime,province,city,area,areaDetail};
			baseDao.insertBy(sql, params);
			
		 
		} catch (Exception e) {
			status=0;
			 
			e.printStackTrace();
		}
		return  status;
	}
	public static JSONObject getLngAndLat(String areaDetail){
		JSONObject json=new JSONObject();
		try {
			String ak="jXwKZ1LZ1aGSLO4GhqaiQo36";
			//去掉括号 上海浦东新区浦东新区 江东路(张江农工商附近 镇政府对面)((张江农工商附近 镇政府对面))
			int index=areaDetail.indexOf("(");
			if(index>0){
				areaDetail=areaDetail.substring(0,index);
			}
			String url="http://api.map.baidu.com/geocoder/v2/?address="+areaDetail+"&output=json&ak="+ak+"&callback=showLocation";
			Document doc=Jsoup.connect(url).userAgent(phoneUserAgent).timeout(10000).get();
			String content=doc.outerHtml();
			int lng_index=content.indexOf("lng");
			
			String content_2=content.substring(lng_index,content.length());
			int index_1=content_2.indexOf(":");
			int index_2=content_2.indexOf(",");
			
			String lng=content_2.substring(index_1+1,index_2);
			
			  index_1=content_2.indexOf("lat");
			  index_2=content_2.indexOf("}");
			
			String lat=content_2.substring(index_1+10,index_2);
			
			double ln=Double.valueOf(lng);
			double la=Double.valueOf(lat);
			
			json.put("lng", ln);
			json.put("lat", la);
			
			//System.out.println(lng+"-----"+lat);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}

	
    public static double distanceByLngLat(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double a = radLat1 - radLat2;
        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
System.out.println(s);
        return s;
    }
    
    /** 
     * 转化为弧度(rad) 
     * */  
    private static double rad(double d)  
    {  
       return d * Math.PI / 180.0;  
    }  
    

    /** 
     * 基于余弦定理求两经纬度距离 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位km 
     * */  
    public static double LantitudeLongitudeDist(double lon1, double lat1,double lon2, double lat2) {  
        double radLat1 = rad(lat1);  
        double radLat2 = rad(lat2);  
  
        double radLon1 = rad(lon1);  
        double radLon2 = rad(lon2);  
  
        if (radLat1 < 0)  
            radLat1 = Math.PI / 2 + Math.abs(radLat1);// south  
        if (radLat1 > 0)  
            radLat1 = Math.PI / 2 - Math.abs(radLat1);// north  
        if (radLon1 < 0)  
            radLon1 = Math.PI * 2 - Math.abs(radLon1);// west  
        if (radLat2 < 0)  
            radLat2 = Math.PI / 2 + Math.abs(radLat2);// south  
        if (radLat2 > 0)  
            radLat2 = Math.PI / 2 - Math.abs(radLat2);// north  
        if (radLon2 < 0)  
            radLon2 = Math.PI * 2 - Math.abs(radLon2);// west  
        double x1 = EARTH_RADIUS * Math.cos(radLon1) * Math.sin(radLat1);  
        double y1 = EARTH_RADIUS * Math.sin(radLon1) * Math.sin(radLat1);  
        double z1 = EARTH_RADIUS * Math.cos(radLat1);  
  
        double x2 = EARTH_RADIUS * Math.cos(radLon2) * Math.sin(radLat2);  
        double y2 = EARTH_RADIUS * Math.sin(radLon2) * Math.sin(radLat2);  
        double z2 = EARTH_RADIUS * Math.cos(radLat2);  
  
        double d = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)+ (z1 - z2) * (z1 - z2));  
        //余弦定理求夹角  
        double theta = Math.acos((EARTH_RADIUS * EARTH_RADIUS + EARTH_RADIUS * EARTH_RADIUS - d * d) / (2 * EARTH_RADIUS * EARTH_RADIUS));  
        double dist = theta * EARTH_RADIUS;  
        System.out.println(dist);
        return dist;  
    }
    
    
    /** 
     * 基于googleMap中的算法得到两经纬度之间的距离,计算精度与谷歌地图的距离精度差不多，相差范围在0.2米以下 
     * @param lon1 第一点的精度 
     * @param lat1 第一点的纬度 
     * @param lon2 第二点的精度 
     * @param lat3 第二点的纬度 
     * @return 返回的距离，单位km 
     * */  
    public static double GetDistance(double lon1,double lat1,double lon2, double lat2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;  
       double b = rad(lon1) - rad(lon2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 10000) / 10000;  
       System.out.println(s);
       return s;  
    }
}