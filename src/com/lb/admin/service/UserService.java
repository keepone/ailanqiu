package com.lb.admin.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface UserService {
	public JSONArray getBattleList();
	
	
	public JSONArray getUserList();
	public Integer deleteUserBy(Integer userId);
	
	public PageBean getUserListBy(String startTime,String endTime,Integer usreType,Integer pageNo, Integer pageSize);
	
	public Integer userRegister(String phone, String pwd);

	public JSONObject getUserNameAndImgBy(Integer userId);
	
	public Integer addFocusBy(Integer userId, Integer focudUserId);
	
	
	/**
	 * 随机获取一名用户
	 * @return
	 */
	public JSONObject getRandomUser();
	
	
	/**
	 * 获取首页广告
	 * @return
	 */
	public JSONArray getIndexAd();
	
	public JSONArray getCircleList();
}
