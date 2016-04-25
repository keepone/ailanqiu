package com.lb.service;

import com.lb.utils.PageBean;

import net.sf.json.JSONObject;

public interface DirectService {
	
	/**
	 * 爬虫录入直播信息
	 */
	public void addDirectBroadcast();
	
	/**
	 * 获取从今天开始的比赛直播情况
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	 public PageBean getDirectSinceToday(Integer pageNo,Integer pageSize);
	
 
	 /**
	  * 根据日期时间戳（精确到秒）和客队名字得到某一场直播的主键ID
	  * @param date
	  * @param guest_team
	  * @return
	  */
	 public Integer getDirectId(long date,String guest_team);
 
}
