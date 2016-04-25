package com.lb.crawl.nba.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetNBAInfoUtil;
import com.lucene.crawl.GetPlayerVideoByYouKu;

 
@Component
public class LBContentService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	
	//从nba中国录取专栏作家文章
	@Scheduled(cron="0 30 19 * * ?")  
	public void addSpecial() {
		System.out.println("从nba中国录取专栏作家文章");
		String startURL = "http://china.nba.com/";
	 	GetNBAInfoUtil.getSpecial(startURL);
	 	 
		 
	}
}
