package com.lb.crawl.common.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.utils.AnalyzeVideoUtil;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;
 
@Component
public class TeachVideoService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	

	//增加新浪视频(球星视频)（http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=1&pf=2121726312&ps=2132736388&dpc=1）
	//@Scheduled(cron="0 0/1 17 * * ?")  
	public void addSinaVideo() throws Exception {
		String startURL = "http://search.sina.com.cn/?q=%B0%AC%B8%A5%C9%AD&range=all&c=news&sort=time&SL2=nbavideo&PID=";
		GetPlayerVideoBySina.getSinaVideo(startURL);
	}

	//增加优酷视频(加农贝克)
	//@Scheduled(cron="0 0/1 17 * * ?")  
	public void addYouKuVideo() throws Exception {
		//加农贝克系列教学
		String startURL = "http://www.soku.com/search_video/q_%E5%8A%A0%E5%86%9C%E8%B4%9D%E5%85%8B_orderby_1_page_";
		GetPlayerVideoByYouKu.getYouKuVideo(startURL);
	}
	
	//@Scheduled(cron="0 0/1 17 * * ?")  
	public void addTeamPlayerRanking(){
		System.out.println("录入五虎教学.........");
		GetPlayerVideoByYouKu.getWuHu("http://www.soku.com/search_video/q_%E4%BA%94%E8%99%8E%E7%AF%AE%E7%90%83_orderby_1_page_");
	}
	
	//增加优酷视频(NIKE系列教学)
	//@Scheduled(cron="0 0/1 17 * * ?")  
	public void addYouKuVideoFromNike() throws Exception {
		String startURL ="http://www.soku.com/search_video/q_NIKE%E7%B2%BE%E5%93%81%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_";
		GetPlayerVideoByYouKu.getYouKuVideoFromNike(startURL);
	}
	
	//增加暑期篮球教学系列
	//@Scheduled(cron="0 0/1 20 * * ?")  
	public void addYouKuVideoFromShuQi() throws Exception {
		String startURL ="http://www.soku.com/search_video/q_%E6%9A%91%E6%9C%9F%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_";
		GetPlayerVideoByYouKu.getYouKuVideoFromShuQi(startURL);
	}
	
	
	
}
