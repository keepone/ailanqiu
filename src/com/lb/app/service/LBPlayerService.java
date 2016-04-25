package com.lb.app.service;

import net.sf.json.JSONObject;

public interface LBPlayerService {
	/**
	 * �������ID�����Ա
	 * @param teamId
	 * @returnname,img,weight,height,birthday,position,clothNum
	 */
	public Integer addPlayer(String name,String img,Integer weight,Integer height,String birthday,Integer position,Integer clothNum,Integer teamId);
	
	/**
	 * ��ȡ��Ա��ϸ��Ϣ
	 * @param playerId
	 * @return
	 */
	public JSONObject getPlayerDetail(Integer playerId);
}
