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

	
	//��nba�й�¼ȡר����������
	@Scheduled(cron="0 30 19 * * ?")  
	public void addSpecial() {
		System.out.println("��nba�й�¼ȡר����������");
		String startURL = "http://china.nba.com/";
	 	GetNBAInfoUtil.getSpecial(startURL);
	 	 
		 
	}
}
