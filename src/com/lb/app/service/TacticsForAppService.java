package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface TacticsForAppService {
	/**
	 * ����ҳ����ÿҳ��С����ʱ�併���ȡ������ս����
	 * @param pageNo �ڼ�ҳ
	 * @param pageSize ÿҳ��ʾ����
	 * @return
	 */
	public JSONArray getAllTactics(Integer pageNo,Integer pageSize);
	
	/**
	 * ���ݡ�����ս����ID��ȡ��Ƶ����
	 * @param newsId
	 * @return
	 */
	public JSONObject getTacticsById(Integer newsId);
}
