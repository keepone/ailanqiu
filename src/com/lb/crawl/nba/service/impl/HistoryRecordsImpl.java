package com.lb.crawl.nba.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.nba.GetHistoryRecords;
import com.lb.crawl.nba.GetSpecialFrom163;
import com.lb.dao.BaseDao;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetProxyIpUtil;
 
@Component
public class HistoryRecordsImpl {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	
	//nbaÀúÊ·¼ÍÂ¼
	//@Scheduled(cron="0 21 23 * * ?")  
	public void addLianSheng(){
		GetHistoryRecords.getLianSheng("http://sports.sohu.com/20070303/n248486360.shtml");
		GetHistoryRecords.getPersonalScore("http://sports.sohu.com/20070326/n248986361.shtml");
		GetHistoryRecords.getEveryMaxScore("http://sports.sohu.com/20070327/n249014288.shtml");
		GetHistoryRecords.getPersonalSumScore("http://sports.sohu.com/20070327/n249011128.shtml");
		GetHistoryRecords.getAllChampion("http://sports.sina.com.cn/nba/finals.shtml");
		GetHistoryRecords.getBestPlayer("http://sports.163.com/13/0626/22/92B1N94S00051CA1.html");
	}
 
	
	 
}
