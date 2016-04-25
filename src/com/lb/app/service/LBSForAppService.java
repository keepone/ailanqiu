package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface LBSForAppService {
	/**
	 * ����ҳ����ÿҳ��С����ʱ�併���ȡ����
	 * @param pageNo �ڼ�ҳ
	 * @param pageSize ÿҳ��ʾ����
	 * @return
	 */
	public JSONArray getCourt(Integer pageNo,String lng,String lat);
	
	/**
	 * ��ȡ����ϸ��Ϣ
	 * @param courtId
	 * @return
	 */
	public JSONObject getCourtDetail(Integer courtId);
	 
}
