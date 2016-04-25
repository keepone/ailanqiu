package com.lb.utils;

import java.util.ArrayList;
import java.util.List;

public class StrUtil {
	
	public static void main(String [] args){
		String content="<p>腾讯体育11月17日讯等等</p>";
		 
		getStrBySubString(content, "<p>", "</p>");
		
	}
	
 
	
	public static String getStrByRemoveFistStr(String content,String beginStr){
		String str="";
		if(!content.equals("")){
			if(content.indexOf(beginStr)!=-1){
				int start=content.indexOf(beginStr);
				 
				Integer count=start+beginStr.length();
				str=content.substring(count,content.length());
			}else{
				str=content;
			}
		}
		
		
	 
		 
		return str;
	}
	
	public static boolean isEmpty(String str){
		boolean flag=false;
		if(str!=null&&!str.equals("")){
			flag=true;
		}
		 
		return flag;
	}
	
	
	public static boolean isNotEmpty(String str){
		boolean flag=false;
		if(str!=null&&!str.equals("")&&!str.equals("null")){
			flag=true;
		}
		 
		return flag;
	}
	
	/**
	 * 截取字符串
	 * @param content
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String getStrBySubString(String content,String beginStr,String endStr){
		String str="";
		int start=content.indexOf(beginStr);
		int end=content.indexOf(endStr);
		Integer count=start+beginStr.length();
		str=content.substring(count,end);
		System.out.println(str);
		 
		return str;
	}
	
	
	/**
	 * 
	 * @param content
	 * @param str
	 * @return
	 */
	public static String replaceStrForCraw(String str){
		 
		str=str.replaceAll("(微博)", "");
		str=str.replaceAll("新浪体育讯", "").replaceAll("已收藏!", "").replace("您可通过新浪首页顶部 “”， 查看所有收藏过的文章。", "")
				.replace("知道了", "");
		return str;
	}
	
	/**
	 * 截取字符串，升级版，新增根据偏移量截取
	 * @param content
	 * @param beginStr
	 * @param endStr
	 * @param beginNum
	 * @param endNum
	 * @return
	 */
	public static String getStrBySubString_2(String content,String beginStr,String endStr,Integer beginNum,Integer endNum){
		String str="";
		int start=content.indexOf(beginStr);
		int end=content.indexOf(endStr);
		str=content.substring(start+beginStr.length(),end-endNum);
		System.out.println(str);
		 
		return str;
	}
	
	/**
	 * 截取字符串,返回前半段字符串
	 * @param content
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String getBeforeStrBySubString(String content,String tag){
		String str="";
		int start=content.indexOf(tag);
		 
		str=content.substring(0,start);
		System.out.println(str);
		 
		return str;
	}
	
	/**
	 * 截取字符串,返回后半段字符串
	 * @param content
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	public static String getAfterStrBySubString(String content,String tag){
		String str="";
		int start=content.indexOf(tag);
		str=content.substring(start+1,content.length());
		System.out.println(str);
		 
		return str;
	}
	
	/**
	 * 过滤空格，空字符串,然后加上自定义的规则
	 * @param text
	 * @return
	 */
	public static String filterStr(String text){
		text=text.replaceAll("\u3000", "").replace("&nbsp;", "").replace(" ", "");
		text="<p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+text+"</p>";
		return text;
	}
}
