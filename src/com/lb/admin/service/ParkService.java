package com.lb.admin.service;

import net.sf.json.JSONArray;

public interface ParkService {
	public Integer addPark(Integer userId,String parkName,String areaDetail,String parkImg);
	
	/**
	 * ���������Ĺ�԰��ѧУ��
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
	 * ��ȡһ����԰���������
	 * @param parkId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONArray getTeamBy(Integer parkId,Integer pageNo,Integer pageSize);
	
	
}
