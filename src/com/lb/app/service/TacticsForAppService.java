package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TacticsForAppService {
	/**
	 * 根据页数和每页大小根据时间降序获取【篮球战术】
	 * @param pageNo 第几页
	 * @param pageSize 每页显示数据
	 * @return
	 */
	public JSONArray getAllTactics(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据【篮球战术】ID获取视频详情
	 * @param newsId
	 * @return
	 */
	public JSONObject getTacticsById(Integer newsId);
}
