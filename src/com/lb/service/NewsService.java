package com.lb.service;

import java.util.List;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface NewsService {
	/**
	 * �������ţ����ˣ�
	 */
	//public void addNews();
 
	/**
	 * ���ݲ�ѯ������ȡ�����б�
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean getAllNewsByConditions(Integer pageNo,Integer pageSize);
	
	/**
	 * ���ݲ�ѯ������ȡ�����б�����
	 * @return
	 */
	public Integer getAllNewsCountByConditions();
	
	
	/**
	 * ��ȡ��������
	 * @param newsId
	 * @return
	 */
	public JSONObject getNewsDetail(Integer newsId);
	
	
	public Integer updateNews(Integer newsId,String newsTitle,String newsContent);
	/**
	 * ��������ս��(http://www.172lanqiu.com/)
	 */
	public void addTactics();
	
	public PageBean getAllSpecialByConditions(Integer pageNo,Integer pageSize);
	
	public Integer getCountBySql(String sql);
	/**
	 * ����ר��ID��ȡ��Ӧ��ר����Ϣ
	 * @param specialId
	 * @return
	 */
	public JSONObject getSpecialById(Integer specialId);
	
	/**
	 * ����ר��ID���¶�Ӧר����Ϣ
	 * @param specialId
	 * @param specialName
	 * @param content
	 * @return
	 */
	public Integer updateSpecialById(Integer specialId,String specialName,String content);
	
	/**
	 * ��ȡ����NBA����ϵ������http://sports.163.com/special/archaeology_04/��
	 * @return
	 */
	public List<JSONObject> getAllHistorySpecial();
	
	

	
}
