package com.lb.app.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.lb.utils.PageBean;

public interface DirectForAppService {
	/**
	 * ��ȡ�ӽ��쿪ʼ��ֱ����Ϣ
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 public Map<String, JSONArray> getDirectSinceToday(Integer pageNo,Integer pageSize);
	 
	 /**
	  * ����ָ�����ڻ�ȡ�����ֱ����Ϣ
	  * @param date
	  * @return
	  */
	 public JSONArray getDirectByDate(long date);
}
