package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface NewsForAppService {
	/**
	 * 根据页数和每页大小根据时间降序获取新闻
	 * @param pageNo 第几页
	 * @param pageSize 每页显示数据
	 * @return
	 */
	public JSONArray getAllNews(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据新闻ID获取新闻详情
	 * @param newsId
	 * @return
	 */
	public JSONObject getNewsByNewsId(Integer newsId);
}
