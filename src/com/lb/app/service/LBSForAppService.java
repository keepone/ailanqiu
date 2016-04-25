package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface LBSForAppService {
	/**
	 * 根据页数和每页大小根据时间降序获取新闻
	 * @param pageNo 第几页
	 * @param pageSize 每页显示数据
	 * @return
	 */
	public JSONArray getCourt(Integer pageNo,String lng,String lat);
	
	/**
	 * 获取球场详细信息
	 * @param courtId
	 * @return
	 */
	public JSONObject getCourtDetail(Integer courtId);
	 
}
