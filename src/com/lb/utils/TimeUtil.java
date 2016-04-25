package com.lb.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class TimeUtil {

	public static long getCurrentTime(){
		long time=System.currentTimeMillis();
		
		return time;
	}
	
	public static void main(String []args){
//		String time="2014-11-7";
//		long aa=dateTo(time);
//		System.out.println(toDate("1415289600000"));
		
		// dateTo();
		// toDate();
		long a =  1411056000000l;
		long b = 1411056000000l +24*60*60*1000l;
		
		System.out.println(System.currentTimeMillis());
		/*System.out.println(dateTo_2(" 2014-11-27 09:59:52"));
		System.out.println(toDate("1417053592000"));*/
	}
	
/**
 * 获取当天日期，精确到日（2014-11-24）
 * @return
 */
 public static String getToday(){
	 	String today=TimeUtil.toDate(""+System.currentTimeMillis());
		today=today.substring(0, 10);
		return today;
 }

 
 /**
  * 获取当前年份
  * @return
  */
  public static String getCurrentYear(){
 	 	String today=TimeUtil.toDate(""+System.currentTimeMillis());
 		today=today.substring(0, 4);
 		return today;
  }
 /**
  *  
  * @return
  */
  public static String getToday_2(){
 	 	String today=TimeUtil.toDate(""+System.currentTimeMillis());
 		today=toDate(today);
 		return today;
  }
 /**
  * 获取当天的时间戳，精确到分钟；
  * @return
  */
 public static long getTodayDecimal(){
	 	String today=getToday();
		long todayDecimal=dateTo(today);
		return todayDecimal;
}
 
 /**
  * 将时间戳（用户申请时间）转换为指定日期格式(yyyy-MM-dd HH时mm分)
  * @param time
  * @return
  */
	public static String toDate(String time){
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd HH时mm分");
		return  sdf.format(new Date(Long.parseLong(time)));
	}
	
	 /**
	  * 将时间戳（用户申请时间）转换为指定日期格式(yyyy-MM-dd HH时mm分)
	  * @param time
	  * @return
	  */
		public static String toDate3(String time){
			SimpleDateFormat sdf = new SimpleDateFormat(
					"MM-dd HH时mm分");
			return  sdf.format(new Date(Long.parseLong(time)));
		}
	
	 /**
	  * 将时间戳（用户申请时间）转换为指定日期格式(yyyy-MM-dd)
	  * @param time
	  * @return
	  */
		public static String toDay(String time){
			SimpleDateFormat sdf = new SimpleDateFormat(
					"yyyy-MM-dd");
			return  sdf.format(new Date(Long.parseLong(time)));
		}
	/**
	 * Date或者String转化为时间戳( "yyyy-MM-dd" );
	 * @param time
	 * @return
	 */
	public static long  dateTo(String time){
		 SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
		Date date=null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return date.getTime();
	}
	
	/**
	 * Date或者String转化为时间戳( "yyyy-MM-dd HH:mm:ss" );
	 * @param time
	 * @return
	 */
	public static long  dateTo_2(String time){
		 SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
		Date date=null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return date.getTime();
	}
	
	/**
	 * Date或者String转化为时间戳( "yyyy-MM-dd HH:mm" );
	 * @param time
	 * @return
	 */
	public static long  dateTo_3(String time){
		 SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		Date date=null;
		try {
			date = format.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return date.getTime();
	}
}
