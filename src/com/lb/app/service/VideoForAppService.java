package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface VideoForAppService {
	/**
	 * ����ҳ����ÿҳ��С����ʱ�併���ȡ��Ƶ
	 * @param pageNo �ڼ�ҳ
	 * @param pageSize ÿҳ��ʾ����
	 * @return
	 */
	public JSONArray getAllVideo(Integer pageNo,Integer pageSize);
	
	/**
	 * ������ƵID��ȡ��Ƶ����
	 * @param newsId
	 * @return
	 */
	public JSONObject getVideoById(Integer newsId);
}
