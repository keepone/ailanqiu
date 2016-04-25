package com.lb.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginCookie {
	/**
	 * ����Cookie���ӿڷ�����
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
		Cookie cookie=new Cookie(name, value);
		cookie.setPath("/");
		if(maxAge>0){
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		}
	}
	
	/**
	 * ��Cookie��װ��Map�У��ǽӿڷ�����
	 * @param request
	 * @return
	 */
	public static Map<String, Cookie> ReadCookieMap(HttpServletRequest request){
		Map<String,Cookie> cookieMap=new HashMap<String, Cookie>();
		Cookie[]cookies=request.getCookies();
		if(null!=cookies){
			for(Cookie cookie:cookies){
				cookieMap.put(cookie.getName(), cookie);
			}
		}
		return cookieMap;
	}
	
	
	/**
	 * �������ֻ�ȡCookie(�ӿڷ���)
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
		Map<String, Cookie> cookieMap=ReadCookieMap(request);
		if(cookieMap.containsKey(name)){
			Cookie cookie=cookieMap.get(name);
			return cookie;
		}else{
			return null;
		}
	}
}
