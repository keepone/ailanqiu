package com.lb.crawl.thread.nba.video.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.thread.nba.video.service.NBAMatchResultService;
import com.lb.dao.BaseDao;
import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoByYouKu;

@Service("MatchResultThreadImpl")
@Component
public class MatchResultThreadImpl{
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	NBAMatchResultService t=new NBAMatchResultService();
	EveryDayVideoFromSinaThreadImpl every=new EveryDayVideoFromSinaThreadImpl();
	
	 
	//@Scheduled(cron="0 2 10 * * ?") 
	public void addNBAGameResult(){
	   for(int i=1;i<=3;i++){
		  Thread thread2=new Thread(t,"nbamatchresult��"+i);
		  System.out.println("����");
		  thread2.start();
		   
		  System.out.println("�Ѿ�����");

		  try {
			  System.out.println("�߳�����");
			thread2.sleep(30000);
			 System.out.println("�߳����߽���");
			 System.out.println("�߳��Ѿ�ִ�У�������������������������������������������������"+i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  System.out.println("�߳��Ѿ�ִ�У�"+i);
		 
	   }
		//thread.start();
		 
	}
	
	 
}
