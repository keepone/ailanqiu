package com.lb.app.service;

import java.util.Map;

import net.sf.json.JSONArray;

import com.lb.utils.PageBean;

public interface DirectForAppService {
	/**
	 * 获取从今天开始的直播信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 public Map<String, JSONArray> getDirectSinceToday(Integer pageNo,Integer pageSize);
	 
	 /**
	  * 根据指定日期获取当天的直播信息
	  * @param date
	  * @return
	  */
	 public JSONArray getDirectByDate(long date);
}
