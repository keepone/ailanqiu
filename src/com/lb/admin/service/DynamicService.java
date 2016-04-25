package com.lb.admin.service;

import com.lb.utils.PageBean;
import com.lb.utils.TimeUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface DynamicService {
	
	
	public PageBean getDynamicListBy(String userName,Integer adId,String searchDynamicContent,Integer pageNo,Integer pageSize);
	
	public Integer getDynamicListCountBy(String searchDynamicContent);
	
	
	public Integer deteteDynamicBy(Integer dynamicId);
	public Integer addDynamic(Integer circleId,String cardName,Integer dynamicType,Integer adId,int userId, String textContent, JSONArray imgArr,String tag_name,double x_position,double y_position, String tag_name2,double x_position2,double y_position2, String tag_name3,double x_position3,double y_position3);
	public Integer updateDynamic(Integer dynamicId,String content,String dynamicImg);
	public Integer addDynamicAgree(int userId, int replyUserId, int dynamicId);
	
	
	
	public Integer addDynamicLeave(Integer userId, Integer replyLeaveId,
			Integer dynamicId,String leaveContent,Integer replyUserId) ;
	
	public JSONArray getLeaveOfDynamicBy(Integer dynamicId);
	
	public JSONObject getLeaveBasicInfoBy(Integer leaveId) ;
	
	public Integer recommendDynamic(int dynamicId);
	
	
	public Integer getUserCardNumBy(Integer userId, String cardName);
	
	public Integer ifAddCardToday(Integer userId, String cardName);
	

	}
