package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface LBTeamService {
	/**
	 * ¼��LB�������
	 * @param name
	 * @param img
	 * @param province
	 * @param city
	 * @param area
	 * @param phone
	 */
	public Integer addLBTeam(String name,String img,String province,String city,String area,String phone);

	/**
	 * ���ݲ�ѯ�����г�LB�������
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public JSONArray getLBTeamBy(Integer pageNo,Integer pageSize);
	
	/**
	 * �������ID��ȡ�����ϸ��Ϣ
	 * @param teamId
	 * @return
	 */
	public JSONObject getLBTeamDetailBy(Integer teamId);
	
	
	 
	/**
	 * �������ID��ȡ�ö�������Ա
	 * @param teamId
	 * @return
	 */
	public JSONArray getPlayersByTeamId(Integer teamId);
	
	
	
	
}
