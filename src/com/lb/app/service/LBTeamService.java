package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface LBTeamService {
	/**
	 * 录入LB联盟球队
	 * @param name
	 * @param img
	 * @param province
	 * @param city
	 * @param area
	 * @param phone
	 */
	public Integer addLBTeam(String name,String img,String province,String city,String area,String phone);

	/**
	 * 根据查询条件列出LB联盟球队
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONArray getLBTeamBy(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据球队ID获取球队详细信息
	 * @param teamId
	 * @return
	 */
	public JSONObject getLBTeamDetailBy(Integer teamId);
	
	
	 
	/**
	 * 根据球队ID获取该队所有球员
	 * @param teamId
	 * @return
	 */
	public JSONArray getPlayersByTeamId(Integer teamId);
	
	
	
	
}
