package com.lb.app.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.utils.PageBean;

public interface CommonForAppService {
	 /**
	  * ��ȡÿһ���NBAѡ��״Ԫ
	  * @return
	  */
	 public List<JSONObject> getAllBestPlayer();
	 
	 /**
	  * ��ȡNBAн�����а�
	  * @return
	  */
	 public List<JSONObject> getSalary();
	 
	 public List<JSONObject> getChampion();
}
