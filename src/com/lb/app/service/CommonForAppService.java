package com.lb.app.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.utils.PageBean;

public interface CommonForAppService {
	 /**
	  * 获取每一年的NBA选秀状元
	  * @return
	  */
	 public List<JSONObject> getAllBestPlayer();
	 
	 /**
	  * 获取NBA薪资排行榜
	  * @return
	  */
	 public List<JSONObject> getSalary();
	 
	 public List<JSONObject> getChampion();
}
