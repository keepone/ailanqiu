package com.lb.utils;

import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MapUtil {

	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";
	public final static String phoneUserAgent = "Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A5313e Safari/7534.48.3";
	
	public  static void main(String [] args){
		double distance=getDistanceByName("上海市杨浦区大学路307号", "上海市虹口区虹口足球场");
		System.out.println(distance/1000);
	}
	/**
	 * 根据地址获取经纬度
	 * @param areaDetail
	 * @return
	 */
	 public static JSONObject getLngAndLat(String areaDetail){
//		 if(areaDetail.length()>25){
//			 int index=areaDetail.indexOf("(");
//			 areaDetail=areaDetail.substring(0,index);
//		 }
			JSONObject json=new JSONObject();
			try {
				String ak="jXwKZ1LZ1aGSLO4GhqaiQo36";
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
//				String con=IoUtil.readTxtFile("/Users/Allen/tomcat7/经纬度错误.txt").toString();
//				con=con+"\r\n"+areaDetail+":   \r\n";
//				IoUtil.writeFile("/Users/Allen/tomcat7/fault.txt", con);
				e.printStackTrace();
			}
			
			return json;
		}
	 
	 /**
	  * 根据经纬度求距离
	  * @param lng1
	  * @param lat1
	  * @param lng2
	  * @param lat2
	  * @return
	  */
	  public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
	        double radLat1 = lat1 * Math.PI / 180;
	        double radLat2 = lat2 * Math.PI / 180;
	        double a = radLat1 - radLat2;
	        double b = lng1 * Math.PI / 180 - lng2 * Math.PI / 180;
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
	                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
	        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
	        s = Math.round(s * 10000) / 10000;
	        
	        return s;
	    }
	  
		 /**
		  * 根据2个详细地址求距离
		  * @param lng1
		  * @param lat1
		  * @param lng2
		  * @param lat2
		  * @return
		  */
		  public static double getDistanceByName(String place1,String place2) {
			    JSONObject json1=getLngAndLat(place1);
			    JSONObject json2=getLngAndLat(place2);
		        double s=getDistance(json1.getDouble("lng"), json1.getDouble("lat"), json2.getDouble("lng"), json2.getDouble("lat"));
		        return s;
		    }
	 
	 
}
