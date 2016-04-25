package com.lb.crawl.nba.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.nba.GetSpecialFrom163;
import com.lb.dao.BaseDao;
 
@Component
public class GetSpecialCrawImpl {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	
	//从网易抓取［考古系］
	//@Scheduled(cron="0 1 20 * * ?")  
	public void addTeamPlayerRanking(){
		GetSpecialFrom163.getNewsBy163_history("http://sports.163.com/special/archaeology/");
	}
	
	//从网易抓取［人物志］模块
	//@Scheduled(cron="0 38 20 * * ?")  
	public void addPerson(){
		System.out.println("从网易抓取［人物志］模块........");
		String startURL = "http://sports.163.com/special/nbalegendhz";
		GetSpecialFrom163.getPersonal("http://sports.163.com/special/nbalegendhz");
	}
	
	
	 
}
