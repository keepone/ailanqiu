package com.lb.app.service;

import net.sf.json.JSONObject;

public interface LBPlayerService {
	/**
	 * 根据球队ID添加球员
	 * @param teamId
	 * @returnname,img,weight,height,birthday,position,clothNum
	 */
	public Integer addPlayer(String name,String img,Integer weight,Integer height,String birthday,Integer position,Integer clothNum,Integer teamId);
	
	/**
	 * 获取球员详细信息
	 * @param playerId
	 * @return
	 */
	public JSONObject getPlayerDetail(Integer playerId);
}
