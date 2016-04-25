package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface VideoForAppService {
	/**
	 * 根据页数和每页大小根据时间降序获取视频
	 * @param pageNo 第几页
	 * @param pageSize 每页显示数据
	 * @return
	 */
	public JSONArray getAllVideo(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据视频ID获取视频详情
	 * @param newsId
	 * @return
	 */
	public JSONObject getVideoById(Integer newsId);
}
