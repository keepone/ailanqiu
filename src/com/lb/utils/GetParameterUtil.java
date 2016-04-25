package com.lb.utils;

import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

public class GetParameterUtil {
	public static String getString(String str){
		String result="";
		String param=ServletActionContext.getRequest().getParameter(str);
		if(StrUtil.isNotEmpty(param)){
			 result=ServletActionContext.getRequest().getParameter(str);
			 try {
				result=new String(result.getBytes("ISO-8859-1"),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	//针对POST提交参数。
	public static String getString_2(String str){
		 
		String param=ServletActionContext.getRequest().getParameter(str);
		 
		return param;
	}
	
	
	public static Integer getInteger(String str){
		Integer result=0;
		String param=ServletActionContext.getRequest().getParameter(str);
		if(StrUtil.isNotEmpty(param)){
			 result=Integer.parseInt(param);
		}
		 
		return result;
	}
}
