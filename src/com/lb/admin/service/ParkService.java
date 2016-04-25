package com.lb.admin.service;

import net.sf.json.JSONArray;

public interface ParkService {
	public Integer addPark(Integer userId,String parkName,String areaDetail,String parkImg);
	
	/**
	 * 搜索附近的公园（学校）
	 * @param pageNo
	 * @param pageSize
	 * @param lng
	 * @param lat
	 * @param city
	 * @param area
	 * @return
	 */
	public JSONArray getParkBy(Integer pageNo,Integer pageSize,String lng,String lat,String city,String area);
	
	/**
	 * 获取一个公园内所有球队
	 * @param parkId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONArray getTeamBy(Integer parkId,Integer pageNo,Integer pageSize);
	
	
}
