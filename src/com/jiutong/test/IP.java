package com.jiutong.test;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.lb.utils.StrUtil;
import com.opensymphony.xwork2.ActionContext;

public class IP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ActionContext ctx = ActionContext.getContext();   
		HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
		//System.out.println(ServletActionContext.getRequest().getRemoteAddr());

	}
	/**
     * ��ȡ������IP
     * 
     * ��һ�������ʹ��Request.getRemoteAddr()���ɣ����Ǿ���nginx�ȷ��������������������ʧЧ��
     * 
     * �������ȴ�Header�л�ȡX-Real-IP������������ٴ�X-Forwarded-For��õ�һ��IP(��,�ָ�)��
     * ����������������Request .getRemoteAddr()��
     * 
     * @param request
     * @return
     */
    public static String getIpAddr() {
    	ActionContext ctx = ActionContext.getContext();   
    	HttpServletRequest request=(HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
       // HttpServletResponse response = (HttpServletResponse)ctx.get(ServletActionContext.HTTP_RESPONSE);  
        String ip = request.getHeader("X-Real-IP");
        if (StrUtil.isEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (StrUtil.isEmpty(ip)&& !"unknown".equalsIgnoreCase(ip)) {
        // ��η���������ж��IPֵ����һ��Ϊ��ʵIP��
        int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
             return request.getRemoteAddr();
        }
    }
}
