package com.lb.admin.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONArray;

public interface BasketballCourtService {
	
	/**
	 * ��ȡ������˵���
	 * @return
	 */
	public JSONArray getReviewCourt();
	
	/**
	 * ѧУ�б�
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBean getSchoolList(Integer ifOKSchool,String startTime,String endTime,String schoolName,Integer pageNo,Integer pageSize,Integer userType);
	
	
	public Integer updateCourtStatus(Integer courtId,String tableName);
	
	public Integer updateNational(Integer nationalId,String content);
	
	public JSONArray getReview(String tableName);
	
	
	
}
