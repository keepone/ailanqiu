package com.lb.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONObject;

public interface DirectService {
	
	/**
	 * ����¼��ֱ����Ϣ
	 */
	public void addDirectBroadcast();
	
	/**
	 * ��ȡ�ӽ��쿪ʼ�ı���ֱ�����
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 public PageBean getDirectSinceToday(Integer pageNo,Integer pageSize);
	
 
	 /**
	  * ��������ʱ�������ȷ���룩�ͿͶ����ֵõ�ĳһ��ֱ��������ID
	  * @param date
	  * @param guest_team
	  * @return
	  */
	 public Integer getDirectId(long date,String guest_team);
 
}
