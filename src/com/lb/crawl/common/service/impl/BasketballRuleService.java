package com.lb.crawl.common.service.impl;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.craw.common.GetBasketballRule;
import com.lb.dao.BaseDao;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetProxyIpUtil;

 
@Component
public class BasketballRuleService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}


	
	//¿∫«ÚπÊ‘Ú
	//@Scheduled(cron="0 0/1 22 * * ?")  
	public void addTeamPlayerRanking(){
		GetBasketballRule.getRuleName("http://www.ilanqiu.com/rules/list_3_",1);
	}
	
 
}
