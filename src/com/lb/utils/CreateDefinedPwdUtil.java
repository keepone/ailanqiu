package com.lb.utils;

import java.util.UUID;

import net.sf.json.JSONObject;

public class CreateDefinedPwdUtil {

	/**
	 * @author Allen
	 * @date 2014-12-4
	 * @function 自定义算法对用户ID进行加密
	 */
	public static void main(String[] args) {
	 String encryp_pwd=encryp(1988);  //数字2加密后的密码
	 int deco_pwd=Decoding(encryp_pwd);         //对数字2加密后的密码解密
	 
	 System.out.println(encryp_pwd);
	 System.out.println(deco_pwd);
	}
	/**
	 * 加密(针对整数类型)
	 * @param uid
	 * @return
	 */
//	public static String encryp(Integer uid){
//		//String uuid=UUID.randomUUID().toString().replace("-", "");
////		long currentTime=System.currentTimeMillis();
//		String uuid="cb623c09a5374be781f19b89d3c80da5";
//		long currentTime=TimeUtil.dateTo("2015-03-19");
//		String time=String.valueOf(currentTime);
//		String four=String.valueOf(time.substring(0,5));
//		String five=String.valueOf(time.substring(5,time.length()));
//		String result="";
//		Integer id=0;
//		id=uid*1215-17;
//		
//		String one=uuid.substring(0,9);
//		String two=uuid.substring(9,12);
//		String three=uuid.substring(12,uuid.length());
//	 
// 
//		result=one+four+two+five+three+id;
// 
//		
//		return result;
//	}
	
	public static String encryp(Integer uid){
		uid=uid+903;
		String pwd=String.valueOf(uid);
 
		 
 
		
		return pwd;
	}
	/**
	 * 解密.返回一个实际的用户ID
	 * @param str
	 * @return
	 */
//	public static int Decoding(String str){
//		//?spm=a1z02.1.754894437.1.KMVknE&ad_id=&am_id=&cm_id=&pm_id=1501036000a02c5c3739
//		String one=str.substring(0,9);
//		String two=str.substring(14,17);
//		String three=str.substring(25,45);
//		String four=str.substring(45,str.length());
//		Integer five=Integer.parseInt(four);
//		five=(five+17)/1215;
//		Integer uid=five;
//		String uuid=one+two+three;
//		JSONObject json=new JSONObject();
//		 
//		 
//		return uid;
//	}
	
	public static int Decoding(String str){
		 
		 Integer a=Integer.parseInt(str);
		 a=a-903;
		 
		 
		return a;
	}
}
