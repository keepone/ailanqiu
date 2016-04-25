package com.lb.admin.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;

public interface BasketballCourtService {
	
	/**
	 * 获取正在审核的球场
	 * @return
	 */
	public JSONArray getReviewCourt();
	
	/**
	 * 学校列表
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean getSchoolList(Integer ifOKSchool,String startTime,String endTime,String schoolName,Integer pageNo,Integer pageSize,Integer userType);
	
	
	public Integer updateCourtStatus(Integer courtId,String tableName);
	
	public Integer updateNational(Integer nationalId,String content);
	
	public JSONArray getReview(String tableName);
	
	
	
}
