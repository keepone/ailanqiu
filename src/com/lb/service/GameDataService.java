package com.lb.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface GameDataService {
	 public JSONArray getGameDataByDirectId(Integer directId,String team);
 
}
