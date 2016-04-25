package com.lb.service;

import java.util.List;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface NewsService {
	/**
	 * 增加新闻（新浪）
	 */
	//public void addNews();
 
	/**
	 * 根据查询条件获取新闻列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean getAllNewsByConditions(Integer pageNo,Integer pageSize);
	
	/**
	 * 根据查询条件获取新闻列表数量
	 * @return
	 */
	public Integer getAllNewsCountByConditions();
	
	
	/**
	 * 获取新闻详情
	 * @param newsId
	 * @return
	 */
	public JSONObject getNewsDetail(Integer newsId);
	
	
	public Integer updateNews(Integer newsId,String newsTitle,String newsContent);
	/**
	 * 增加篮球战术(http://www.172lanqiu.com/)
	 */
	public void addTactics();
	
	public PageBean getAllSpecialByConditions(Integer pageNo,Integer pageSize);
	
	public Integer getCountBySql(String sql);
	/**
	 * 根据专栏ID获取对应的专栏信息
	 * @param specialId
	 * @return
	 */
	public JSONObject getSpecialById(Integer specialId);
	
	/**
	 * 根据专栏ID更新对应专栏信息
	 * @param specialId
	 * @param specialName
	 * @param content
	 * @return
	 */
	public Integer updateSpecialById(Integer specialId,String specialName,String content);
	
	/**
	 * 获取所有NBA考古系（网易http://sports.163.com/special/archaeology_04/）
	 * @return
	 */
	public List<JSONObject> getAllHistorySpecial();
	
	

	
}
