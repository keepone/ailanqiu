package com.lb.app.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface NewsForAppService {
	/**
	 * ����ҳ����ÿҳ��С����ʱ�併���ȡ����
	 * @param pageNo �ڼ�ҳ
	 * @param pageSize ÿҳ��ʾ����
	 * @return
	 */
	public JSONArray getAllNews(Integer pageNo,Integer pageSize);
	
	/**
	 * ��������ID��ȡ��������
	 * @param newsId
	 * @return
	 */
	public JSONObject getNewsByNewsId(Integer newsId);
}
